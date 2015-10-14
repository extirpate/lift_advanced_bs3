name := "Lift v2.6 Template Application with Bootstrap v3"

version := "0.0.5"

organization := "net.liftweb"

scalaVersion := "2.11.2"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                  "staging"       at "https://oss.sonatype.org/content/repositories/staging",
                  "releases"      at "https://oss.sonatype.org/content/repositories/releases",
                  "Sonatype.org Releases" at "https://oss.sonatype.org/content/repositories/releases/"
                 )

seq(webSettings :_*)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  //val liftEdition = liftVersion.substring(0,3)
  //val ngVersion = "1.3.15"
  //val liftNgJsVersion = "0.2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "net.liftmodules"   %% "fobo_2.6"           % "1.3"     % "compile",
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty" % "jetty-plus"          % "8.1.7.v20120910"  % "container,test", // For Jetty Config
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        %% "specs2"             % "2.3.12"           % "test",
    "com.h2database"    % "h2"                  % "1.3.167",
    //"net.liftmodules" %% ("ng-js_"+liftVersion) % (liftNgJsVersion+"_"+ngVersion) % "compile"
    //"net.liftmodules" %% ("ng_"+liftEdition)    % "0.6.4"            % "compile",
    //"net.liftmodules" %% ("ng-js_"+liftEdition) % (liftNgJsVersion+"_"+ngVersion) % "compile"
    "com.softwaremill.macwire" %% "macros" % "1.0.5"
  )
}

