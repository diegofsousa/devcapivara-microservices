package podcast.microservice.dev.user.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import podcast.microservice.dev.user.api.models.registerpattern.{CapivaraRegisterForm, UserRegisterForm}
import podcast.microservice.dev.user.api.models.{ListUserDTO, MessageDTO, UserWithCapivarasDTO}


/**
  * The user service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the DevcapivaraService.
  */
trait UserService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def getUsers(): ServiceCall[NotUsed, ListUserDTO]
  def addUser(): ServiceCall[UserRegisterForm, MessageDTO]
  def addCapivara(id: Long): ServiceCall[CapivaraRegisterForm, MessageDTO]
  def getUser(id: Long): ServiceCall[NotUsed, UserWithCapivarasDTO]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("user")
      .withCalls(
        restCall(Method.GET, "/api/user", getUsers _),
        restCall(Method.GET, "/api/user/:id", getUser _),
        restCall(Method.POST, "/api/user", addUser _),
        restCall(Method.POST, "/api/user/capivara/:id", addCapivara _)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}
