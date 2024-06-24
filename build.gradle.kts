plugins {
    id("java")
}

group = "net.botwithus"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        setUrl("https://nexus.botwithus.net/repository/maven-snapshots/")
    }
}

val includeInJar by configurations.creating {
    isTransitive = false
}

tasks.named<Jar>("jar") {
    finalizedBy(copyJar)
}

dependencies {
    implementation("net.botwithus.rs3:botwithus-api:1.0.0-SNAPSHOT")
    implementation("com.google.flogger:flogger:0.7.4")
    implementation("com.google.flogger:flogger-system-backend:0.7.4")
    implementation("org.tmatesoft.sqljet:sqljet:1.1.15")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "20"
    targetCompatibility = "20"
    options.compilerArgs.add("--enable-preview")
}

val copyJar = tasks.create("copyJar", Copy::class.java) {
    from(tasks.withType<Jar>())
    into("${System.getProperty("user.home")}\\BotWithUs\\scripts\\local\\")
}

tasks.withType<Jar> {
    finalizedBy(copyJar)
}

tasks.test {
    useJUnitPlatform()
}