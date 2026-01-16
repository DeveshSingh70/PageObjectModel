/*
 =============================================================================
  Jenkinsfile – Enterprise CI Pipeline for Test Automation
 -----------------------------------------------------------------------------
  Tech Stack : Java | Selenium | TestNG | Maven | Jenkins | Extent Reports
  Author     : Senior SDET
  Purpose    : CI execution with parameterized, environment-based test runs
 =============================================================================
*/

pipeline {

    /*************** AGENT CONFIGURATION ****************/
    agent any

    /*************** TOOLS CONFIGURATION ****************/
    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    /*************** PIPELINE PARAMETERS ****************/
    parameters {
        choice(
            name: 'ENV',
            choices: ['QA', 'UAT'],
            description: 'Select target environment'
        )
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Select browser'
        )
        choice(
            name: 'SUITE',
            choices: ['sanity', 'regression'],
            description: 'Select test suite'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run tests in headless mode'
        )
    }

    /*************** ENVIRONMENT VARIABLES ****************/
    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {

        /*************** STAGE 1: CODE CHECKOUT ****************/
        stage('Checkout Source Code') {
            steps {
                checkout scm
            }
        }

        /*************** STAGE 2: VALIDATION ****************/
        stage('Validate Inputs') {
            steps {
                echo "Environment : ${params.ENV}"
                echo "Browser     : ${params.BROWSER}"
                echo "Suite       : ${params.SUITE}"
                echo "Headless    : ${params.HEADLESS}"
            }
        }

        /*************** STAGE 3: TEST EXECUTION ****************/
        stage('Execute Automation Tests') {
            steps {
                script {
                    def suiteFile = (params.SUITE == 'sanity')
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
}
