package kontrol

import koma.eye
import koma.zeros
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtensionsTest {
  companion object {
    val FULL_RANK_M = eye(3)
    val ZERO_RANK_M = zeros(3, 3)
  }

  @Test fun testMatrixRank() {
    assertEquals(3, FULL_RANK_M.rank(), "Matrix of full rank should have rank = n")
    assertEquals(0, ZERO_RANK_M.rank(), "Matrix of zero rank should have rank = 0")
  }
}