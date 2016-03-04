logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Spray repository" at "http://repo.spray.io"

addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.7")
