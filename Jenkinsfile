pipeline {
    agent any
    environment {
        NEXUS_USERNAME = credentials('nexus-username')
        NEXUS_PASSWORD = credentials('nexus-password')
    }
    parameters {
        choice(
            name:'compileTool',
            choices: ['maven', 'gradle'],
            description: 'Seleccione herramienta de compilacion'
        )
    }
    stages {
        stage("Pipeline"){
            steps {
                script{
                    sh "git diff ${GIT_COMMIT} main"
                    // def ejecucion = load "${params.compileTool}.groovy"
                    // ejecucion.call()
                }
            }
            // post{
            //     success{
            //         slackSend color: 'good', message: "[Alejandro Arévalo] [${JOB_NAME}] [${BUILD_TAG}] Ejecucion Exitosa", teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'slack-token'
            //     }
            //     failure{
            //         slackSend color: 'danger', message: "[Alejandro Arévalo] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.TAREA}]", teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'slack-token'
            //     }
            // }
        }
    }
}
