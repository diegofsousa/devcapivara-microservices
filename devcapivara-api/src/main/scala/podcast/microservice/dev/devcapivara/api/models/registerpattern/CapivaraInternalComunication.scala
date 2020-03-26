package podcast.microservice.dev.devcapivara.api.models.registerpattern

import play.api.libs.json.{JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._

case class CapivaraInternalComunication(name: String, age: Int, color: String, userId: Long)

object CapivaraInternalComunication {

  implicit val writes = new Writes[CapivaraInternalComunication]{
    def writes(userForm: CapivaraInternalComunication) = Json.obj(
      "name" -> userForm.name,
      "age" -> userForm.age,
      "color" -> userForm.color,
      "userId" -> userForm.userId
    )
  }

  implicit val reads: Reads[CapivaraInternalComunication] = (
    (JsPath \ "name").read[String](Reads.minLength[String](1)) and
      (JsPath \ "age").read[Int] and
      (JsPath \ "color").read[String] and
      (JsPath \ "userId").read[Long]
    ) (CapivaraInternalComunication.apply _)
}
