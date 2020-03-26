package podcast.microservice.dev.devcapivara.api.models.registerpattern

import play.api.libs.json.{JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._

case class CapivaraFormRegister(name: String, age: Int, color: String, userId: Option[Long])

object CapivaraFormRegister {

  implicit val writes = new Writes[CapivaraInternalComunication]{
    def writes(userForm: CapivaraInternalComunication) = Json.obj(
      "name" -> userForm.name,
      "age" -> userForm.age,
      "color" -> userForm.color
    )
  }

  implicit val reads: Reads[CapivaraFormRegister] = (
    (JsPath \ "name").read[String](Reads.minLength[String](1)) and
      (JsPath \ "age").read[Int] and
      (JsPath \ "color").read[String] and
      (JsPath \ "userId").readNullable[Long]
    ) (CapivaraFormRegister.apply _)
}
