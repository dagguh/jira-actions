import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.3.30"

plugins {
    kotlin("jvm").version("1.3.30")
    `java-library`
    id("com.atlassian.performance.tools.gradle-release").version("0.5.0")
    id("org.jlleitschuh.gradle.ktlint").version("8.2.0")
}

configurations.all {
    resolutionStrategy {
        activateDependencyLocking()
        failOnVersionConflict()
        eachDependency {
            when (requested.module.toString()) {
                "commons-codec:commons-codec" -> useVersion("1.10")
                "org.jetbrains:annotations" -> useVersion("13.0")
                "org.slf4j:slf4j-api" -> useVersion("1.7.25")
            }
            when (requested.group) {
                "org.jetbrains.kotlin" -> useVersion(kotlinVersion)
            }
        }
    }
}

dependencies {
    api("com.github.stephenc.jcip:jcip-annotations:1.0-1")
    api(webdriver("selenium-api"))

    implementation(webdriver("selenium-support"))
    implementation(webdriver("selenium-chrome-driver"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.glassfish:javax.json:1.1")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("com.atlassian.performance.tools:concurrency:[1.0.0,2.0.0)")
    listOf(
        "api",
        "core",
        "slf4j-impl",
        "jul"
    ).map { module ->
        "org.apache.logging.log4j:log4j-$module:2.10.0"
    }.forEach { implementation(it) }
    testCompile("org.assertj:assertj-core:3.11.0")
    testCompile("com.atlassian.performance.tools:io:[1.0.0,2.0.0)")
    testCompile("com.atlassian.performance.tools:docker-infrastructure:0.1.2")
    testCompile("junit:junit:4.12")
}

tasks.test {
    testLogging.showStandardStreams = true
}

tasks
    .withType(KotlinCompile::class.java)
    .forEach { compileTask ->
        compileTask.apply {
            kotlinOptions.apply {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xjvm-default=enable")
            }
        }
    }

fun webdriver(module: String): String = "org.seleniumhq.selenium:$module:3.11.0"

tasks.wrapper {
    gradleVersion = "5.1.1"
    distributionType = Wrapper.DistributionType.ALL
}

tasks.getByName("test", Test::class).apply {
    exclude("**/*IT.class")
}

val testIntegration = task<Test>("testIntegration") {
    include("**/*IT.class")
}

ktlint {
    version.set("0.34.2")
    enableExperimentalRules.set(true)
}

tasks["ktlintCheck"].dependsOn(tasks["ktlintFormat"])
tasks["check"].dependsOn(testIntegration)
