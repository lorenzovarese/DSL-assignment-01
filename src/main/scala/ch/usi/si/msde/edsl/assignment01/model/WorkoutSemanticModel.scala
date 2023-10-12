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
import squants.time.Time
/*
 * Implement here, or in other scala files in this package,
 * the semantic model for Exercise 2. If you use other
 * files, please remember to import them.
 */


//<editor-fold desc="Named">
sealed trait NamedObject:
  val name: Name

case class Name(value: String):
  require(value.nonEmpty)
// </editor-fold>

case class Weight(mass: Mass):
  require(mass.value >= 0)

//<editor-fold desc="Description">
case class Description(value: String):
  require(value.nonEmpty)
sealed trait DescObject:
  val description: Description
// </editor-fold>

//<editor-fold desc="Workout_Params">
case class Duration(min: Time):
  require(min.value > 0)

case class Workout_Set(value: Int, repetition: Repetition):
  require(value > 0)

case class Repetition(value: Int):
  require(value > 0)
// </editor-fold>

//<editor-fold desc="Workout">
case class WorkoutDaily(description: Description, workouts: Set[Workout] = Set()) extends DescObject
sealed trait Workout extends DescObject:
  val requirements : Set[Requirement]

// Set[RequirementSet] = Set() ---> meaning it can be null
case class CardioTraining(description: Description, duration: Duration, requirements: Set[Requirement] = Set()) extends Workout
case class WeightTraining(description: Description, set: Workout_Set, requirements: Set[Requirement] = Set()) extends Workout

// </editor-fold>

//<editor-fold desc="Requirement">
sealed trait Requirement extends NamedObject
case class RequirementFacility(name: Name, facility: Facility) extends Requirement
case class RequirementEquipment(name: Name, weight: Option[Weight]) extends Requirement
//</editor-fold>

//<editor-fold desc="Facility">
sealed trait AbstractFacility extends NamedObject:
  val fitnessCenter: FitnessCenter
case class Gym(name: Name, fitnessCenter: FitnessCenter, equipment: Set[Equipment] = Set()) extends AbstractFacility
case class Facility(name : Name, fitnessCenter: FitnessCenter) extends AbstractFacility
case class FitnessCenter(name: Name, facilities: Set[Facility] = Set()) extends NamedObject
//</editor-fold>

//<editor-fold desc="Equipment">
sealed trait Equipment extends NamedObject:
  val gym: Gym
case class Item(name: Name, gym: Gym, weight: Weight) extends Equipment
sealed trait Machine extends Equipment
case class CardioMachine(name: Name, gym: Gym, setting: CardioMachineSetting) extends Machine
case class WeightMachine(name: Name, gym: Gym, setting: WeightMachineSetting) extends Machine
//</editor-fold>

//<editor-fold desc="Settings">

case class Slope(value: Int):
  require(value >= -30 && value <= 30)
case class Speed(vel: Velocity):
  require(vel.value >= 0 && vel.value <= 60)
case class CardioMachineSetting(slope: Slope, speed: Speed)
case class WeightMachineSetting(min: Mass, max: Mass):
  require(max.value > min.value)



/** Construct - if you want - one or more workout examples examples here.
  */
@main def exampleWorkout(): Unit =

  val cardioTraining =  CardioTraining(Description("Cardio Traning"), Duration(Minutes(10)), Set(new Requi))
  // cardio training with not requirement
  val cardioTraining =  CardioTraining(Description("Cardio Traning"), Duration(Minutes(10)))
  val workouts = Set("apple", "orange", "peach", "banana")
  val exampleWorkout =  WorkoutDaily( Description("Monday Workout"))
  println(exampleWorkout)

/** Examples to construct weight, speeds, etc. and of the Option data type.
  */
@main def datatypeExamples(): Unit =
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
