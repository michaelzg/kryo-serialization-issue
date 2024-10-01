package com.example

import com.typesafe.config.ConfigFactory
import io.altoo.serialization.kryo.scala.serializer.ScalaKryo
import io.altoo.serialization.kryo.scala.{DefaultKryoInitializer, ScalaKryoSerializer}

import scala.util.{Failure, Success}

object VectorSerializationExample extends App {
  val config = ConfigFactory.load()
  val classLoader = Thread.currentThread().getContextClassLoader
  val serializer = new ScalaKryoSerializer(config, classLoader)

  val originalVector = Vector(1, 2, 3, 4, 5)
  println(s"Original vector: $originalVector")

  serializer.serialize(originalVector) match {
    case Success(serializedBytes) =>
      println(s"Serialized to ${serializedBytes.length} bytes")

      // set the type explicitly else
      // java.lang.ClassCastException: class scala.collection.immutable.Vector cannot be cast to class scala.runtime.Nothing
      serializer.deserialize[Vector[Int]](serializedBytes) match {
        case Success(deserializedVector) =>
          println(s"Deserialized vector: $deserializedVector")
        case Failure(e) =>
          println(s"Deserialization failed: ${e.getClass.getName}")
          e.printStackTrace()
      }

    case Failure(e) =>
      println(s"Serialization failed: ${e.getClass.getName}")
      e.printStackTrace()
  }
}

class CustomKryoInitializer extends DefaultKryoInitializer {
  override def postInit(kryo: ScalaKryo): Unit = {
    kryo.register(classOf[Vector[_]])
  }
}