name := """class-info-api"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.13"

// Dependencies:
libraryDependencies += guice
libraryDependencies += "org.playframework" %% "play" % "3.0.2"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test

libraryDependencies ++= Seq(
  "org.playframework" %% "play-slick"            % "6.0.0",
  "org.playframework" %% "play-slick-evolutions" % "6.0.0",
  "mysql" % "mysql-connector-java" % "8.0.26"
)
