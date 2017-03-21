### Processing RabbitMQ's stream with Apache Spark (Scala implementation)

(Using "sbt" as build tool)
<br>
`Spark version: 1.6.0`
<br>
`Scala version: 2.10.5`

Building & Execution:
<br>
`sbt package`
<br>
`sbt run`


TODO:
<br> 1. Logging
<br> 2. Refactor the RabbitmqSparkStreamHandler class


`spark-submit --class simpl_spark_cassandra.Main  target/scala-2.10/rabbitmq_spark_stream_processor_2.10-1.0.jar`

