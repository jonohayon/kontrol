package kontrol

import koma.extensions.*
import koma.matrix.Matrix
import koma.zeros

class Model(val X: Int, val Y: Int, val U: Int) {
  var A: Matrix<Double> = zeros(X, X)
  var B: Matrix<Double> = zeros(X, U)
  var C: Matrix<Double> = zeros(Y, X)
  var D: Matrix<Double> = zeros(Y, U)

  fun copy(): Model {
    val m = Model(X, Y, U)
    m.A = A; m.B = B; m.C = C; m.D = D;
    return m
  }

  companion object {
    @JvmStatic
    fun c2d(model: Model, dt: Double = 0.01): Model {
      val dm = model.copy()
      val M = zeros(model.X + model.U, model.X + model.U)
      val x = model.X - 1; val u = model.X + model.U - 1
      M[0..x, 0..x] = model.A
      M[0..x, model.X..u] = model.B
      val exp = (M * dt).expm()
      dm.A = exp[0..x, 0..x]
      dm.B = exp[0..x, model.X..u]
      return dm
    }
  }
}