package my

import scala.util.DynamicVariable

object App {

  class Signal[T](expr: => T) {

    import Signal._

    private var myExpr: () => T = _
    private var myValue: T = _
    private var observers: Set[Signal[_]] = Set()
    update(expr)

    protected def update(expr: => T): Unit = {
      myExpr = () => expr // eval or not ?
      computeValue()
    }

    protected def computeValue(): Unit = {
      val newValue = caller.withValue(this)(myExpr())
      if (myValue != newValue) {
        myValue = newValue
        val obs = observers
        observers = Set()
        obs.foreach(_.computeValue())
      }
    }

    def apply(): T = {
      observers += caller.value
      assert(!caller.value.observers.contains(this), "cyclic signal definition")
      myValue
    }
  }

  case object NoSignal extends Signal[Nothing](???) {
    override def computeValue() = ()
  }

  object Signal {
    lazy private val caller = new DynamicVariable[Signal[_]](NoSignal)

    def apply[T](expr: => T) = new Signal(expr)
  }

  class Var[T](expr: => T) extends Signal(expr) {
    override def update(expr: => T): Unit = super.update(expr)
  }

  object Var {
    def apply[T](expr: => T) = new Var(expr)
  }

  class StackableVar[T](init: T) {
    private var values: List[T] = List(init)

    def value: T = values.head

    def withValue[R](newValue: T)(op: => R): R = {
      values = newValue :: values
      try op finally values = values.tail
    }
  }

  def main(args: Array[String]) {

    val signalA = Var("please input") // User input change / update

    signalA()

    val inputValue = Signal {
      println("Hey, " + signalA())  // SetState from input
    }

    inputValue()

    signalA() = "hello world"

    inputValue()
  }

}
