/*
 =============================================================================
  Jenkinsfile – Enterprise CI/CD Pipeline
  Role Level : SDET / Automation Engineer (10–12 LPA)
  Purpose    : End-to-end automation execution with reports & notifications
 =============================================================================
*/

pipeline {

    /**********************************************************************
     * Agent Declaration
     * - 'any' allows Jenkins to pick any available executor
     * - In enterprises, this can be replaced with labels or Docker agents
     **********************************************************************/
    agent any

    /**********************************************************************
     * Tool Configuration
     * - Tools are preconfigured in Jenkins Global Tool Configuration
     * - Ensures consistent Java & Maven versions across all builds
     **********************************************************************/
    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    /**********************************************************************
     * Build Trigger
     * - Nightly execution using cron
     * - 'H' distributes load across Jenkins nodes
     **********************************************************************/
    triggers {
        cron('H 1 * * *')   // Runs daily around 1 AM
    }

    /**********************************************************************
     * Parameters
     * - Enables reusable pipeline for multiple environments & browsers
     * - Common enterprise practice to avoid hardcoding
     **********************************************************************/
    parameters {
        choice(name: 'ENV', choices: ['QA', 'UAT'], description: 'Target Environment')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Browser Type')
        choice(name: 'SUITE', choices: ['sanity', 'regression'], description: 'Test Suite Type')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run browser in headless mode')
    }

    /**********************************************************************
     * Environment Variables
     * - JVM memory optimization for Maven builds
     **********************************************************************/
    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {

        /******************************************************************
         * Stage: Checkout Code
         * - Pulls source code from configured SCM (GitHub)
         * - Ensures pipeline always runs on latest committed code
         ******************************************************************/
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        /******************************************************************
         * Stage: Validate Inputs
         * - Prints runtime parameters for traceability
         * - Helps debugging environment-specific failures
         ******************************************************************/
        stage('Validate Inputs') {
            steps {
                echo "ENV      : ${params.ENV}"
                echo "BROWSER  : ${params.BROWSER}"
                echo "SUITE    : ${params.SUITE}"
                echo "HEADLESS : ${params.HEADLESS}"
            }
        }

        /******************************************************************
         * Stage: Execute Tests
         * - Dynamically selects TestNG suite based on input
         * - Uses Maven Surefire for execution
         ******************************************************************/
        stage('Execute Tests') {
            steps {
                script {

                    // Select suite file dynamically
                    def suiteFile = params.SUITE == 'sanity'
                            ? 'testng_sanity.xml'
                            : 'testng.xml'

                    // Windows batch execution
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

    /**********************************************************************
     * Post Build Actions
     * - Executed irrespective of build result
     * - Handles reporting, archiving, and notifications
     **********************************************************************/
    post {

        always {

            /**************************************************************
             * Publish TestNG XML Results
             * - Jenkins uses these XMLs for trend graphs & test history
             **************************************************************/
            junit 'target/surefire-reports/*.xml'

            /**************************************************************
             * Archive Screenshots
             * - Useful for debugging failed UI tests
             * - Stored as build artifacts in Jenkins
             **************************************************************/
            archiveArtifacts artifacts: '**/screenshots/*.png', allowEmptyArchive: true

            /**************************************************************
             * Publish Extent Report (HTML)
             * - Rich execution report with steps, logs, screenshots
             * - Visible directly inside Jenkins UI
             **************************************************************/
            publishHTML(
                target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Automation Report'
                ]
            )

            /**************************************************************
             * Publish TestNG HTML Report
             * - Default TestNG execution summary
             **************************************************************/
            publishHTML(
                target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output',
                    reportFiles: 'emailable-report.html',
                    reportName: 'TestNG Report'
                ]
            )
        }

        /******************************************************************
         * Email Notification – Success
         * - Sends detailed execution report to stakeholders
         ******************************************************************/
        success {
            emailext(
                subject: "✅ BUILD SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <h2 style="color:green;">Automation Execution Successful</h2>
                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Environment:</b> ${params.ENV}</p>
                <p><a href="${env.BUILD_URL}">View Jenkins Build</a></p>
                """,
                mimeType: 'text/html',
                to: 'deveshsinghup70@gmail.com',
                attachmentsPattern: 'test-output/ExtentReport.html,**/screenshots/*.png'
            )
        }

        /******************************************************************
         * Email Notification – Failure
         * - Alerts team immediately with logs & screenshots
         ******************************************************************/
        failure {
            emailext(
                subject: "❌ BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <h2 style="color:red;">Automation Execution Failed</h2>
                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Environment:</b> ${params.ENV}</p>
                <p><a href="${env.BUILD_URL}">View Jenkins Logs</a></p>
                """,
                mimeType: 'text/html',
                to: 'deveshsinghup70@gmail.com',
                attachmentsPattern: 'test-output/ExtentReport.html,**/screenshots/*.png'
            )
        }
    }
}
