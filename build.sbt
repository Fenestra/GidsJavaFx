name := "GidsJavaFx"

version := "1.0"

scalaVersion := "2.12.2"

assemblyJarName in assembly := "GidsJavaFx.jar"

mainClass in (Compile, run) := Some("main.GidsMain")

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-feature")

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
