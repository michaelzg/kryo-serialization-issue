package com.example

import akka.actor.ActorSystem
import akka.serialization.SerializationExtension
import io.altoo.akka.serialization.kryo.DefaultKryoInitializer
import io.altoo.akka.serialization.kryo.serializer.scala.ScalaKryo

class CustomKryoInitializer extends DefaultKryoInitializer {
  override def postInit(kryo: ScalaKryo): Unit = {
    kryo.register(classOf[Vector[_]])
  }
}

object VectorSerializationExample extends App {
  val system = ActorSystem("VectorSystem")

  try {
    val v = Vector(1, 2, 3)
    val serialization = SerializationExtension(system)
    val serializer = serialization.findSerializerFor(v)
    val bytes = serializer.toBinary(v)
    val deserialized = serializer.fromBinary(bytes, Some(classOf[Vector[_]])) //  error happens here
    println(s"Deserialized: $deserialized")
  } finally {
    system.terminate()
  }
}