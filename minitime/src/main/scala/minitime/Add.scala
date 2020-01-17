package minitime

trait Add[L, R] {
  def apply(l: L, r: R): L
}

object Add {
  def create[L, R](f: (L, R) => L) = new Add[L, R] {
    override def apply(l: L, r: R) = f(l, r)
  }

  implicit val ldp: Add[LocalDate, Period]        = create[LocalDate, Period](_ plus _)
  implicit val ldtp: Add[LocalDateTime, Period]   = create[LocalDateTime, Period](_ plus _)
  implicit val ldtd: Add[LocalDateTime, Duration] = create[LocalDateTime, Duration](_ plus _)
  implicit val ltd: Add[LocalTime, Duration]      = create[LocalTime, Duration](_ plus _)
  implicit val zdtp: Add[ZonedDateTime, Period]   = create[ZonedDateTime, Period](_ plus _)
  implicit val zdtd: Add[ZonedDateTime, Duration] = create[ZonedDateTime, Duration](_ plus _)
  implicit val pp: Add[Period, Period]            = create[Period, Period](_ plus _)
  implicit val dd: Add[Duration, Duration]        = create[Duration, Duration](_ plus _)
}
