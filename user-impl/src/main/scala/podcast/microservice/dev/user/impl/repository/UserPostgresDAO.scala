package podcast.microservice.dev.user.impl.repository
import podcast.microservice.dev.user.api.models.UserDTO
import podcast.microservice.dev.user.impl.repository.converters.UserConverter
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class UserPostgresDAO (db: Database) (implicit executionContext: ExecutionContext){
  val userConverter = TableQuery[UserConverter]

  def add(user: UserDTO) = {
    db.run(userConverter += user)
  }

  def findById(id: Long): Future[Option[UserDTO]] = {
    db.run(userConverter.filter(_.id === id).result.headOption)
  }

  def findByEmail(email: String): Future[Option[UserDTO]] = {
    db.run(userConverter.filter(_.email === email).result.headOption)
  }

  def listAll: Future[Seq[UserDTO]] = {
    db.run(userConverter.sortBy(_.id.asc).result)
  }


}
