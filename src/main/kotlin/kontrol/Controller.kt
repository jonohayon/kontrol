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
   * Simulates a controller's response with a given initial state. Currently works only for discrete
   * models, still WIP.
   * @param x0 The initial state of the system
   * @param dt The sampling time of the controller
   * @param tTotal The total running time of the controller
   */
  fun lsim(x0: Matrix<Double>, dt: Double = 0.01, tTotal: Double = 10.0): List<Matrix<Double>> {
    x0.validate("initial state vector") { model.X x 1 }

    val samples: Int = (tTotal / dt).toInt()
    var x = x0
    val simulatedValues = (0..samples).map { i ->
      val xnew = this.model.A * x + this.model.B * u
      val ynew = this.model.C * x + this.model.D * u
      x = xnew
      this.update(ynew, dt)
      ynew
    }

    this.reset()

    return simulatedValues
  }
}