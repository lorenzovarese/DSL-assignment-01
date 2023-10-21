package ch.usi.si.msde.edsl.assignment01.model

import scala.collection.immutable.Set

/** Imports for mass and weight types. */
import squants.mass.Mass
import squants.mass.Kilograms

/** Imports for speed types. */
import squants.motion.KilometersPerHour
import squants.motion.Velocity

/** Imports for time types. */
import squants.time.Minutes
import squants.time.Time

//<editor-fold desc="Named">

/**
 * A trait for named objects.
 *
 * @define name the name of the object
 */
sealed trait NamedObject:
  val name: Name

/**
 * Class representing a name.
 *
 * @constructor create a new name with a string value.
 * @param value the string value of the name
 */
case class Name(value: String):
  require(value.nonEmpty)

// </editor-fold>

//<editor-fold desc="Description">

/**
 * Class representing a description.
 *
 * @constructor create a new description with a string value.
 * @param value the string value of the description
 */
case class Description(value: String):
  require(value.nonEmpty)

/**
 * A trait for describable objects.
 *
 * @define description the description of the object
 */
sealed trait DescObject:
  val description: Description

// </editor-fold>

//<editor-fold desc="Workout_Params">

/**
 * Class representing weight.
 *
 * @constructor create a new weight with a mass.
 * @param mass the mass value of the weight
 */
case class Weight(mass: Mass):
  require(mass.value >= 0)

/**
 * Class representing a duration.
 *
 * @constructor create a new duration with a time value.
 * @param min the time value of the duration
 */
case class Duration(min: Time):
  require(min.toMinutes > 0)

/**
 * Class representing a workout set.
 *
 * @constructor create a new workout set with a value and repetition.
 * @param value the number of sets
 * @param repetition the number of repetitions in each set
 */
case class Workout_Set(value: Int, repetition: Repetition):
  require(value > 0)

/**
 * Class representing a repetition.
 *
 * @constructor create a new repetition with a value.
 * @param value the number of repetitions
 */
case class Repetition(value: Int):
  require(value > 0)

// </editor-fold>

//<editor-fold desc="Workout">

/**
 * Class representing a daily workout.
 *
 * @constructor create a new daily workout with a description and set of workouts.
 * @param description the description of the daily workout
 * @param workouts the set of workouts for the day
 */
case class WorkoutDaily(description: Description, workouts: Set[Workout] = Set()) extends DescObject

/**
 * A trait for different types of workouts.
 *
 * @define facilities the required facilities for the workout
 */
sealed trait Workout extends DescObject:
  val facilities : Set[RequirementFacility]

/**
 * Class representing a cardio training workout.
 *
 * @constructor Create a new cardio training workout with a description, duration, and set of requirements.
 * @param description Description of the cardio training workout.
 * @param duration Duration of the cardio training.
 * @param facilities Optional set of required facilities.
 * @param cardioReqs Optional set of cardio requirements.
 */
case class WorkoutCardioTraining(description: Description, duration: Duration, facilities: Set[RequirementFacility] = Set(), cardioReqs: Set[RequirementCardio] = Set()) extends Workout

/**
 * Class representing a weight training workout.
 *
 * @constructor Create a new weight training workout with a description, set, and set of requirements.
 * @param description Description of the weight training workout.
 * @param set Workout set specification.
 * @param facilities Optional set of required facilities.
 * @param weightReqs Optional set of weight training requirements.
 */
case class WorkoutWeightTraining(description: Description, set: Workout_Set, facilities: Set[RequirementFacility] = Set(), weightReqs: Set[RequirementWeightTraining] = Set()) extends Workout

// </editor-fold>

//<editor-fold desc="Facility">

/**
 * Trait representing a general object facility.
 */
sealed trait ObjectFacility extends NamedObject

/**
 * Case class representing a gym.
 *
 * @constructor Create a new gym with a name and set of equipment.
 * @param name The name of the gym.
 * @param equipment Optional set of equipment available in the gym.
 */
case class Gym(name: Name, equipment: Set[Equipment] = Set()) extends ObjectFacility

/**
 * Case class representing a generic facility.
 *
 * @constructor Create a new facility with a name.
 * @param name The name of the facility.
 */
case class Facility(name : Name) extends ObjectFacility

/**
 * Case class representing a fitness center.
 *
 * @constructor Create a new fitness center with a name and set of facilities.
 * @param name The name of the fitness center.
 * @param facilities Optional set of facilities available in the fitness center.
 */
case class FitnessCenter(name: Name, facilities: Set[ObjectFacility] = Set()) extends NamedObject

//</editor-fold>

//<editor-fold desc="Requirement">

/**
 * Trait for requirements.
 */
sealed trait Requirement extends NamedObject

/**
 * Class representing a facility requirement.
 *
 * @constructor Create a new facility requirement.
 * @param name Name of the facility requirement.
 * @param facility Specified facility object.
 */
case class RequirementFacility(name: Name, facility: ObjectFacility) extends Requirement

/**
 * Class representing a weight training requirement.
 *
 * @constructor Create a new weight training requirement.
 * @param name Name of the weight training requirement.
 * @param equipments Optional set of weight equipment.
 * @param weight Specified weight.
 */
case class RequirementWeightTraining(name: Name, equipments: Set[WeightEquipment] = Set(), weight: Weight) extends Requirement

/**
 * Class representing a cardio requirement.
 *
 * @constructor Create a new cardio requirement.
 * @param name Name of the cardio requirement.
 * @param cardioMachines Specified cardio machine.
 */
case class RequirementCardio(name: Name, cardioMachines: CardioMachine) extends Requirement

//</editor-fold>

//<editor-fold desc="Equipment">

/**
 * Sealed trait representing general equipment. All types of equipment extend this trait.
 *
 * Extends NamedObject to ensure each piece of equipment has a name.
 */
sealed trait Equipment extends NamedObject

/**
 * Sealed trait representing weight equipment. Serves as a type marker for equipment dealing with weights.
 */
sealed trait WeightEquipment

/**
 * Case class representing a single item, like a dumbbell or a barbell.
 *
 * @constructor Create a new Item instance.
 * @param name The name of the item.
 * @param weight The weight of the item.
 */
case class Item(name: Name, weight: Weight) extends Equipment, WeightEquipment

/**
 * Sealed trait representing a machine, serving as a type marker for different types of machines.
 *
 * Extends Equipment to indicate that machines are a subtype of equipment.
 */
sealed trait Machine extends Equipment

/**
 * Case class representing a cardio machine like a treadmill or an elliptical.
 *
 * @constructor Create a new CardioMachine instance.
 * @param name The name of the machine.
 * @param setting The settings of the cardio machine, including speed and slope.
 */
case class CardioMachine(name: Name, setting: CardioMachineSetting) extends Machine

/**
 * Case class representing a weight machine like a leg press or a bench press machine.
 *
 * @constructor Create a new WeightMachine instance.
 * @param name The name of the machine.
 * @param setting The settings of the weight machine, including minimum and maximum weight.
 * @param weights Optional set of weight settings. Defaults to an empty set.
 */
case class WeightMachine(name: Name, setting: WeightMachineSetting, weights: Set[Weight] = Set()) extends Machine, WeightEquipment

//</editor-fold>

//<editor-fold desc="Settings">

/**
 * Case class representing the slope setting of a cardio machine.
 *
 * @constructor Create a new Slope instance.
 * @param value The value of the slope. Must be between -30 and 30 inclusive.
 */
case class Slope(value: Int):
  require(value >= -30 && value <= 30)

/**
 * Case class representing the speed setting of a cardio machine.
 *
 * @constructor Create a new Speed instance.
 * @param vel The velocity of the machine. Must be between 0 and 60 inclusive.
 */
case class Speed(vel: Velocity):
  require(vel.value >= 0 && vel.value <= 60)

/**
 * Case class representing the settings for a cardio machine.
 *
 * @constructor Create a new CardioMachineSetting instance.
 * @param slope The slope setting for the cardio machine.
 * @param speed The speed setting for the cardio machine.
 */
case class CardioMachineSetting(slope: Slope, speed: Speed)

/**
 * Case class representing the settings for a weight machine.
 *
 * @constructor Create a new WeightMachineSetting instance.
 * @param min The minimum mass setting for the weight machine.
 * @param max The maximum mass setting for the weight machine. Must be greater than min.
 */
case class WeightMachineSetting(min: Mass, max: Mass):
  require(max.value > min.value)

//</editor-fold>

/**
 * Construct - if you want - one or more workout examples examples here.
  */

val cardioMachineSetting = CardioMachineSetting(Slope(30), Speed(KilometersPerHour(30)))
val cardioMachine1 = CardioMachine(Name("Cardio Machine"), cardioMachineSetting)
val weightMachineSetting = WeightMachineSetting(Kilograms(10), Kilograms(100))
val weightMachine1 = WeightMachine(Name("Weight Machine"), weightMachineSetting)
val item = Item(Name("Kettlebell"), Weight(Kilograms(30)))

val gym = Gym(Name("Gym 1"), Set(cardioMachine1, weightMachine1, item))
val fitnessFacility = Facility(Name("Fitness Facility"))

val requirementFitness = RequirementFacility(Name("My Fitness Center"), fitnessFacility)
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
  val emptyFitnessCenter = FitnessCenter(Name("Fitness Center A"))
  println(emptyFitnessCenter)
  val fitnessCenter = FitnessCenter(Name("Fitness Center B"), Set(gym, fitnessFacility))
  println(fitnessCenter)

@main def exampleGymOwnsEquipment(): Unit =
  println(gym)

@main def example1Prof() : Unit =
  val gymFacility = Gym(Name("Gym"))
  val swimmingPoolFacility = Facility(Name("Swimming pool"))
  val fitnessCenter = FitnessCenter(Name("Fitness Center"), Set(swimmingPoolFacility, gymFacility))
  println(fitnessCenter)
  val requirementFacility = RequirementFacility(Name("Swimming pool"), swimmingPoolFacility)
  val swimmingWorkout =  WorkoutCardioTraining(Description("Swimming workout"), Duration(Minutes(30)), Set(requirementFacility))
  val outdoorRunning = WorkoutCardioTraining(Description("running workout"), Duration(Minutes(30)))
  println(WorkoutDaily(Description("Workout Daily"), Set(swimmingWorkout, outdoorRunning)))

@main def example2Prof() : Unit =
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

/**
 * Examples to construct weight, speeds, etc. and of the Option data type.
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
