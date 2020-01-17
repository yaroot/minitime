package minitime

trait Add[A, B] {
  type R
  def apply(a: A, b: B): R
}

object Add {
  type Aux[A, B, C] = Add[A, B] { type R = C }
  def of[A, B, C](f: (A, B) => C): Aux[A, B, C] = new Add[A, B] {
    type R = C

    override def apply(a: A, b: B): C = f(a, b)
  }

  implicit val ldp: Aux[LocalDate, Period, LocalDate]            = of(_ plus _)
  implicit val ldtp: Aux[LocalDateTime, Period, LocalDateTime]   = of(_ plus _)
  implicit val ldtd: Aux[LocalDateTime, Duration, LocalDateTime] = of(_ plus _)
  implicit val ltd: Aux[LocalTime, Duration, LocalTime]          = of(_ plus _)
  implicit val zdtp: Aux[ZonedDateTime, Period, ZonedDateTime]   = of(_ plus _)
  implicit val zdtd: Aux[ZonedDateTime, Duration, ZonedDateTime] = of(_ plus _)
  implicit val pp: Aux[Period, Period, Period]                   = of(_ plus _)
  implicit val dd: Aux[Duration, Duration, Duration]             = of(_ plus _)

  implicit def commutativeAdd[A, B](implicit ev: Aux[A, B, A]): Aux[B, A, A] = of[B, A, A]((b, a) => ev(a, b))
}
