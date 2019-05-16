package kontrol

import koma.matrix.Matrix
import koma.util.validation.validate
import koma.zeros

/**
 * A class representing a given [Model]'s state space controller.
 * @property model The physical model that defines the relations between the state's inputs and outputs
 * @property xHat An estimation of the system's state. Still WIP
 * @property u The system input of the controller
 * @property K The controller gains, used to drive the input to the desired output
 */
class Controller(private val model: Model) {
  // Control Variables
  var xHat: Matrix<Double> = zeros(model.X, 1) // State estimate; TODO - Implement estimation
  var u: Matrix<Double> = zeros(model.U, 1) // Control input

  // Control Gains
  var K = zeros(model.U, model.X)

  /**
   * Updates the state data of the controller using the recorded output.
   * @param y The system output
   * @param dt The sampling time of the system (required for state estimation)
   */
  fun update(y: Matrix<Double>, dt: Double) {
    y.validate("control output vector") { model.Y x 1 }
    xHat = y // TODO - Implement estimation
    u = -K * xHat
  }

  /**
   * Resets the controller's state data (state estimation and control input)
   */
  fun reset() {
    xHat = zeros(model.X, 1)
    u = zeros(model.U, 1)
  }

  /**
   * Simulates a controller's response with a given initial state, with the *input set to the zero vector*.
   * Still WIP.
   * @param x0 The initial state of the system
   * @param dt The sampling time of the controller
   * @param tTotal The total running time of the controller
   * @return A list of the system outputs over the interval [0, tTotal] in increments of dt
   */
  fun lsim(x0: Matrix<Double>, dt: Double = 0.01, tTotal: Double = 10.0): List<Matrix<Double>> {
    x0.validate("initial state vector") { model.X x 1 }

    // Discritizes the current model. According to the Octave source code, this is the way to do apparently
    val newMod = if (!this.model.isDiscrete) Model.c2d(this.model, dt) else this.model

    val samples: Int = (tTotal / dt).toInt()
    var x = x0
    val simulatedValues = (0..samples).map { i ->
      val xnew = newMod.A * x + newMod.B * u
      val ynew = newMod.C * x + newMod.D * u
      x = xnew
      this.update(ynew, dt)
      ynew
    }

    this.reset()

    return simulatedValues
  }
}