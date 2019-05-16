package kontrol

import koma.*
import koma.extensions.*

fun main() {
  val m = 100.0 // Mass of elevator, kg
  val d = 10.0 // Damping factor, N * s / m

  val model = Model(2, 2, 1)
  model.A = mat [0, 1 end
                 0, -d / m]
  model.B = mat [0 end 1 / m]
  model.C = mat [1, 0]

  val discrete = Model.c2d(model)
  val controller = Controller(discrete)
  controller.K = mat [40, 70]
  val x0 = mat [10 end 0]
  val toPlot = arrayOf(controller.lsim(x0).map { yk -> yk[0, 0] })

  val K = mat [0, 1 end -1, 0]
  println(K.eig())

  println(discrete.steadyStateFeedforward(x0))
  println(model.isControllable())
  println(model.isObservable())
}