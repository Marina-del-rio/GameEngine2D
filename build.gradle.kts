plugins {
    java
    id("application") // <-- AÑADIR ESTA LÍNEA
    // El plugin de Spring Boot debe estar aquí para gestionar el proyecto
    id("org.springframework.boot") version "3.2.0"
    // Este plugin gestiona las versiones de las dependencias para evitar conflictos
    id("io.spring.dependency-management") version "1.1.4"
    // Mantenemos el plugin de JavaFX
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.game"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Configuración de JavaFX (se mantiene igual)
javafx {
    version = "21" // No es necesario el .0.6, con 21 es suficiente
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
// Spring Data JPA + Hibernate
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Driver MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // Spring Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Validación
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("com.h2database:h2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// La tarea 'bootRun' de Spring Boot ejecutará la aplicación
application {
    mainClass.set("com.game.Main") // Asegúrate de que tu clase Main está en el paquete com.game
}