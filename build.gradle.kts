plugins {
    kotlin("js") version "1.5.21"
}

group = "me.lerak"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:0.0.1-pre.234-kotlin-1.5.21")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-virtual:2.8.1-pre.235-kotlin-1.5.21")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.229-kotlin-1.5.21")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.229-kotlin-1.5.21")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.0-pre.229-kotlin-1.5.21")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:5.2.0-pre.229-kotlin-1.5.21")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.0-pre.229-kotlin-1.5.21")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.4-pre.229-kotlin-1.5.21")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}