package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import podcast.microservice.dev.devcapivara.api.DevcapivaraService
import podcast.microservice.dev.devcapivara.api.models.registerpattern.CapivaraInternalComunication
import podcast.microservice.dev.user.api.UserService
import podcast.microservice.dev.user.api.models.registerpattern.{CapivaraRegisterForm, UserRegisterForm}

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class DevCapivaraController @Inject()(val controllerComponents: ControllerComponents,
                                      val devCapivaraService: DevcapivaraService,
                                      val userService: UserService)(implicit ec: ExecutionContext) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def getAllCapivaras() = Action.async { _ =>
    devCapivaraService.allCapivaras.invoke.map{ response =>
      Ok(Json.toJson(response))
    }
  }

  def getAllUsers() = Action.async { _ =>
    userService.getUsers()
      .invoke.map{ response =>
      Ok(Json.toJson(response))
    }
  }

  def getUser(id: Long) = Action.async { _ =>
    userService.getUser(id)
      .invoke.map{ response =>
      Ok(Json.toJson(response))
    }
  }

  def addUser() = Action.async(parse.json) { request =>
    userService.addUser()
      .invoke(request.body.as[UserRegisterForm]).map{ response =>
      Created(Json.toJson(response))
    }
  }

  def addCapivara(id: Long) = Action.async(parse.json) { request =>
    userService.addCapivara(id)
      .invoke(request.body.as[CapivaraRegisterForm])
      .map{ response =>
        Created(Json.toJson(response))
      }
  }
  
}
