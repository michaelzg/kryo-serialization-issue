name := "kryo-vector-serialization-test"

version := "0.1"

val scala2131 = "2.13.1" // works
val scala2132 = "2.13.2" // error, new Vector impl introduced
val scala21315 = "2.13.15" // latest version has error too

scalaVersion := scala21315
crossScalaVersions := Seq(scala21315, scala2132, scala2131)

val akkaVersion = "2.6.20"
val kryoSerializationVersion = "1.0.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "io.altoo" %% "akka-kryo-serialization" % kryoSerializationVersion
)