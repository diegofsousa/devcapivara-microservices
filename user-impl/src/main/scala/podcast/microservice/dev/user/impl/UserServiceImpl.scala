package podcast.microservice.dev.user.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import podcast.microservice.dev.devcapivara.api.DevcapivaraService
import podcast.microservice.dev.devcapivara.api.models.registerpattern.CapivaraInternalComunication
import podcast.microservice.dev.user.api.UserService
import podcast.microservice.dev.user.api.models.registerpattern.{CapivaraRegisterForm, UserRegisterForm}
import podcast.microservice.dev.user.api.models.{CapivaraDTO, ListUserDTO, MessageDTO, UserDTO, UserWithCapivarasDTO}
import podcast.microservice.dev.user.impl.repository.UserPostgresDAO

import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation of the UserService.
  */
class UserServiceImpl(userPostgresDAO: UserPostgresDAO, devcapivaraService: DevcapivaraService)(implicit ec: ExecutionContext)
  extends UserService {

  override def getUsers(): ServiceCall[NotUsed, ListUserDTO] = { _ =>
    userPostgresDAO.listAll.map{ users =>
      ListUserDTO(users.toList)
    }
  }

  override def getUser(id: Long): ServiceCall[NotUsed, UserWithCapivarasDTO] = { _ =>
    userPostgresDAO.findById(id).flatMap{ user =>
      devcapivaraService
        .getCapivarasByIdUser(user.get.id)
        .invoke().map{ capivaraList =>
          UserWithCapivarasDTO(user.get.id,
                               user.get.name,
                               user.get.email,
                               capivaraList
                                 .data.map(c => CapivaraDTO(c.name,
                                                            c.age,
                                                            c.color,
                                                            Option.empty)))
      }
    }
  }

  override def addUser(): ServiceCall[UserRegisterForm, MessageDTO] = { requestUser =>
    userPostgresDAO.add(UserDTO(0,requestUser.name, requestUser.email)).map{ _ =>
        MessageDTO("Usuário adicionado com sucesso. ", "user.insert.success")
    }
  }

  override def addCapivara(id: Long): ServiceCall[CapivaraRegisterForm, MessageDTO] = { requestCapivara =>
    userPostgresDAO.findById(id).flatMap{ responseUser =>
      if (responseUser.isDefined){
        devcapivaraService.addCapivaras()
          .invoke(CapivaraInternalComunication(requestCapivara.name, requestCapivara.age, requestCapivara.color, id))
          .map{ _ =>
            MessageDTO("Capivara adicionada com sucesso", "capivara.insert.success")
          }
      } else {
        Future(MessageDTO("Erro ao adicionar capivara", "capivara.insert.fail"))
      }
    }

  }
}
