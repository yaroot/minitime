import java.time.{Duration => JDuration, Period => JPeriod}
import scala.concurrent.duration._
import scala.language.implicitConversions

package object minitime {
  type LocalDate     = java.time.LocalDate
  type LocalDateTime = java.time.LocalDateTime
  type LocalTime     = java.time.LocalTime
  type ZonedDateTime = java.time.ZonedDateTime
  type Period        = java.time.Period
  type Duration      = java.time.Duration

  implicit class RichInt(val i: Int) extends AnyVal {
    def hour    = JDuration.ofHours(i)
    def hours   = JDuration.ofHours(i)
    def minute  = JDuration.ofMinutes(i)
    def minutes = JDuration.ofMinutes(i)
    def second  = JDuration.ofSeconds(i)
    def seconds = JDuration.ofSeconds(i)
    def milli   = JDuration.ofMillis(i)
    def millis  = JDuration.ofMillis(i)
    def nano    = JDuration.ofNanos(i)
    def nanos   = JDuration.ofNanos(i)
    def day     = JPeriod.ofDays(i)
    def days    = JPeriod.ofDays(i)
    def week    = JPeriod.ofWeeks(i)
    def weeks   = JPeriod.ofWeeks(i)
    def month   = JPeriod.ofMonths(i)
    def months  = JPeriod.ofMonths(i)
    def year    = JPeriod.ofYears(i)
    def years   = JPeriod.ofYears(i)
  }

  implicit class RichDuration(val d: JDuration) extends AnyVal {
    def toFiniteDuration: FiniteDuration =
      FiniteDuration(d.toNanos, NANOSECONDS)
  }

  implicit class Infix[L](val l: L) extends AnyVal {
    def +[R](r: R)(implicit add: Add[L, R]): L                 = add(l, r)
    def -[R, C](r: R)(implicit subtract: Subtract[L, R, C]): C = subtract(l, r)
    def *(scalar: Int)(implicit multiply: Multiply[L]): L      = multiply(l, scalar)
    def /[R, C](r: R)(implicit divide: Divide[L, R, C]): C     = divide(l, r)

    def to[S](r: L)(implicit ev: TimeRangeBuilder[L, S]): TimeRange[L, S] =
      ev.build(l, r, true)

    def till[S](r: L)(implicit ev: TimeRangeBuilder[L, S]): TimeRange[L, S] =
      ev.build(l, r, false)
  }

  implicit def infixOrderingOps[T: Ordering](x: T): Ordering[T]#Ops = {
    Ordering.Implicits.infixOrderingOps(x)
  }

  def orderingOf[T](f: (T, T) => Int): Ordering[T]            = (x: T, y: T) => f(x, y)
  implicit val localDateOrdering: Ordering[LocalDate]         = orderingOf[LocalDate](_ compareTo _)
  implicit val localDateTimeOrdering: Ordering[LocalDateTime] = orderingOf[LocalDateTime](_ compareTo _)
  implicit val localTimeOrdering: Ordering[LocalTime]         = orderingOf[LocalTime](_ compareTo _)
  implicit val zonedDateTimeOrdering: Ordering[ZonedDateTime] = orderingOf[ZonedDateTime](_ compareTo _)
  implicit val durationOrdering: Ordering[Duration]           = orderingOf[Duration](_ compareTo _)
}
