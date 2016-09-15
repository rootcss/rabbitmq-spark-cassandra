name := "simpl_spark_cassandra"
version := "1.0"
scalaVersion := "2.10.5"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-sql"  % "1.6.0"
libraryDependencies += "org.apache.cassandra" % "cassandra-all" % "3.5"
libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.10" % "1.5.0-M2"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.0"
libraryDependencies += "com.stratio.receiver" % "spark-rabbitmq_1.6" % "0.3.0"
// libraryDependencies += "io.spray" %%  "spray-json" % "1.3.2"
resolvers += "Akka Repository" at "http://repo.akka.io/releases/"