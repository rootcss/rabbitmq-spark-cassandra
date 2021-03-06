package simpl_spark_cassandra

import simpl_spark_cassandra._
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import com.datastax.spark.connector._
import com.datastax.driver.core.utils.UUIDs
import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

object Main {
 def main(args: Array[String]) {
   val configurations = ConfigFactory.load("application.conf")

   Logger.getLogger("org").setLevel(Level.ERROR)
   Logger.getLogger("akka").setLevel(Level.ERROR)

   val x = new RabbitmqTest(configurations)
   x.executexx()
 }
}

class RabbitmqTest(var config: Config) extends RabbitmqSpark {
  def executexx() {
    setRabbitmqConfig(config.getConfig("rabbitmq"))
    fetchTimeInterval = 5
    setup()
    execute()
  }
}
