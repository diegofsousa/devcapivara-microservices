package podcast.microservice.dev.devcapivara.api.models

import play.api.libs.json.{Format, Json}

case class CapivaraListDTO(data: List[CapivaraDTO])

object CapivaraListDTO {
  implicit val format: Format[CapivaraListDTO] = Json.format[CapivaraListDTO]
}