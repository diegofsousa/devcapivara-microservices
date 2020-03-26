package podcast.microservice.dev.devcapivara.impl.repository

import javax.inject.Inject

import play.api.Configuration

import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{Completed,MongoClient,MongoCollection}
import play.api.Configuration

import scala.concurrent.{ExecutionContext,Future}

class CapivaraDAO@Inject()(config:Configuration)(implicit ec:ExecutionContext){
  private val mongoClient=MongoClient(config.get[String]("mongodb.uri"))
  protected val db=mongoClient.getDatabase(config.get[String]("mongodb.dbName"))

  def collection:MongoCollection[Document]=db.getCollection[Document]("users")

  def insert(mongoDocument:Document):Future[Completed]={
    collection.insertOne(mongoDocument).toFuture
  }

  def delete(id:String)={
    collection.deleteOne(equal("_id",id)).toFuture
  }

  def get(id:String)={
    collection.find(equal("_id",id)).first.toFuture
  }

  def getByUserId(id: Long) ={
    collection.find(equal("userId",id)).toFuture
  }

  def getAll()={
    collection.find().toFuture
  }
}
