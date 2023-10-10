val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    developers := List(Developer("firstname.lastname", "FirstName LastName", "firstanme.lastname@usi.ch", url("https://yourwebsite.com"))),
    name := "edsl-assignment-01",
    version := "1.0.0",
        scalacOptions ++= Seq(
      "-Yexplicit-nulls"
    ),
    libraryDependencies += "org.typelevel" %% "squants" % "1.8.3",
    scalaVersion := scala3Version,
  )
