import sbt._, Keys._

object RootBuild extends Build {
  lazy val root = Project(
    id = "functional-programing-in-scala-2",
    base = file("."))
    .aggregate(calculator, streams, example, quickcheck)

  def project(id: String) = Project(
    id = id,
    base = file(id))

  lazy val calculator = project("calculator")
  lazy val streams = project("streams")
  lazy val example = project("example")
  lazy val quickcheck = project("quickcheck")
  
  scalaVersion := "2.11.7"
  name := "functional-programing-in-scala-2"
  version := "0.0.1"
}
