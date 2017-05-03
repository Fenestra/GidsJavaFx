name := "GidsJavaFx"

version := "1.0"

scalaVersion := "2.12.2"

mainClass in (Compile, run) := Some("GidsMain")

jfxSettings

JFX.mainClass := Some("GidsMain")

JFX.j2se := Some("1.8+")

JFX.vendor := "Westat"

JFX.description := "Prototype of GIDS functionality for the web."

JFX.elevated := true

JFX.keyStore := Some(new File("FenestraCertificate.p12"))

JFX.storePass := Some("lisa1535")

JFX.alias := Some("fenestra")

JFX.keyPass := Some("lisa1535")

