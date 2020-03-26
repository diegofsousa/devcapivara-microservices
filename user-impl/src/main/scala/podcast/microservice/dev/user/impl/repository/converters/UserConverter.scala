package podcast.microservice.dev.user.impl.repository.converters
import java.sql.Timestamp

import org.joda.time.DateTime
import podcast.microservice.dev.user.api.models.UserDTO
import slick.jdbc.PostgresProfile.api._

class UserConverter(tag: Tag) extends Table[UserDTO](tag, "user") {
  implicit val dateColumnType = MappedColumnType.base[DateTime, Timestamp](dateTime => new Timestamp(dateTime.getMillis), timestamp => new DateTime(timestamp.getTime))

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email")

  override def * = (id, name, email) <> ((UserDTO.apply(_:Long, _:String, _:String)).tupled, UserDTO.unapply)
}
