package minitime

trait TimeRangeBuilder[T, S] {
  def build(start: T, end: T, inclusive: Boolean): TimeRange[T, S]
}

object TimeRangeBuilder {
  def create[T: Ordering, S: Multiply](defaultStep: S)(
    implicit add: Add[T, S]
  ) = {
    new TimeRangeBuilder[T, S] {
      override def build(start: T, end: T, inclusive: Boolean) = {
        new TimeRange(start, end, defaultStep, inclusive)
      }
    }
  }

  implicit val ld: TimeRangeBuilder[LocalDate, Period]        = create[LocalDate, Period](1.day)
  implicit val ldt: TimeRangeBuilder[LocalDateTime, Duration] = create[LocalDateTime, Duration](1.second)
  implicit val lt: TimeRangeBuilder[LocalTime, Duration]      = create[LocalTime, Duration](1.second)
  implicit val zdt: TimeRangeBuilder[ZonedDateTime, Duration] = create[ZonedDateTime, Duration](1.second)
}
