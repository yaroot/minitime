import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "minitime"

ThisBuild / version := "0.2.1"
ThisBuild / scalaVersion := "2.12.7"

ThisBuild / organization := "net.pishen"
ThisBuild / licenses += "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")
ThisBuild / homepage := Some(url("https://github.com/pishen/minitime"))
ThisBuild / pomExtra := (
  <scm>
    <url>https://github.com/pishen/minitime.git</url>
    <connection>scm:git:git@github.com:pishen/minitime.git</connection>
  </scm>
  <developers>
    <developer>
      <id>pishen</id>
      <name>Pishen Tsai</name>
    </developer>
  </developers>
)

publish / skip := true

lazy val cross = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .settings(
    name := "minitime"
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC1"
    )
  )

lazy val crossJVM = cross.jvm
lazy val crossJS = cross.js
