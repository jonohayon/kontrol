package kontrol

import koma.E
import koma.end
import koma.mat
import koma.util.test.assertMatrixEquals
import kotlin.test.Test
import kotlin.test.assertNotNull

class ModelTest {
  companion object {
    val E3 = E * E * E
  }

  /**
   * Tests the discretization function of the Model class, Model#c2d.
   *
   * The test model has been taken from https://math.stackexchange.com/questions/2873647/discretization-of-continuous-time-state-space-system
   */
  @Test fun testModelDiscretization() {
    val m = Model(2, 1, 1)
    m.A = mat [1, 1 end
               0, 1]
    m.B = mat [1 end 0]
    m.C = mat [E3, E3]
    val dm = Model.c2d(m, 3.0)

    val resA = mat [E3, 3 * E3 end
                    0, E3]
    val resB = mat [E3 - 1 end 0]
    assertMatrixEquals(resA, dm.A)
    assertMatrixEquals(resB, dm.B)
  }
}
