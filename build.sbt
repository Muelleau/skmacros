ThisBuild / version := "0.8.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"

scalacOptions ++= Seq(
  "-language:experimental.macros"
)

organization := "io.vigg"
publishMavenStyle := true
githubOwner := "Muelleau"
githubRepository := "skmacros"
githubTokenSource := TokenSource.GitConfig("github.token")

resolvers += Resolver.githubPackages("OWNER")
lazy val root = (project in file("."))
  .settings(
    name := "skmacros",
    crossPaths := false,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect",
      "org.scala-lang" % "scala-compiler"
    ).map(_ % "2.13.8"),
    libraryDependencies ++= Seq("io.getquill" %% "quill-cassandra" % "3.16.5"),
    libraryDependencies ++= Seq(
      "com.datastax.cassandra" % "cassandra-driver-core" % "3.11.2"
    )
  )
