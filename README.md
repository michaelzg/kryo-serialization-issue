# kryo-serialization-issue

This is a reproducer for the error `java.lang.InstantiationError: scala.collection.immutable.Vector`
and the following stack trace.

Run in `sbt` with cross-built Scala 2.13.x versions:
  * `++2.13.15 run` (latest Scala version as of this writing, error is present)
  * `++2.13.2 run` (error is present, also coincidentally the [introduction new Vector implementation](https://github.com/scala/scala/releases/tag/v2.13.2))
  * `++2.13.1 run` (no error!)

Tested with Java 11 and 17.

```
Exception in thread "sbt-bg-threads-6" java.lang.InstantiationError: scala.collection.immutable.Vector
	at jdk.internal.reflect.GeneratedSerializationConstructorAccessor3.newInstance(Unknown Source)
	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:500)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:481)
	at com.esotericsoftware.kryo.kryo5.objenesis.instantiator.sun.SunReflectionFactoryInstantiator.newInstance(SunReflectionFactoryInstantiator.java:48)
	at com.esotericsoftware.kryo.kryo5.Kryo.newInstance(Kryo.java:1202)
	at io.altoo.serialization.kryo.scala.serializer.ScalaCollectionSerializer.read(ScalaCollectionSerializer.scala:33)
	at io.altoo.serialization.kryo.scala.serializer.ScalaCollectionSerializer.read(ScalaCollectionSerializer.scala:29)
	at com.esotericsoftware.kryo.kryo5.Kryo.readClassAndObject(Kryo.java:880)
	at io.altoo.serialization.kryo.scala.KryoSerializerBackend.fromBinary(KryoSerializerBackend.scala:61)
	at io.altoo.serialization.kryo.scala.KryoSerializer.fromBinaryInternal(KryoSerializer.scala:183)
	at io.altoo.serialization.kryo.scala.ScalaKryoSerializer.$anonfun$deserialize$1(ScalaKryoSerializer.scala:23)
	at scala.util.Try$.apply(Try.scala:217)
	at io.altoo.serialization.kryo.scala.ScalaKryoSerializer.deserialize(ScalaKryoSerializer.scala:23)
	at com.example.ScalaKryoVectorErrorExample$.delayedEndpoint$com$example$ScalaKryoVectorErrorExample$1(VectorSerializationExample.scala:23)
	at com.example.ScalaKryoVectorErrorExample$delayedInit$body.apply(VectorSerializationExample.scala:9)
	at scala.Function0.apply$mcV$sp(Function0.scala:42)
	at scala.Function0.apply$mcV$sp$(Function0.scala:42)
	at scala.runtime.AbstractFunction0.apply$mcV$sp(AbstractFunction0.scala:17)
	at scala.App.$anonfun$main$1(App.scala:98)
	at scala.App.$anonfun$main$1$adapted(App.scala:98)
	at scala.collection.IterableOnceOps.foreach(IterableOnce.scala:619)
	at scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:617)
	at scala.collection.AbstractIterable.foreach(Iterable.scala:935)
	at scala.App.main(App.scala:98)
	at scala.App.main$(App.scala:96)
	at com.example.ScalaKryoVectorErrorExample$.main(VectorSerializationExample.scala:9)
	at com.example.ScalaKryoVectorErrorExample.main(VectorSerializationExample.scala)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:569)
	at sbt.Run.invokeMain(Run.scala:144)
	at sbt.Run.execute$1(Run.scala:94)
	at sbt.Run.$anonfun$runWithLoader$5(Run.scala:121)
	at sbt.Run$.executeSuccess(Run.scala:187)
	at sbt.Run.runWithLoader(Run.scala:121)
	at sbt.Defaults$.$anonfun$bgRunTask$6(Defaults.scala:1986)
	at sbt.Defaults$.$anonfun$termWrapper$2(Defaults.scala:1925)
	at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.java:23)
	at scala.util.Try$.apply(Try.scala:213)
	at sbt.internal.BackgroundThreadPool$BackgroundRunnable.run(DefaultBackgroundJobService.scala:367)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	at java.base/java.lang.Thread.run(Thread.java:840)
```