package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = Signal {
    val bb = b()
    bb * bb - 4 * a() * c()
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = Signal {
    delta() match {
      case d if d > 0 => {
        val ax2 = 2 * a()
        val bb = b()
        val sqrt = math.sqrt(d)
        Set((- bb + sqrt) / ax2, (- bb - sqrt) / ax2)
      }
      case d if d == 0 => Set(- b() / 2 / a())
      case _ => Set()
    }
  }
}
