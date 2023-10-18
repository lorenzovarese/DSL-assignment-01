package ch.usi.si.msde.edsl.assignment01.model

/** The types used to specify mass/weight (Mass) and to construct a weight in
  * kilograms
  */
import squants.mass.Mass
import squants.mass.Kilograms

import scala.collection.immutable.Set

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
  require(min.toMinutes > 0)

case class Workout_Set(value: Int, repetition: Repetition):
  require(value > 0)

case class Repetition(value: Int):
  require(value > 0)
// </editor-fold>

//<editor-fold desc="Workout">
case class WorkoutDaily(description: Description, workouts: Set[Workout] = Set()) extends DescObject
sealed trait Workout extends DescObject:
  val facilities : Set[RequirementFacility]

// Set[RequirementSet] = Set() ---> meaning it can be null
case class WorkoutCardioTraining(description: Description, duration: Duration, facilities: Set[RequirementFacility] = Set(), cardioReqs: Set[RequirementCardio] = Set()) extends Workout
case class WorkoutWeightTraining(description: Description, set: Workout_Set, facilities: Set[RequirementFacility] = Set(), weightReqs: Set[RequirementWeightTraining] = Set()) extends Workout

// </editor-fold>

//<editor-fold desc="Requirement">
sealed trait Requirement extends NamedObject
case class RequirementFacility(name: Name, facility: ObjectFacility) extends Requirement

//Todo: check if weight is needed
// equipments can be empty if user want to
case class RequirementWeightTraining(name: Name, equipments: Set[WeightEquipment] = Set(), weight: Weight) extends Requirement

case class RequirementCardio(name: Name, cardioMachines: CardioMachine) extends Requirement
//</editor-fold>

//<editor-fold desc="Facility">
sealed trait ObjectFacility extends NamedObject
case class Gym(name: Name, equipment: Set[Equipment] = Set()) extends ObjectFacility
case class Facility(name : Name) extends ObjectFacility
case class FitnessCenter(name: Name, facilities: Set[ObjectFacility] = Set()) extends NamedObject
//</editor-fold>

//<editor-fold desc="Equipment">
sealed trait WeightEquipment
sealed trait Equipment extends NamedObject
case class Item(name: Name, weight: Weight) extends Equipment, WeightEquipment
sealed trait Machine extends Equipment
case class CardioMachine(name: Name, setting: CardioMachineSetting) extends Machine
case class WeightMachine(name: Name, setting: WeightMachineSetting, weights: Set[Weight] = Set()) extends Machine, WeightEquipment
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
// Cardio Training Workout

val cardioMachineSetting = CardioMachineSetting(Slope(30), Speed(KilometersPerHour(30)))
val cardioMachine1 = CardioMachine(Name("Cardio Machine"), cardioMachineSetting)
val weightMachineSetting = WeightMachineSetting(Kilograms(10), Kilograms(100))
val weightMachine1 = WeightMachine(Name("Weight machine"), weightMachineSetting)
val item = Item(Name("Item"), Weight(Kilograms(30)))

val gym = Gym(Name("Gym 1"), Set(cardioMachine1, weightMachine1, item))
val fitnessFacility = Facility(Name("Fitness facility"))

val requirementFitness = RequirementFacility(Name("My Fitness center "), fitnessFacility)
val requirementGym = RequirementFacility(Name("My Gym"), gym)
@main def exampleWorkout(): Unit =

  // Cardio
  val cardioRequirement =  RequirementCardio(Name("Cardio Requirement 1"), cardioMachine1)
  val cardioTraining =  WorkoutCardioTraining(Description("Cardio Training"), Duration(Minutes(10)), Set(requirementGym), Set(cardioRequirement))
  // cardio training with no requirement
  val cardioTrainingEmpty =  WorkoutCardioTraining(Description("Cardio Training Empty"), Duration(Minutes(10)))

  // Workout empty
  val workout_Set = Workout_Set(10, Repetition(10))
  val weightTrainingEmpty = WorkoutWeightTraining(Description("Weight training empty"), workout_Set)
  // Workout non empty


  val itemDumbbell = Item(Name("Dumbbell"), Weight(Kilograms(30)))
  val itemBodyWeight = Item(Name("Body Weight"), Weight(Kilograms(10)))
  val requirementWeightTraining = RequirementWeightTraining(Name("Weight training daily"), Set(itemDumbbell, itemBodyWeight), Weight(Kilograms(40)))
  val requirementWeightMachine = RequirementWeightTraining(Name("Weight machine training daily"), Set(weightMachine1), Weight(Kilograms(30)))
  val weightTraining = WorkoutWeightTraining(Description("Weight Training"), workout_Set, Set(requirementFitness),Set(requirementWeightTraining, requirementWeightMachine))

  // Workout Daily
  val exampleWorkout =  WorkoutDaily( Description("Monday Workout"), Set(cardioTrainingEmpty, cardioTraining, weightTrainingEmpty, weightTraining))
  println(exampleWorkout)

@main def exampleFitnessCenter(): Unit =
  val EmptyFitnessCenter = FitnessCenter(Name("Fitness Center A"))
  println(EmptyFitnessCenter)
  val fitnessCenter = FitnessCenter(Name("Fitness Center B"), Set(gym, fitnessFacility))
  println(fitnessCenter)

@main def exampleGymOwnsEquipment(): Unit =
  println(gym)

@main def Example1Prof() : Unit =
  val gymFacility = Gym(Name("Gym"))
  val swimmingPoolFacility = Facility(Name("Swimming pool"))
  val fitnessCenter = FitnessCenter(Name("Fitness Center"), Set(swimmingPoolFacility, gymFacility))
  println(fitnessCenter)
  val requirementFacility = RequirementFacility(Name("Swimming pool"), swimmingPoolFacility)
  val swimmingWorkout =  WorkoutCardioTraining(Description("Swimming workout"), Duration(Minutes(30)), Set(requirementFacility))
  val outdoorRunning = WorkoutCardioTraining(Description("running workout"), Duration(Minutes(30)))
  println(WorkoutDaily(Description("Workout Daily"), Set(swimmingWorkout, outdoorRunning)))

@main def Example2Prof() : Unit =
  // Facility
  val gymFacility = Gym(Name("Gym 2"))
  val fitnessCenter = FitnessCenter(Name("Fitness Center"), Set(gymFacility))
  val requirementFacility = RequirementFacility(Name("Gym"), gymFacility)
  // machine
  val genericMachineSettings = WeightMachineSetting(Kilograms(10), Kilograms(60))
  val chestPressMachine =  WeightMachine(Name("Chest Press"), genericMachineSettings)
  val requirementChestPressMachine = RequirementWeightTraining(Name("Req ChestPress"), Set(chestPressMachine), Weight(Kilograms(20)))
  val shoulderPressMachine = WeightMachine(Name("Shoulder press machine"), genericMachineSettings, Set(Weight(Kilograms(10)), Weight(Kilograms(20)), Weight(Kilograms(30)), Weight(Kilograms(40)), Weight(Kilograms(50)), Weight(Kilograms(60))))
  val requirementShoulderPressMachine = RequirementWeightTraining(Name("Req Shoulder press"), Set(chestPressMachine), Weight(Kilograms(20)))
  val dumbbell = Item(Name("Dumbbell"), Weight(Kilograms(30)))
  val requirementDumbbell = RequirementWeightTraining(Name("Dumbbell"), Set(dumbbell), Weight(Kilograms(20)))
  // workouts
  val chestWorkout = WorkoutWeightTraining(Description("Chest Workout"), Workout_Set(3, Repetition(12)), Set(requirementFacility), Set(requirementChestPressMachine))
  val bicepsWorkout = WorkoutWeightTraining(Description("Biceps workout"), Workout_Set(4, Repetition(15)), Set(requirementFacility) ,Set(requirementDumbbell))
  val shoulderWorkout = WorkoutWeightTraining(Description("Shoulder workout"), Workout_Set(4, Repetition(10)),Set(requirementFacility), Set(requirementShoulderPressMachine))
  println(WorkoutDaily(Description("Upper Body weight training"),Set(chestWorkout, shoulderWorkout, bicepsWorkout)))

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
