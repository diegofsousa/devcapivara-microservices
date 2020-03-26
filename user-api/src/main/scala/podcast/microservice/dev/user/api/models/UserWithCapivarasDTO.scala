package podcast.microservice.dev.user.api.models

import play.api.libs.json.{Format, Json}

case class UserWithCapivarasDTO(id: Long, name:String, email: String, capivaras: List[CapivaraDTO])

object UserWithCapivarasDTO {
  implicit val format: Format[UserWithCapivarasDTO] = Json.format[UserWithCapivarasDTO]
}
