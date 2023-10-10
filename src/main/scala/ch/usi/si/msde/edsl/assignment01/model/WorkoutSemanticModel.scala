package ch.usi.si.msde.edsl.assignment01.model

/** The types used to specify mass/weight (Mass) and to construct a weight in
  * kilograms
  */
import squants.mass.Mass
import squants.mass.Kilograms

/** The types to be used to specify speed in Kms per hour (see example)
  */
import squants.motion.KilometersPerHour
import squants.motion.Velocity
/*
 * Implement here, or in other scala files in this package,
 * the semantic model for Exercise 2. If you use other
 * files, please remember to import them.
 */

/** Construct - if you want - one or more workout examples examples here.
  */
@main def exampleWorkout: Unit =
  val exampleWorkout = ???

  println(exampleWorkout)

/** Examples to construct weight, speeds, etc. and of the Option data type.
  */
@main def datatypeExamples: Unit =
  // A mass/weight
  val mass1: Mass = Kilograms(20)
  println(mass1)
  // Some speed (velocity)
  val speed: Velocity = KilometersPerHour(1)
  println(speed)

  // In scala, whenever you want to represent a value
  // that might be absent, you use the Option data type.
  // It is similar to Java's Optional.
  // For example: 'an optional velocity' can be represented as follows:
  val optionalVelocity1: Option[Velocity] = None

  // Option[T] is a generic type and it is a sum type that can be
  // either None (absent) or Some(v), where v is of type T.
  val optionalVelocity2: Option[Velocity] = Some(KilometersPerHour(3.5))

  // To check if an option is defined there are many ways, but
  // the simplest one for now is to call isDefined/isEmpty
  val optionalVelocity1isDefined = optionalVelocity1.isDefined // False
  val optionalVelocity2isDefined = optionalVelocity2.isDefined // True
  val optionalVelocity1isEmpty = optionalVelocity1.isEmpty // False
  val optionalVelocity2isEmpty = optionalVelocity2.isEmpty // True
