import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackOutput.Target.COMMONJS
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.DEVELOPMENT
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.PRODUCTION
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackOutput.Target.UMD

buildscript {
    repositories {
        jcenter()
    }

    configurations.classpath {
        resolutionStrategy.activateDependencyLocking()
    }

    extra.set("production", (findProperty("production") ?: "false") == "true")
}

plugins {
    kotlin("js") version "1.3.72"
}

group = "katas.todomvc"
version = "1.0-SNAPSHOT"

val webDir = file("src/main/web")
val isProductionBuild = extra.get("production") as Boolean

repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers/")
}

configurations.all {
    resolutionStrategy.activateDependencyLocking()

    if (isProductionBuild) {
        resolutionStrategy {
            // TODO: different kotlin dependency versions
            // failOnVersionConflict()
            failOnDynamicVersions()
            failOnChangingVersions()
            failOnNonReproducibleResolution()
        }
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(kotlin("stdlib-js"))

    implementation("org.jetbrains:kotlin-react:16.13.0-pre.94-kotlin-1.3.70")
    implementation("org.jetbrains:kotlin-react-dom:16.13.0-pre.94-kotlin-1.3.70")
    implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.94-kotlin-1.3.70")
    implementation("org.jetbrains:kotlin-react-router-dom:4.3.1-pre.94-kotlin-1.3.70")
    implementation("org.jetbrains:kotlin-redux:4.0.0-pre.94-kotlin-1.3.70")

    implementation(npm("core-js", "3.6.5"))
    implementation(npm("react", "16.13.0"))
    implementation(npm("react-is", "16.13.0"))
    implementation(npm("react-dom", "16.13.0"))
    implementation(npm("react-redux", "5.0.7"))
    implementation(npm("react-router-dom", "4.3.1"))
    implementation(npm("redux", "4.0.0"))

    // Kotlin Styled
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.94-kotlin-1.3.70")
    implementation(npm("inline-style-prefixer", "6.0.0"))
    implementation(npm("styled-components", "5.1.0"))

    // Webpack
    implementation(npm("html-webpack-plugin", "4.2.0"))
    implementation(npm("style-loader", "1.1.3"))
    implementation(npm("css-loader", "3.5.2"))
}

tasks {
    named<Wrapper>("wrapper") {
        gradleVersion = project.property("gradle-wrapper.version") as String
        distributionType = ALL
    }
}

val resourcesDir = "${project.buildDir}/processedResources/Js/main"

kotlin {
    sourceSets {
        all {
            languageSettings.apply {
                apiVersion = "1.3"
                languageVersion = "1.3"
                progressiveMode = true
                enableLanguageFeature("InlineClasses")
                enableLanguageFeature("NewInference")
                useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
            }
        }
        named("main") {
            kotlin.srcDir("src/main/kotlinX")
            resources.srcDir(webDir)
        }
        named("test") {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
    }

    target {
        browser {
            compilations.all {
                kotlinOptions {
                    friendModulesDisabled = false
                    metaInfo = true
                    moduleKind = COMMONJS
                    main = "call"
                    sourceMap = !isProductionBuild
                    sourceMapEmbedSources =  if(!isProductionBuild) "always" else "never"

                    allWarningsAsErrors = false
                }
            }
            runTask {
                outputFileName = "main.bundle.js"
                mode = if (!isProductionBuild) DEVELOPMENT else PRODUCTION
                output.libraryTarget = UMD
                report = false
                sourceMaps = !isProductionBuild
                devServer = DevServer(
                    open = true,
                    port = 3000,
                    contentBase = listOf(resourcesDir)
                )
            }
            webpackTask {
                sourceMaps = false
                outputFileName = "main.bundle.js"
            }
            dceTask {
            }
            testTask {
                enabled = true
                useKarma {
                    useChromeHeadless()
                }
            }
            distribution {
                directory = File("${buildDir}/distribution/")
            }
        }
    }
}

