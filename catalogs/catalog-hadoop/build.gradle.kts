/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
description = "catalog-hadoop"

plugins {
  `maven-publish`
  id("java")
  id("idea")
}

dependencies {
  implementation(project(":api"))
  implementation(project(":core"))
  implementation(project(":common"))

  implementation(libs.slf4j.api)
  implementation(libs.guava)
  implementation(libs.hadoop2.common)
  implementation(libs.hadoop2.hdfs)

  testImplementation(libs.junit.jupiter.api)
  testImplementation(libs.junit.jupiter.params)
  testRuntimeOnly(libs.junit.jupiter.engine)
  testImplementation(libs.mockito.core)
}

tasks {
  val copyDepends by registering(Copy::class) {
    from(configurations.runtimeClasspath)
    into("build/libs_all")
  }

  val copyCatalogLibs by registering(Copy::class) {
    dependsOn(copyDepends, "build")
    from("build/libs_all", "build/libs")
    into("$rootDir/distribution/package/catalogs/hadoop/libs")
  }

  val copyCatalogConfig by registering(Copy::class) {
    from("src/main/resources")
    into("$rootDir/distribution/package/catalogs/hadoop/conf")

    // TODO. add configuration file later on.

    rename { original ->
      if (original.endsWith(".template")) {
        original.replace(".template", "")
      } else {
        original
      }
    }

    exclude { details ->
      details.file.isDirectory()
    }
  }

  register("copyLibAndConfig", Copy::class) {
    dependsOn(copyCatalogConfig, copyCatalogLibs)
  }
}
