name := "kryo-vector-serialization-test"

version := "0.1"

val scala2131 = "2.13.1" // works
val scala2132 = "2.13.2" // error, new Vector impl introduced https://github.com/scala/scala/releases/tag/v2.13.2
val scala21315 = "2.13.15" // latest version has error too

scalaVersion := scala2131
crossScalaVersions := Seq(scala21315, scala2132, scala2131)

libraryDependencies ++= Seq(
  "io.altoo" %% "scala-kryo-serialization" % "1.2.0"
)