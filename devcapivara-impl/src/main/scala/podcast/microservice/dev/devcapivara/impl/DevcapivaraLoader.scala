package podcast.microservice.dev.devcapivara.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomServer, _}
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents
import podcast.microservice.dev.devcapivara.api.DevcapivaraService
import podcast.microservice.dev.devcapivara.impl.repository.CapivaraDAO

import scala.collection.immutable

class DevcapivaraLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new DevcapivaraApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new DevcapivaraApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[DevcapivaraService])
}

abstract class DevcapivaraApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[DevcapivaraService](wire[DevcapivaraServiceImpl])
  lazy val capivaraDao: CapivaraDAO = wire[CapivaraDAO]

  lazy val jsonSerializerRegistry = new JsonSerializerRegistry {
    override def serializers: immutable.Seq[JsonSerializer[_]] = immutable.Seq.empty[JsonSerializer[_]]
  }

}
