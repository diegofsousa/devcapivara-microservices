package podcast.microservice.dev.devcapivara.api.models

import play.api.libs.json.{Format, Json}

case class MessageDTO(message: String, key: String)

object MessageDTO {
  implicit val format: Format[MessageDTO] = Json.format[MessageDTO]
}
