package podcast.microservice.dev.devcapivara.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.mongodb.scala.bson.collection.mutable.Document
import podcast.microservice.dev.devcapivara.api.DevcapivaraService
import podcast.microservice.dev.devcapivara.api.models.registerpattern.CapivaraInternalComunication
import podcast.microservice.dev.devcapivara.api.models.{CapivaraDTO, CapivaraListDTO, MessageDTO}
import podcast.microservice.dev.devcapivara.impl.repository.CapivaraDAO

import scala.concurrent.ExecutionContext

/**
  * Implementation of the DevcapivaraService.
  */
class DevcapivaraServiceImpl(capivaraDao: CapivaraDAO)(implicit ec: ExecutionContext)
  extends DevcapivaraService {


  override def addCapivaras(): ServiceCall[CapivaraInternalComunication, MessageDTO] = { request =>
    val capivara = Document("name"->request.name, "age"->request.age, "color"->request.color, "userId"->request.userId)
    capivaraDao.insert(capivara).map{ _ =>
      MessageDTO("Capivara adicionada com sucesso", "capivara.insert.success")
    }
  }

  override def allCapivaras(): ServiceCall[NotUsed, CapivaraListDTO] = ServiceCall { _ =>
    capivaraDao.getAll().map{ capivaraList =>
      CapivaraListDTO(
        capivaraList.map{ capivara =>
          CapivaraDTO(
            capivara.get("name").get.asString.getValue,
            capivara.get("age").get.asInt32.getValue,
            capivara.get("color").get.asString.getValue,
            Option.empty
          )
        }.toList
      )
    }
  }

  override def getCapivarasByIdUser(id: Long): ServiceCall[NotUsed, CapivaraListDTO] = { _ =>
    capivaraDao.getByUserId(id).map{ response =>
      CapivaraListDTO(
          response.map{ capivara =>
          CapivaraDTO(
            capivara.get("name").get.asString.getValue,
            capivara.get("age").get.asInt32.getValue,
            capivara.get("color").get.asString.getValue,
            Option.empty
          )
        }.toList
      )
    }
  }
}
