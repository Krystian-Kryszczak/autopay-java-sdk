import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "krystian.kryszczak"
version = "0.8.2-beta"

repositories {
  mavenCentral()
}

val jetbrainsAnnotationsVersion = "24.1.0"
val jakartaInjectApiVersion = "2.0.1"
val logbackVersion = "1.4.14"
val junitVersion = "5.9.3"
val lombokVersion = "1.18.30"
val reactorVersion = "3.6.2"

val vertxVersion = "4.5.1"
val junitJupiterVersion = "5.9.1"

val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
  mainClass.set(launcherClassName)
}

dependencies {
  testImplementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
  compileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")

  implementation("jakarta.inject:jakarta.inject-api:$jakartaInjectApiVersion")

  compileOnly("org.projectlombok:lombok:$lombokVersion")
  annotationProcessor("org.projectlombok:lombok:$lombokVersion")

  testCompileOnly("org.projectlombok:lombok:$lombokVersion")
  testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

  implementation("io.projectreactor:reactor-core:$reactorVersion")
  testImplementation("io.projectreactor:reactor-test:$reactorVersion")

  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-web-client")
  implementation("io.vertx:vertx-reactive-streams")

  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")

  implementation("commons-codec:commons-codec:1.15")

  implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
  implementation("com.fasterxml.jackson.core:jackson-core:2.15.0")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.0")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.0")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
  testImplementation("org.mockito:mockito-core:5.3.1")
  testImplementation("org.xmlunit:xmlunit-core:2.9.1")
  testImplementation("org.xmlunit:xmlunit-parent:2.9.1")
  testImplementation("org.xmlunit:xmlunit-placeholders:2.9.1")
  testImplementation("org.xmlunit:xmlunit-assertj:2.9.1")

  testImplementation("org.wiremock:wiremock:3.3.1")

  implementation("ch.qos.logback:logback-core:$logbackVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}
