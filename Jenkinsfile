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
                    def branch = env.GIT_BRANCH.split('/')[1]
                    sh "echo $branch"
                    if(branch == "develop" || branch.startsWith("feature-")){
                        figlet "Integración Continua"
                        def ejecucion = load "pipelineci.groovy"
                        ejecucion.call()
                    } else if(branch.startsWith("release-")){
                        figlet "Despliegue Continuo"
                        def ejecucion = load "pipelinecd.groovy"
                        ejecucion.call()
                    }
                }
            }
        }
    }
}
