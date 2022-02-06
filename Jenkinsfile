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
                    def branch = env.GIT_BRANCH
                    sh "echo $branch"
                    if(branch == "develop" || branch.startsWith("feature-")){
                        pipeline_ci.call()
                    }else if(branch.startsWith("realease-")){
                        pipeline_cd.call();
                    }
                }
            }
        }
    }
}
