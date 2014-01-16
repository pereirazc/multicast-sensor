import play.Project._

name := "multicast-sensor"

version := "0.1.1"

libraryDependencies ++= Seq(
  "org.webjars" % "underscorejs" % "1.5.1",
  "org.webjars" % "jquery" % "1.9.1",
  "org.webjars" % "bootstrap" % "3.0.3" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.0.8" exclude("org.webjars", "jquery"),
  "org.webjars" % "requirejs" % "2.1.1",
  "org.webjars" % "webjars-play_2.10" % "2.2.0",
  "org.webjars" % "angular-ui-bootstrap" % "0.9.0",
  "org.webjars" % "font-awesome" % "4.0.3",
  "com.google.code.gson" % "gson" % "2.2.4",
  "org.drools" % "knowledge-api" % "5.5.0.Final",
  "org.drools" % "drools-core" % "5.5.0.Final",
  "org.drools" % "drools-compiler" % "5.5.0.Final",
  "br.ufes.inf.lprm" % "situation-api" % "0.9.1",
  "br.ufes.inf.lprm" % "scene-core" % "0.9.1"
)

playScalaSettings

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/repo"

resolvers += "JBoss Public Repository Group" at "http://repository.jboss.org/nexus/content/groups/public/"

resolvers += "Situation API Repo" at "https://raw.github.com/pereirazc/SCENE/mvn-repo"

resolvers += "Maven Repo" at "http://repo1.maven.org/maven2/"

// This tells Play to optimize this file and its dependencies
requireJs += "mainProd.js"

requireJsShim += "mainProd.js"

// The main config file
// See http://requirejs.org/docs/optimization.html#mainConfigFile
requireJsShim := "build.js"

// To completely override the optimization process, use this config option:
//requireNativePath := Some("node r.js -o name=main out=javascript-min/main.min.js")
