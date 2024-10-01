package com.example

import akka.actor.{Actor, ActorSystem, Props}
import io.altoo.akka.serialization.kryo.DefaultKryoInitializer
import io.altoo.akka.serialization.kryo.serializer.scala.ScalaKryo
import akka.serialization.SerializationExtension

class CustomKryoInitializer extends DefaultKryoInitializer {
  override def postInit(kryo: ScalaKryo): Unit = {
    kryo.register(classOf[Vector[_]])
  }
}

object VectorSerializationExample extends App {
  // test using actor serialization
  val system = ActorSystem("VectorSystem")
  val vectorActor = system.actorOf(Props[VectorActor], "vectorActor")

  vectorActor ! Vector(1, 2, 3)

  Thread.sleep(5000)
  system.terminate()

  // test using methods closer to kryo
  val v = Vector(1, 2, 3)
  val serialization = SerializationExtension(system)
  val serializer = serialization.findSerializerFor(v)
  val bytes = serializer.toBinary(v)
  val deserialized = serializer.fromBinary(bytes, Some(classOf[Vector[_]])) //  error happens here
  println(s"Deserialized: $deserialized")
}

class VectorActor extends Actor {
  def receive: Receive = {
    case v: Vector[_] =>
      println(s"Received vector: $v")
    case msg =>
      println(s"Received unexpected message: $msg")
  }
}