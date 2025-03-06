
name := """Leaderboard"""
organization := "io.datatroops"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.15"

val playSlickVersion = "5.3.0"

Compile / doc / scalacOptions ++= Seq("-doc-title", "Project Title", "-doc-version", "1.0")


libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
  evolutions,
  "org.postgresql" % "postgresql" % "42.7.3",
  "com.typesafe.play" %% "play-slick" % playSlickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,
  "com.typesafe.akka" %% "akka-stream" % "2.7.0",
  "com.typesafe.akka" %% "akka-stream-kafka" % "4.0.2",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.7.0",
  "com.typesafe.akka" %% "akka-serialization-jackson" % "2.7.0", // Explicitly added
  "ch.qos.logback" % "logback-classic" % "1.5.6"
)

//libraryDependencies += "io.github.play-swagger" %% "play-swagger" % "1.7.3"
libraryDependencies += "org.webjars" % "swagger-ui" % "5.18.3"
libraryDependencies += "com.iheart" %% "play-swagger" % "0.10.7"
libraryDependencies += "io.swagger" % "swagger-core" % "1.6.8"





