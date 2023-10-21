package ch.usi.si.msde.edsl.assignment01.fluentapi
import ch.usi.si.msde.edsl.assignment01.model.*
import squants.mass.{Kilograms, Mass}
import squants.motion.{KilometersPerHour, Velocity}
import squants.time.{Minutes, Time}

/**
  * Exercise 3 - Fluent API
  *
  */
trait WorkoutFluentApi:
  def named(name: String): Name = ???
  def `described by`(desc: String) : Description = ???

  //Cardio machines
  def `set angle`(slope: Integer) : Slope = ???
  def `set speed`(speed: Velocity) : Speed = ???
  def `configure settings`(slope: Slope, speed: Speed) : CardioMachineSetting = ???
  def `create cardio machine`(name: Name, cardioMachineSetting: CardioMachineSetting) : CardioMachine = ???

  //Weight & weight machine
  class MinWeight(weight: Mass)
  class MaxWeight(weight: Mass)

  def `set min weight`(weight: Mass) : MinWeight = ???
  def `set max weight`(weight: Mass) : MaxWeight = ???
  def `with weight`(weight: Mass) : Weight = ???
  def `contains weights`(weight: Mass*) : Set[Weight] = ???
  def `configure settings`(`min`: MinWeight, max : MaxWeight) : WeightMachineSetting = ???
  def `create weight machine`(name: Name, weightMachineSetting: WeightMachineSetting, weights: Set[Weight] = Set()) : WeightMachine = ???
  def `create item`(name: Name, weight: Weight) : Item = ???

  // facility & fitness center
  def `create fitness center`(name: Name, facilities: Set[ObjectFacility] = Set()) : FitnessCenter = ???
  def `contains`(facilities: String*) : Set[ObjectFacility] = ???
  def `create facility`(name: Name) : Facility = ???
  def `create gym`(name: Name, equipment: Set[Equipment] = Set()) : Gym = ???
  def `owns`(equipments: String*) : Set[Equipment] = ???

  // Workout Daily schedule
  def `workout daily schedule`(description: Description, workouts:Workout*) : WorkoutDaily = ???

  // requirement facility
  def `located at`(string: String) : ObjectFacility = ???
  def `requirement facility`(name: Name, facility: ObjectFacility) : RequirementFacility = ???
  abstract class DescObject(description: Description)
  class WorkoutBuilder(description: Description) extends DescObject(description):
    def `add`(requirementFacility: Requirement): WorkoutBuilder = ???
    def build() : Workout = ???

  // Workout weight training
  def `with`(sets: Integer, repetition: Repetition) : Workout_Set = ???
  def `set multiplied by`(repetition: Integer) : Repetition = ???
  def `sets multiplied by`(repetition: Integer) : Repetition = ???
  def `create weight workout`(description: Description, workout_Set: Workout_Set) : WorkoutWeightTrainingBuilder = ???

  // Requirement weight training
  def matches(mass: Mass) : Weight = ???
  def `weight equipments`(equipments: String*) : Set[WeightEquipment] = ???
  def `requirement weight training`(name: Name, set: Set[WeightEquipment] = Set(), weight: Weight) : RequirementWeightTraining = ???
  class WorkoutWeightTrainingBuilder(description: Description, workout_Set: Workout_Set) extends WorkoutBuilder(description):
    def `add`(requirementWeightTraining: RequirementWeightTraining): WorkoutWeightTrainingBuilder = ???
  def `that lasts`(time : Time) : Duration = ???
  class WorkoutCardioTrainingBuilder(description: Description, duration: Duration) extends WorkoutBuilder(description):
    def `add cardio machine`(machine: CardioMachine): WorkoutCardioTrainingBuilder = ???
  def `create cardio training`(description: Description, duration: Duration): WorkoutCardioTrainingBuilder = ???

end WorkoutFluentApi

/**
 * This object WorkoutExercise1Example1 serves as the implementation for Example 1,
 * as described in the first section of the Assignment Instructions.
 *
 */
object WorkoutExercise1Example1 extends WorkoutFluentApi:

  `create fitness center`(named("USI Fitness Center"), `contains`("Swimming Pool", "Gym"))

  `workout daily schedule`(`described by`("Cardio Training Day 1"),
    `create cardio training`(`described by`("Swimming"), `that lasts`(Minutes(30)))
      .`add`(`requirement facility`(named("Swimming Pool Requirement"), `located at`("Swimming Pool"))).build(),
    `create cardio training`(`described by`("Outdoor Running"), `that lasts`(Minutes(30))).build())

end WorkoutExercise1Example1

/**
 * This object WorkoutExercise1Example2 serves as the implementation for Example 2,
 * as described in the first section of the Assignment Instructions.
 *
 */
object WorkoutExercise1Example2 extends WorkoutFluentApi:
  `workout daily schedule`(`described by`("Upper Body Weight Training"),

    `create weight workout`(`described by`("Chest Workout"), `with`(3, `sets multiplied by`(12)))
      .`add`(`requirement weight training`(
        named("Chest Requirement"),
        `weight equipments`("Chest Press Machine"),
          matches(Kilograms(50))
      )
      ).build(),

    `create weight workout`(`described by`("Biceps Workout"), `with`(4, `sets multiplied by`(15)))
      .`add`(`requirement weight training`(
          named("Biceps Requirement"),
          `weight equipments`("Dumbbell"),
          matches(Kilograms(20))
        )
      ).build(),

    `create weight workout`(`described by`("Shoulder Workout"), `with`(4, `sets multiplied by`(10)))
      .`add`(`requirement weight training`(
          named("Shoulder Requirement"),
          `weight equipments`("Shoulder Press Machine"),
          matches(Kilograms(30))
        )
      ).build()
  )

  `create fitness center`(named("Only Gym"), `contains`("USI Gym"))

end WorkoutExercise1Example2

/**
 * This object WorkoutFluentApiGenericExample1 defines a fitness center with cardio and weight machines,
 * a dumbbell, and a swimming pool. It also specifies two daily weight workouts.
 *
 */
object WorkoutFluentApiGenericExample1 extends WorkoutFluentApi:

  //cardio Machine
  `create cardio machine`(named("Tapis Roulant"),
    `configure settings`(`set angle`(30), `set speed`(KilometersPerHour(30))))

  `create weight machine`(named("Lat Machine"),
    `configure settings`(`set min weight`(Kilograms(10)), `set max weight`(Kilograms(30))), `contains weights`(Kilograms(10),Kilograms(20), Kilograms(30)))

  `create item`(named("Dumbbell"), `with weight`(Kilograms(15)))

  `create facility`(named("Swimming pool"))

  `create gym`(named("Gym A"), `owns`("Tapis Roulant", "Lat Machine", "Dumbbell"))

  `create fitness center`(named("FitCenter"), `contains`("Gym A", "Swimming Pool"))

  `workout daily schedule`(`described by`("Shoulder&Back Workout"),
    `create weight workout`(`described by`("Dumbbell Overhead Press"), `with`(3, `sets multiplied by`(10)))
      .`add`(
        `requirement weight training`(
          named("Requirement weights"),
          `weight equipments`("Dumbbell"),
          matches(Kilograms(30))
        ))
      .build(),
    `create weight workout`(`described by`("Wide-Grip Lat Pull-Down"), `with`(1, `set multiplied by`(25)))
      .`add`(
        `requirement weight training`(named("Require Lat Machine"),
          `weight equipments`("Lat Machine"), matches(Kilograms(35)))
      )
      .build()
  )


end WorkoutFluentApiGenericExample1

/**
 * This object WorkoutFluentApiGenericExample2 sets up a fitness center with cardio and weight machines in a gym
 * and a separate cycling studio. It also outlines a daily workout schedule focused on lower body and cardio training.
 *
 */
object WorkoutFluentApiGenericExample2 extends WorkoutFluentApi:

  // Cardio and Weight Machines
  `create cardio machine`(named("Cycling Machine"),
    `configure settings`(`set angle`(0), `set speed`(KilometersPerHour(25))))

  `create weight machine`(named("Leg Press Machine"),
    `configure settings`(`set min weight`(Kilograms(20)), `set max weight`(Kilograms(100))), `contains weights`(Kilograms(20), Kilograms(40), Kilograms(60)))

  `create weight machine`(named("Squat Rack"),
    `configure settings`(`set min weight`(Kilograms(40)), `set max weight`(Kilograms(150))), `contains weights`(Kilograms(40), Kilograms(80), Kilograms(120)))

  // Facilities
  `create facility`(named("Cycling Studio"))
  `create gym`(named("Gym JL"), `owns`("Cycling Machine", "Leg Press Machine", "Squat Rack"))

  // Fitness Center
  `create fitness center`(named("Fitness Center with Cycling Studio"), `contains`("Gym JL", "Cycling Studio"))

  // Workout Daily Schedule
  `workout daily schedule`(`described by`("Lower Body and Cardio Training"),
    `create weight workout`(`described by`("Leg Press Workout"), `with`(4, `sets multiplied by`(12)))
      .`add`(`requirement weight training`(
        named("Leg Press Requirement"),
        `weight equipments`("Leg Press Machine"),
        matches(Kilograms(60))
      )).build(),

    `create weight workout`(`described by`("Squats"), `with`(3, `sets multiplied by`(10)))
      .`add`(`requirement weight training`(
        named("Squat Requirement"),
        `weight equipments`("Squat Rack"),
        matches(Kilograms(80))
      )).build(),

    `create cardio training`(`described by`("Cycling"), `that lasts`(Minutes(45)))
      .`add`(`requirement facility`(named("Cycling Studio Requirement"), `located at`("Cycling Studio")))
      .build()
  )

end WorkoutFluentApiGenericExample2