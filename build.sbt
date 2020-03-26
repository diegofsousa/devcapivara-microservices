organization in ThisBuild := "podcast.microservice.dev"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.13.0"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val mongoScalaDrive = "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0"
val slick = "com.typesafe.slick" %% "slick" % "3.3.2"
val postgresql = "org.postgresql" % "postgresql" % "42.2.8"
val persistenceJdbc = "com.lightbend.lagom" %% "lagom-scaladsl-persistence-jdbc" % "1.6.1" % Test

def commonSettings: Seq[Setting[_]] = Seq(
)

lazy val `devcapivara` = (project in file("."))
  .aggregate(`devcapivara-api`, `devcapivara-impl`, `user-api`, `user-impl`, `gateway`)

lazy val `devcapivara-api` = (project in file("devcapivara-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `devcapivara-impl` = (project in file("devcapivara-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      macwire,
      scalaTest,
      mongoScalaDrive
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`devcapivara-api`)

lazy val `user-api` = (project in file("user-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `user-impl` = (project in file("user-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      macwire,
      scalaTest,
      slick,
      postgresql,
      lagomScaladslPersistenceJdbc
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`user-api`, `devcapivara-api`)

// val gatewayPlugins = Seq(PlayScala, LagomPlay, SbtReactiveAppPlugin)

lazy val `gateway` =(project in file("gateway"))
  .enablePlugins(PlayScala, LagomPlay)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire,
      scalaTest,
      lagomScaladslAkkaDiscovery
    ),
    lagomServiceHttpPort := 57948
  )
  .dependsOn(
    `devcapivara-api`,
    `user-api`
  ).settings(lagomServiceAddress := "0.0.0.0")

lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false