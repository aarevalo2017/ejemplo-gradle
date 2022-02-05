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
                    def ejecucion = load "${params.compileTool}.groovy"
                    ejecucion.call()
                }
            }
            post{
                success{
                    slackSend color: 'good', message: "[Su Nombre] [${JOB_NAME}] [${BUILD_TAG}] Ejecucion Exitosa", teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'slack-token'
                }
                failure{
                    slackSend color: 'danger', message: "[Su Nombre] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.TAREA}]", teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'slack-token'
                }
            }
        }
    }
}
