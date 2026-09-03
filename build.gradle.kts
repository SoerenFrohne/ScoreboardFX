plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "4.1.1"
    id("io.freefair.lombok") version "9.5.0"
}

group = "de.tvneheim"
version = "1.0.0"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("de.tvneheim.scoreboardfx")
    mainClass.set("de.tvneheim.scoreboardfx.MainApplication")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web")
}

dependencies {
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0") {
        exclude(group = "org.openjfx")
    }
    implementation("org.kordamp.ikonli:ikonli-javafx:12.4.0")
    implementation("org.kordamp.ikonli:ikonli-fontawesome6-pack:12.4.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.1")
    implementation("io.github.mkpaz:atlantafx-base:2.0.1")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("com.h2database:h2:2.4.240")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation("org.hamcrest:hamcrest:3.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.testfx:testfx-junit5:4.0.18")
    testImplementation("org.testfx:testfx-core:4.0.18")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))

    addExtraDependencies("slf4j")

    launcher {
        name = "ScoreboardFX"
    }

    jpackage {
        imageName = "ScoreboardFX"
        installerName = "ScoreboardFX"
        appVersion = "1.0.0"

        installerType = "exe"

        installerOptions = listOf(
            "--win-dir-chooser",
            "--win-menu",
            "--win-shortcut"
        )

    }
}
