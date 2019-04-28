package kontrol

import koma.extensions.mapIndexed
import koma.matrix.Matrix

/**
 * Calculates the Moore-Penrose inverse (pseudoinverse) of a double matrix.
 */
fun Matrix<Double>.pinv(): Matrix<Double> {
  val (U, S, V) = this.SVD()
  val pinvS = S.mapIndexed { i, j, t -> if (i == j && t != 0.0) 1.0 / t else t }
  return V * pinvS * U.T
}