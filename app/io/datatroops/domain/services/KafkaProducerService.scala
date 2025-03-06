package io.datatroops.domain.services

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import com.typesafe.config.Config
import io.datatroops.domain.errors.{ExceptionProducingBatchMessageToKafka, ExceptionProducingSingleMessageToKafka}
import io.datatroops.domain.models.TournamentPlayer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import play.api.Logging
import play.api.libs.json.Json

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KafkaProducerService @Inject()(implicit system: ActorSystem, ec: ExecutionContext) extends Logging {

  val config            = system.settings.config
  val kafkaConfig: Config =
    config.getConfig("akka.kafka")

  private val topicName = kafkaConfig.getString("producer.tournamentTopic")
  private val kafkaServer = kafkaConfig.getString("producer.bootstrap.servers")

  private val producerSettings: ProducerSettings[String, String] =
    ProducerSettings(system, new StringSerializer, new StringSerializer)
      .withBootstrapServers(kafkaServer)

  /**
   * Publish a TournamentPlayers object to Kafka.
   *
   * @param key              The key for the Kafka message.
   * @param tournamentPlayer The TournamentPlayers object to send.
   */
  def sendTournamentPlayer(key: String, tournamentPlayer: TournamentPlayer): Future[Either[ExceptionProducingSingleMessageToKafka, Done]] = {
    val jsonValue = Json.toJson(tournamentPlayer).toString()

    logger.info(s"Producing message to Kafka topic: $topicName, key: $key, value: $jsonValue")

    val record = new ProducerRecord[String, String](topicName, key, jsonValue)

    Source.single(record)
      .runWith(Producer.plainSink(producerSettings))
      .map { done => {
        logger.info(s"Message sent successfully to topic: $topicName");
        Right(done)
      }
      }
      .recover {
        case ex =>
          logger.error(s"Error sending message to Kafka: ${ex.getMessage}", ex)
          Left(ExceptionProducingSingleMessageToKafka(ex.getMessage))
      }
  }

  /**
   * Publish a batch of TournamentPlayers objects to Kafka.
   *
   * @param messages List of key-value pairs where value is a TournamentPlayers object.
   */
  def sendTournamentPlayersBatch(messages: Seq[(String, TournamentPlayer)]): Future[Either[ExceptionProducingBatchMessageToKafka, Done]] = {
    logger.info(s"Producing batch messages to Kafka topic: $topicName")

    val records = messages.map { case (key, tournamentPlayer) =>
      val jsonValue = Json.toJson(tournamentPlayer).toString()
      new ProducerRecord[String, String](topicName, key, jsonValue)
    }

    Source(records)
      .runWith(Producer.plainSink(producerSettings))
      .map(done => {
        logger.info(s"Batch messages sent successfully to topic: $topicName")
        Right(done)
      })
      .recover {
        case ex =>
          logger.error(s"Error sending batch messages to Kafka: ${ex.getMessage}", ex)
          Left(ExceptionProducingBatchMessageToKafka(ex.getMessage))
      }
  }
}
