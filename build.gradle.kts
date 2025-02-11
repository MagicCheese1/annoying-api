plugins {
    java
}

// Prevent java tasks from running for root project
tasks {
    classes { enabled = false }
    jar { enabled = false }
    assemble { enabled = false }
    testClasses { enabled = false }
    check { enabled = false }
    build { enabled = false }
}

subprojects {
    version = "2.0.5"
    group = "xyz.srnyx"

    apply(plugin = "java")

    repositories {
        mavenCentral() // org.spigotmc:spigot, net.md-5:bungeecord-api (api)
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots") // org.spigotmc:spigot-api
        maven("https://oss.sonatype.org/content/repositories/snapshots") // org.spigotmc:spigot-api
    }

    dependencies {
        compileOnly("org.spigotmc", "spigot-api", "1.11-R0.1-SNAPSHOT")
    }

    // Set Java version
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks {
        // Text encoding
        compileJava {
            options.encoding = "UTF-8"
        }

        // Replace '${name}' and '${version}' in 'plugin.yml'
        processResources {
            inputs.property("name", project.name)
            inputs.property("version", version)
            filesMatching("**/plugin.yml") {
                expand("name" to project.name, "version" to version)
            }
        }
    }
}
