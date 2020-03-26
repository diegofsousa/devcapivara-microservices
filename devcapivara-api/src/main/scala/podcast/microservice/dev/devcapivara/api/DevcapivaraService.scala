package podcast.microservice.dev.devcapivara.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Service.restCall
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import podcast.microservice.dev.devcapivara.api.models.{CapivaraDTO, CapivaraListDTO, MessageDTO}
import podcast.microservice.dev.devcapivara.api.models.registerpattern.CapivaraInternalComunication

object DevcapivaraService  {
  val TOPIC_NAME = "greetings"
}

/**
  * The devcapivara service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the DevcapivaraService.
  */
trait DevcapivaraService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def allCapivaras(): ServiceCall[NotUsed, CapivaraListDTO]
  def addCapivaras(): ServiceCall[CapivaraInternalComunication, MessageDTO]
  def getCapivarasByIdUser(id: Long): ServiceCall[NotUsed, CapivaraListDTO]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("devcapivara")
      .withCalls(
        restCall(Method.GET, "/api/capivara", allCapivaras _),
        restCall(Method.POST, "/api/capivara", addCapivaras _),
        restCall(Method.GET, "/api/capivarasByIdUser/:id", getCapivarasByIdUser _),
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}
