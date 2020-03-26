package podcast.microservice.dev.user.api.models.registerpattern

import play.api.libs.json.{JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._

case class UserRegisterForm (name:String, email: String)

object  UserRegisterForm{
  implicit val writes = new Writes[UserRegisterForm]{
    def writes(userForm: UserRegisterForm) = Json.obj(
      "name" -> userForm.name,
      "email" -> userForm.email
    )
  }

  implicit val reads: Reads[UserRegisterForm] = (
    (JsPath \ "name").read[String](Reads.minLength[String](1) keepAnd Reads.maxLength[String](150)) and
      (JsPath \ "email").read[String](Reads.email)
    ) (UserRegisterForm.apply _)
}
