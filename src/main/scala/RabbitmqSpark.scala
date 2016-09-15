package simpl_spark_cassandra

import org.apache.spark.streaming.rabbitmq.RabbitMQUtils
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.Time
import com.typesafe.config.Config

class RabbitmqSpark(val configurations: Config) {

  def getRabbitmqConfig() : Map[String, String] = {
    return Map(
      "hosts"         -> configurations.getString("hosts"),
      "queueName"     -> configurations.getString("queueName"),
      "exchangeName"  -> configurations.getString("exchangeName"),
      "exchangeType"  -> configurations.getString("exchangeType"),
      "vHost"         -> configurations.getString("vHost"),
      "userName"      -> configurations.getString("userName"),
      "password"      -> configurations.getString("password")
    )
  }

  def execute() {
    println("Creating Streaming context..")
    val conf = new SparkConf().setAppName("simpl-rabbitmq").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))

    println("Creating rabbit stream receiver..")
    val receiverStream = RabbitMQUtils.createStream[String](ssc, getRabbitmqConfig())
    receiverStream.start()

    println("Ready for processing..")
    receiverStream.foreachRDD(r => {
      if (!r.isEmpty()) {
        r.collect().foreach(println)
      } else {
        println("_Nothing arrived yet..")
      }
    })

    println("Initiating stream consumer..")
    ssc.start()
    ssc.awaitTermination()
  }

}
