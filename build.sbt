import play.PlayScala
import com.typesafe.sbt.web.Import._
import com.typesafe.sbt.less.Import._
// Enable Play-Scala via its sbt auto-settings

// TODO Replace with your project's/module's name
name := "multicast-sensor"

version := "0.1.5"

// TODO Set your organization here
organization := "br.com.multicast"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
// Scala Version, Play supports both 2.10 and 2.11
//scalaVersion := "2.10.4"

scalaVersion := "2.11.1"

// Dependencies
libraryDependencies ++= Seq(
  filters,
  cache,
  // WebJars (i.e. client-side) dependencies
  "org.webjars" % "requirejs" % "2.1.11-1",
  "org.webjars" % "underscorejs" % "1.6.0-3",
  "org.webjars" % "jquery" % "1.11.1",
  "org.webjars" % "bootstrap" % "3.1.1-1" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.2.16-2" exclude("org.webjars", "jquery"),
  "org.webjars" % "angular-ui-router" % "0.2.10-1",
  "org.webjars" % "highcharts" % "3.0.10" exclude("org.webjars", "jquery"),
  "org.webjars" % "highcharts-ng" % "0.0.6",
  "org.webjars" % "angular-ui-bootstrap" % "0.11.0-2",
  "org.webjars" % "bootstrap-switch" % "3.0.1",
  "org.webjars" % "angular-bootstrap-switch" % "0.3.0-alpha.2",
  "org.webjars" % "font-awesome" % "4.0.3",
  "br.ufes.inf.lprm" % "scene-core" % "0.9.1" excludeAll(ExclusionRule(organization = "org.slf4j"))
)

includeFilter in (Assets, LessKeys.less) := "*.less"

//
// Scala Compiler Options
// If this project is only a subproject, add these to a common project setting.
 //
scalacOptions ++= Seq(
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused
)

//
// sbt-web configuration
// https://github.com/sbt/sbt-web
//

// Configure the steps of the asset pipeline (used in stage and dist tasks)
// rjs = RequireJS, uglifies, shrinks to one file, replaces WebJars with CDN
// digest = Adds hash to filename
// gzip = Zips all assets, Asset controller serves them automatically when client accepts them
pipelineStages := Seq(rjs, digest, gzip)

// RequireJS with sbt-rjs (https://github.com/sbt/sbt-rjs#sbt-rjs)
// ~~~
RjsKeys.paths += ("jsRoutes" -> ("/jsroutes" -> "empty:"))

//RjsKeys.mainModule := "main"
//RjsKeys.buildProfile := Map("paths" -> paths) // ++  RjsKeys.webJarModuleIds.value.map(m => m -> j"empty:").toMap.toJS

// Asset hashing with sbt-digest (https://github.com/sbt/sbt-digest)
// ~~~
// md5 | sha1
//DigestKeys.algorithms := "md5"
//includeFilter in digest := "..."
//excludeFilter in digest := "..."

// HTTP compression with sbt-gzip (https://github.com/sbt/sbt-gzip)
// ~~~
// includeFilter in GzipKeys.compress := "*.html" || "*.css" || "*.js"
// excludeFilter in GzipKeys.compress := "..."

// JavaScript linting with sbt-jshint (https://github.com/sbt/sbt-jshint)
// ~~~
// JshintKeys.config := ".jshintrc"

resolvers += "JBoss Public Repository Group" at "http://repository.jboss.org/nexus/content/groups/public/"

//resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "Situation API Repo" at "https://raw.github.com/pereirazc/SCENE/mvn-repo"

resolvers += "Maven Repo" at "http://repo1.maven.org/maven2/"
