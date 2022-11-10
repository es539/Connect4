name := "Connect-4"

version := "1.0.6"

scalaVersion := "2.13.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-Ymacro-annotations")

Compile / resourceDirectory := (Compile / scalaSource).value
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "18.0.1-R27",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test"
)

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "18.0.1" classifier osName)
libraryDependencies += "com.jfoenix" % "jfoenix" % "9.0.10"
libraryDependencies += "guru.nidi" % "graphviz-java" % "0.18.1"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.13.0"
libraryDependencies += "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.13.0"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true