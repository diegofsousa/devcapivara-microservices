import com.lightbend.lagom.scaladsl.api.{LagomConfigComponent, ServiceAcl, ServiceInfo}
import com.lightbend.lagom.scaladsl.client.LagomServiceClientComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.softwaremill.macwire._
import controllers.{AssetsComponents, DevCapivaraController}
import play.api.ApplicationLoader.Context
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, Mode}
import play.filters.HttpFiltersComponents
import com.lightbend.lagom.scaladsl.akka.discovery.AkkaDiscoveryComponents
import podcast.microservice.dev.devcapivara.api.DevcapivaraService
import podcast.microservice.dev.user.api.UserService

import scala.collection.immutable
import scala.concurrent.ExecutionContext
import router.Routes

abstract class WebGateway(context: Context) extends BuiltInComponentsFromContext(context)
  with AssetsComponents
  with HttpFiltersComponents
  with AhcWSComponents
  with LagomConfigComponent
  with LagomServiceClientComponents {

  override lazy val serviceInfo: ServiceInfo = ServiceInfo(
    "gateway",
    Seq(ServiceAcl.forPathRegex("(?!/api/).*"))
  )

  override implicit lazy val executionContext: ExecutionContext = actorSystem.dispatcher

  override lazy val router = {
    val prefix = "/"
    wire[Routes]
  }

  lazy val devCapivaraService = serviceClient.implement[DevcapivaraService]
  lazy val userService = serviceClient.implement[UserService]
  lazy val gatewayController = wire[DevCapivaraController]
}

class WebGatewayLoader extends ApplicationLoader {
  override def load(context: Context) = context.environment.mode match {
    case Mode.Dev =>
      (new WebGateway(context) with LagomDevModeComponents).application
    case _ =>
      (new WebGateway(context) with AkkaDiscoveryComponents).application
  }
}