package podcast.microservice.dev.user.api.models

import play.api.libs.json.{Format, Json}

case class UserDTO(id: Long, name:String, email: String)

object UserDTO {
  implicit val format: Format[UserDTO] = Json.format[UserDTO]
}