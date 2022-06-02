ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

organization := "io.vigg"
publishMavenStyle := true
githubOwner := "Muelleau"
githubRepository := "skmacros"
githubTokenSource := TokenSource.GitConfig("github.token")

resolvers += Resolver.githubPackages("OWNER")
lazy val root = (project in file("."))
  .settings(
    name := "skmacros",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect",
      "org.scala-lang" % "scala-compiler"
    ).map(_ % "2.13.8"),
    libraryDependencies ++= Seq("io.getquill" %% "quill-cassandra" % "3.16.5"),
    libraryDependencies ++= Seq(
      "com.datastax.cassandra" % "cassandra-driver-core" % "3.11.2"
    )
  )
