package ch.usi.si.msde.edsl.assignment01.fluentapi
import ch.usi.si.msde.edsl.assignment01.model.*
import squants.mass.{Kilograms, Mass}
import squants.motion.{KilometersPerHour, Velocity}
import squants.time.{Minutes, Time}

/**
  * Exercise 3
  * Sketch the fluent API methods in this trait.
  */
trait WorkoutFluentApi:
  def named(name: String): Name = ???
  def described(desc: String) : Description = ???

  //Cardio machines
  def `with angle`(slope: Integer) : Slope = ???
  def `with speed`(speed: Velocity) : Speed = ???
  def `with cardio settings`(slope: Slope, speed: Speed) : CardioMachineSetting = ???
  def cardioMachine(name: Name, cardioMachineSetting: CardioMachineSetting) : CardioMachine = ???

  //Weight & weight machine
  def `with weight`(weight: Mass) : Weight = ???
  def `with weight machine settings`(min : Weight, max : Weight) : WeightMachineSetting = ???
  def weightMachine(name: Name, weightMachineSetting: WeightMachineSetting) : WeightMachine = ???
  def item(name: Name, weight: Weight) : Item = ???

  // facility & fitness center
  def fitnessCenter(name: Name, facilities: Set[ObjectFacility] = Set()) : FitnessCenter = ???
  def `composed of`(facilities: String*) : Set[ObjectFacility] = ???
  def facility(name: Name) : Facility = ???
  def gym(name: Name, equipment: Set[Equipment] = Set()) : Gym = ???
  def `owns`(equipments: String*) : Set[Equipment] = ???

  // Workout Daily schedule
  def `workout daily schedule`(description: Description, workouts:Workout*) : WorkoutDaily = ???

  // requirement facility
  def `at place`(string: String) : ObjectFacility = ???
  def `requirement facility`(name: Name, facility: ObjectFacility) : RequirementFacility = ???
  abstract class DescObject(description: Description)

  class WorkoutBuilder(description: Description) extends DescObject(description):
    def `add req facility`(requirementFacility: Requirement): WorkoutBuilder = ???
    def build() : Workout = ???
  // Workout weight training
  def `number of`(sets: Integer, repetition: Repetition) : Workout_Set = ???
  def `multiply by`(repetition: Integer) : Repetition = ???
  def `new workout weight`(description: Description, workout_Set: Workout_Set) : WorkoutWeightTrainingBuilder = ???
  // Requirement weight training

  def matches(mass: Mass) : Weight = ???
  def `weight equipments`(equipments: String*) : Set[WeightEquipment] = ???
  def `requirement weight training`(name: Name, set: Set[WeightEquipment] = Set(), weight: Weight) : RequirementWeightTraining = ???
  class WorkoutWeightTrainingBuilder(description: Description, workout_Set: Workout_Set) extends WorkoutBuilder(description):
    def `add weight equipment`(requirementWeightTraining: RequirementWeightTraining): WorkoutWeightTrainingBuilder = ???

  def lasts(time : Time) : Duration = ???
  class WorkoutCardioTrainingBuilder(description: Description, duration: Duration) extends WorkoutBuilder(description):
    def `add cardio machine`(machine: CardioMachine): WorkoutCardioTrainingBuilder = ???
    def `create workout cardio training` : WorkoutCardioTraining = ???


end WorkoutFluentApi

/**
  * Use the fluent API to construct a workout schedule example.
  */
object WorkoutFluentApiExample1 extends WorkoutFluentApi:

  //cardio Machine
  cardioMachine(named("Cardio Machine"),
    `with cardio settings`(`with angle`(30), `with speed`(KilometersPerHour(30))))

  weightMachine(named("Weight Machine"),
    `with weight machine settings`(`with weight`(Kilograms(10)), `with weight`(Kilograms(30))))

  item(named("Dumbbell"), `with weight`(Kilograms(30)))

  facility(named("Swimming pool"))
  gym(named("Gym A"), `owns`("Cardio Machine", "Weight Machine", "Dumbbell"))

  fitnessCenter(named("Fitness Center"), `composed of`("Gym A", "Swimming Pool"))

  `workout daily schedule`(described("Cardio Training"),
    `new workout weight`(described("Weight workout"), `number of`(3, `multiply by`(10)))
      .`add weight equipment`(
        `requirement weight training`(
          named("Requirement weights"),
          `weight equipments`("Dumbbell"),
          matches(Kilograms(30))
        ))
      .`add req facility`(`requirement facility`(named("Requirement Gym"), `at place`("Gym A")))
      .build(),
    `new workout weight`(described("Weight workout 2"), `number of`(3, `multiply by`(15)))
      .`add weight equipment`(
        `requirement weight training`(named("Require weight machine"),
          `weight equipments`("Weight Machine"), matches(Kilograms(20)))
      )
      .`add req facility`(`requirement facility`(named("Requirement Gym"), `at place`("Gym A")))
      .build()
  )


end WorkoutFluentApiExample1

/**
  * Use the fluent API to construct another workout schedule example.
  */
object WorkoutFluentApiExample2 extends WorkoutFluentApi:


end WorkoutFluentApiExample2