package simpl_spark_cassandra

import org.apache.spark.streaming.rabbitmq.RabbitMQUtils
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.Time
import com.typesafe.config.Config

abstract class RabbitmqSpark() {

  var spark_streaming_conf: SparkConf         = null
  var spark_streaming_ctx: StreamingContext   = null
  var sparkAppName: String                    = "RabbitMQ Stream Processor"
  var sparkMaster: String                     = "local[2]"
  var rabbitmqConfig: Map[String, String]     = null
  var fetchTimeInterval: Int                  = 3

  def setRabbitmqConfig(rabbitmq_config: Config) {
    rabbitmqConfig = Map(
      "hosts"         -> rabbitmq_config.getString("hosts"),
      "queueName"     -> rabbitmq_config.getString("queueName"),
      "exchangeName"  -> rabbitmq_config.getString("exchangeName"),
      "exchangeType"  -> rabbitmq_config.getString("exchangeType"),
      "vHost"         -> rabbitmq_config.getString("vHost"),
      "userName"      -> rabbitmq_config.getString("userName"),
      "password"      -> rabbitmq_config.getString("password")
    )
  }

  def setSparkStreamingConfig() {
    spark_streaming_conf = new SparkConf().setAppName(sparkAppName).setMaster(sparkMaster)
  }

  def createStreamingContext() {
    println("Creating Streaming context with fetch time: " + fetchTimeInterval)
    spark_streaming_ctx = new StreamingContext(spark_streaming_conf, Seconds(fetchTimeInterval))
  }

  def setup() {
    setSparkStreamingConfig()
    createStreamingContext()
  }

  def execute() {
    println("Creating rabbit stream receiver..")
    var receiverStream = RabbitMQUtils.createStream[String](spark_streaming_ctx, rabbitmqConfig)
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
    spark_streaming_ctx.start()
    spark_streaming_ctx.awaitTermination()
  }

  def getStreamingContext() : StreamingContext = {
    return spark_streaming_ctx
  }

}
