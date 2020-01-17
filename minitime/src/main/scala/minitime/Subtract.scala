package minitime

import java.time.temporal.ChronoUnit

trait Subtract[L, R, C] {
  def apply(l: L, r: R): C
}

object Subtract {
  def create[L, R, C](f: (L, R) => C) = new Subtract[L, R, C] {
    override def apply(l: L, r: R): C = f(l, r)
  }

  // format: OFF
  implicit val ldp: Subtract[LocalDate, Period, LocalDate] = create((l: LocalDate, r: Period) => l minus r)
  implicit val ldld: Subtract[LocalDate, LocalDate, Period] = create((l: LocalDate, r: LocalDate) => Period.ofDays(ChronoUnit.DAYS.between(r, l).toInt))
  implicit val ldtp: Subtract[LocalDateTime, Period, LocalDateTime] = create((l: LocalDateTime, r: Period) => l minus r)
  implicit val ldtd: Subtract[LocalDateTime, Duration, LocalDateTime] = create((l: LocalDateTime, r: Duration) => l minus r)
  implicit val ldtldt: Subtract[LocalDateTime, LocalDateTime, Duration] = create((l: LocalDateTime, r: LocalDateTime) => Duration.between(r, l))
  implicit val ltd: Subtract[LocalTime, Duration, LocalTime] = create((l: LocalTime, r: Duration) => l minus r)
  implicit val ltlt: Subtract[LocalTime, LocalTime, Duration] = create((l: LocalTime, r: LocalTime) => Duration.between(r, l))
  implicit val zdtp: Subtract[ZonedDateTime, Period, ZonedDateTime] = create((l: ZonedDateTime, r: Period) => l minus r)
  implicit val zdtd: Subtract[ZonedDateTime, Duration, ZonedDateTime] = create((l: ZonedDateTime, r: Duration) => l minus r)
  implicit val zdtzdt: Subtract[ZonedDateTime, ZonedDateTime, Duration] = create((l: ZonedDateTime, r: ZonedDateTime) => Duration.between(r, l))
  implicit val pp: Subtract[Period, Period, Period] = create((l: Period, r: Period) => l minus r)
  implicit val dd: Subtract[Duration, Duration, Duration] = create((l: Duration, r: Duration) => l minus r)
}
