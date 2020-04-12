import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL

tasks {
    register("clean"){
        description = "Clean all katas"
        dependsOn(gradle.includedBuilds.map{ it.task(":clean") })
    }

    register("assemble"){
        description = "Assemble all katas"
        dependsOn(gradle.includedBuilds.map{ it.task(":assemble") })
    }

    named<Wrapper>("wrapper"){
        gradleVersion = project.property("gradle-wrapper.version") as String
        distributionType = ALL
    }
}

defaultTasks("assemble")
