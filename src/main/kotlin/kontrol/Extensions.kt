package kontrol

import koma.extensions.mapIndexed
import koma.matrix.Matrix
import koma.matrix.ejml.EJMLMatrix
import org.ejml.data.Complex_F64
import kotlin.math.sqrt

/**
 * Calculates the rank of a double matrix using its SVD
 */
fun Matrix<Double>.rank(): Int {
  val (_, S, _) = this.SVD()
  return S.diag().toIterable().filter { it != 0.0 }.size
}

/**
 * Returns the eigenvalues of a given matrix, using the EJML backend function
 */
fun Matrix<Double>.eig(): MutableList<Complex_F64>? {
  return (this as? EJMLMatrix)?.getBaseMatrix()?.eig()?.eigenvalues
}

/**
 * Calculates the Frobenius norm of a double matrix
 */
fun Matrix<Double>.norm(): Double = sqrt((this * this.transpose()).trace())
