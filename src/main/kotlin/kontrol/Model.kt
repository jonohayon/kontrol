package kontrol

import koma.extensions.*
import koma.eye
import koma.matrix.Matrix
import koma.ones
import koma.util.validation.validate
import koma.zeros

/**
 * A class representing a state space model of a physical/electronical system.
 * @property X The number of states in the system
 * @property Y The number of system outputs
 * @property U The number of system inputs
 * @property A The system matrix
 * @property B The input matrix
 * @property C The output matrix
 * @property D The feedthrough matrix
 * @property isDiscrete Indicates whether the current model has been discretized or not
 */
class Model(val X: Int, val Y: Int, val U: Int) {
  var A: Matrix<Double> = zeros(X, X)
  var B: Matrix<Double> = zeros(X, U)
  var C: Matrix<Double> = zeros(Y, X)
  var D: Matrix<Double> = zeros(Y, U)

  var isDiscrete: Boolean = false

  /**
   * Creates a new [Model] instance with the properties of the current instance.
   */
  fun copy(): Model {
    val m = Model(X, Y, U)
    m.A = A; m.B = B; m.C = C; m.D = D; m.isDiscrete = isDiscrete
    return m
  }

  /**
   * Computes the steady state feedforward input for the given reference input, using equations 8.17-8.19
   * from the Practical Guide to State-space Control (https://file.tavsys.net/control/state-space-guide.pdf).
   * @param reference The given reference input of the systen
   * @return The feedforward input required to keep the system at the reference input steady state
   */
  fun steadyStateFeedforward(reference: Matrix<Double>): Matrix<Double> {
    reference.validate("reference input") { X x 1 }
    val stateRelationMatrix = zeros(X + Y, X + U)
    stateRelationMatrix[0 until X, 0 until X] = if (isDiscrete) A else (A - eye(A.shape()[0]))
    stateRelationMatrix[0 until X, X until X + U] = B
    stateRelationMatrix[X until X + Y, 0 until X] = C
    stateRelationMatrix[X until X + Y, X until X + U]
    val pseudoInverse = stateRelationMatrix.pinv()
    val b = zeros(X + Y, Y)
    b[X until X + Y, 0 until Y] = ones(Y, Y)
    val res = pseudoInverse * b
    val Nx = res[0 until X, 0 until Y]; val Nu = res[X until X + U, 0 until Y]
    return Nu * Nx.pinv() * reference
  }

  companion object {
    /**
     * Converts a given [Model] from continuous time to discrete time.
     * @param model The given Model instance
     * @param dt The sampling time of the system, in seconds. Default value = 0.01
     */
    @JvmStatic
    fun c2d(model: Model, dt: Double = 0.01): Model {
      val dm = model.copy()
      val M = zeros(model.X + model.U, model.X + model.U)
      M[0 until model.X, 0 until model.X] = model.A
      M[0 until model.X, model.X until model.X + model.U] = model.B
      val exp = (M * dt).expm()
      dm.A = exp[0 until model.X, 0 until model.X]
      dm.B = exp[0 until model.X, model.X until model.X + model.U]
      dm.isDiscrete = true
      return dm
    }
  }
}