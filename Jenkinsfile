/*
 =============================================================================
  Jenkinsfile – Enterprise CI Pipeline (SDET Level)
 =============================================================================
*/

pipeline {

    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    /*************** NIGHTLY SCHEDULE ****************/
    triggers {
        cron('H 1 * * *')   // Runs daily around 1 AM
    }

    parameters {
        choice(name: 'ENV', choices: ['QA', 'UAT'], description: 'Target Environment')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Browser')
        choice(name: 'SUITE', choices: ['sanity', 'regression'], description: 'Test Suite')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Headless Mode')
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Validate Inputs') {
            steps {
                echo "ENV      : ${params.ENV}"
                echo "BROWSER  : ${params.BROWSER}"
                echo "SUITE    : ${params.SUITE}"
                echo "HEADLESS : ${params.HEADLESS}"
            }
        }

        stage('Execute Tests') {
            steps {
                script {
                    def suiteFile = params.SUITE == 'sanity'
                            ? 'testng_sanity.xml'
                            : 'testng.xml'

                    bat """
                        mvn clean test ^
                        -DsuiteXmlFile=${suiteFile} ^
                        -Denv=${params.ENV} ^
                        -Dbrowser=${params.BROWSER} ^
                        -Dheadless=${params.HEADLESS}
                    """
                }
            }
        }
    }

    /*************** REPORTS & EMAIL ****************/
    post {

        always {
            // Publish TestNG results
            junit 'target/surefire-reports/*.xml'

            // Archive screenshots
            archiveArtifacts artifacts: '**/screenshots/*.png', allowEmptyArchive: true

            // Publish Extent Report
            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Automation Test Report',
                keepAll: true,
                alwaysLinkToLastBuild: true
            ])
        }

        success {
            emailext(
                subject: "✅ BUILD SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <h2 style="color:green;">Automation Execution Successful</h2>
                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Environment:</b> ${params.ENV}</p>
                <p><a href="${env.BUILD_URL}">🔗 View Jenkins Build</a></p>
                """,
                mimeType: 'text/html',
                to: 'deveshsinghup70@gmail.com',
                attachmentsPattern: 'test-output/ExtentReport.html,**/screenshots/*.png'
            )
        }

        failure {
            emailext(
                subject: "❌ BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <h2 style="color:red;">Automation Execution Failed</h2>
                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Environment:</b> ${params.ENV}</p>
                <p><a href="${env.BUILD_URL}">🔗 View Jenkins Logs</a></p>
                """,
                mimeType: 'text/html',
                to: 'deveshsinghup70@gmail.com',
                attachmentsPattern: 'test-output/ExtentReport.html,**/screenshots/*.png'
            )
        }
    }
}
