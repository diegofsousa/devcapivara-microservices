package podcast.microservice.dev.user.api.models

import play.api.libs.json.{Format, Json}

case class ListUserDTO (data: List[UserDTO])

object ListUserDTO {
  implicit val format: Format[ListUserDTO] = Json.format[ListUserDTO]
}