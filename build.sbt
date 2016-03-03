import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)
workbenchSettings

name := "kan"
version := "1.0"
scalaVersion := "2.11.7"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"
libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "0.10.4"

skip in packageJSDependencies := false

jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % "0.14.3"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "0.14.3"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM")

scalaJSUseRhino in Global := false

bootSnippet := "tutorial.webapp.TutorialApp().main();"
updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
