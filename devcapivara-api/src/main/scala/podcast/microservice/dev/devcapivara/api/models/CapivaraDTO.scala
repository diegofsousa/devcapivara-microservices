package podcast.microservice.dev.devcapivara.api.models

import play.api.libs.json.{Format, Json}

case class CapivaraDTO(name: String, age: Int, color: String, userId: Option[Long])

object CapivaraDTO {
  implicit val format: Format[CapivaraDTO] = Json.format[CapivaraDTO]
}
