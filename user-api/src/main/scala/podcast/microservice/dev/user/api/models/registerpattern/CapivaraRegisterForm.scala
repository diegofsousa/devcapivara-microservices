package podcast.microservice.dev.user.api.models.registerpattern

import play.api.libs.json.{Format, JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._


case class CapivaraRegisterForm(name: String, age: Int, color: String, userId: Option[Long])

object CapivaraRegisterForm {

  implicit val writes = new Writes[CapivaraRegisterForm]{
    def writes(userForm: CapivaraRegisterForm) = Json.obj(
      "name" -> userForm.name,
      "age" -> userForm.age,
      "color" -> userForm.color
    )
  }

  implicit val reads: Reads[CapivaraRegisterForm] = (
    (JsPath \ "name").read[String](Reads.minLength[String](1)) and
      (JsPath \ "age").read[Int] and
      (JsPath \ "color").read[String] and
      (JsPath \ "userId").readNullable[Long]
    ) (CapivaraRegisterForm.apply _)
}
