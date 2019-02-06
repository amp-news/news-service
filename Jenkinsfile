pipeline {
    agent any
    post {
      failure {
        updateGitlabCommitStatus name: 'build', state: 'failed'
      }
      success {
        updateGitlabCommitStatus name: 'build', state: 'success'
      }
    }
    options {
      gitLabConnection('Gitlab')
      gitlabBuilds(builds: ["Prepare", "Build", "Test"])
    }
    triggers {
        gitlab(triggerOnPush: true, triggerOnMergeRequest: true, branchFilterType: 'All')
    }
    stages {
      stage("Prepare") {
        steps {
          gitlabCommitStatus(name: "Prepare") {
            bat 'gradlew clean'
          }
        }
      }

      stage("Build") {
        steps {
          gitlabCommitStatus(name: "Build") {
            bat 'gradlew build -x test'
          }
        }
      }

      stage("Test") {
        steps {
          gitlabCommitStatus(name: "Test") {
            bat 'gradlew test'
          }
        }
      }
    }
}