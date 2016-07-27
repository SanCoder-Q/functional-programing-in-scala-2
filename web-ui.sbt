lazy val webUI = root.in(file("web-ui"))
      .enablePlugins(ScalaJSPlugin)
      .settings(
          unmanagedSourceDirectories in Compile +=
            (scalaSource in (calculator, Compile)).value / "calculator",
          libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0",
          persistLauncher := true,
          coverageEnabled := false,
          coverageExcludedPackages := ".*"
      )
