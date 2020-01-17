package minitime

trait Multiply[A] {
  def apply(a: A, scalar: Int): A
}

object Multiply {
  def create[A](f: (A, Int) => A) = new Multiply[A] {
    override def apply(a: A, scalar: Int) = f(a, scalar)
  }

  implicit val p: Multiply[Period]   = create[Period](_ multipliedBy _)
  implicit val d: Multiply[Duration] = create[Duration](_ multipliedBy _)
}
