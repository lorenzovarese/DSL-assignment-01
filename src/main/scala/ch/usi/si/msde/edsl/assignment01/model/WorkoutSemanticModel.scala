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

/** The types to be used to specify time in minutes
  */
import squants.time.Minutes

/*
 * Implement here, or in other scala files in this package,
 * the semantic model for Exercise 2. If you use other
 * files, please remember to import them.
 */
case class UniqueName(val value: String):
  require(value.length > 0)
  //TODO: mettere constraint su univocità

case class Name(val value: String):
  require(value.length > 0)

case class Description(val value: String):
  require(value.length > 0)

case class Duration(val value: Minutes):
  require(value > 0)

case class Repetition(val value: Int):
  require(value > 0)

case class WorkoutSet(val value: Int, val repetition: Repetition):
  require(value > 0)

case class Weight(val value: Kilograms):
  require(value >= 0)

case class Slope(val value: Int):
  require(value >= -30 && value <= 30)

case class Speed(val value: KilometersPerHour):
  require(value >= 0 && value <= 60)

sealed trait Object:
  val description: Description

case class WorkoutDaily(val description: Description, val workouts: Set[Workout] = Set()) extends Object

sealed trait Workout extends Object:
  val description: Description

case class CardioTraining(val description: Description, val duration: Duration) extends Workout

case class WeightTraining(val description: Description, val set: WorkoutSet) extends Workout

sealed trait Requirement:
  val name: Name

case class Facility(val fitnessCenter: FitnessCenter):

case class Gym(val equipment: Set[Equipment] = Set()) extends Facility:

case class RequirementFacility(val name: Name, val facility: Facility) extends Requirement

case class RequirementEquipment(val name: Name, val weight: Weight) extends Requirement

case class FitnessCenter(val name: UniqueName, val facilities: Set[Facility] = Set()) //Default empty set, but check it

sealed trait Equipment:
  val name: Name
  val gym: Gym

sealed trait Machine extends Equipment:

case class CardioMachine(val name: Name, val gym: Gym, val setting: CardioMachineSetting) extends Machine

case class WeightMachine(val name: Name, val gym: Gym, val setting: WeightMachineSetting) extends Machine

case class CardioMachineSetting(val slope: Slope, val speed: Speed)

case class WeightMachineSetting(val min: Weight, val max: Weight):
  require(max > min)

case class Item(val name: Name, val gym: Gym, val weight: Weight) extends Equipment

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
