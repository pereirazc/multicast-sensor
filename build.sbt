import play.Project._

name := "multicast-sensor"

version := "0.1.1"

libraryDependencies ++= Seq(
  "org.webjars" % "jquery" % "1.9.1",
  "org.webjars" % "bootstrap" % "3.0.3" exclude("org.webjars", "jquery"),
  "org.webjars" % "webjars-play_2.10" % "2.2.0",
  "org.webjars" % "font-awesome" % "4.0.3",
  "com.google.code.gson" % "gson" % "2.2.4",
  "org.drools" % "knowledge-api" % "5.5.0.Final",
  "org.drools" % "drools-core" % "5.5.0.Final",
  "org.drools" % "drools-compiler" % "5.5.0.Final",
  "br.ufes.inf.lprm" % "situation-api" % "0.9.1",
  "br.ufes.inf.lprm" % "scene-core" % "0.9.1"
)

playJavaSettings

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/repo"

resolvers += "JBoss Public Repository Group" at "http://repository.jboss.org/nexus/content/groups/public/"

resolvers += "Situation API Repo" at "https://raw.github.com/pereirazc/SCENE/mvn-repo"

resolvers += "Maven Repo" at "http://repo1.maven.org/maven2/"

