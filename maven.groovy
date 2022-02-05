/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/
def call(){
  stage("Paso 1: Build & Test"){
    sh "mvn clean compile -e"
    sh "mvn clean test -e"
    sh "mvn clean package -e"
  }
  stage("Paso 2: Sonar - Análisis Estático"){
      withSonarQubeEnv('sonarqube') {
          sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=maven-gradle -Dsonar.java.binaries=build'
      }
  }
  stage("Paso 3: Curl Springboot Maven sleep 20"){
      sh "mvn spring-boot:run&"
      sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
  }
  stage("Paso 4: Subir Nexus"){
      nexusPublisher nexusInstanceId: 'nexus',
      nexusRepositoryId: 'devops-usach-nexus',
      packages: [
          [$class: 'MavenPackage',
              mavenAssetList: [
                  [classifier: '',
                  extension: 'jar',
                  filePath: 'build/DevOpsUsach2020-0.1.2.jar'
              ]
          ],
              mavenCoordinate: [
                  artifactId: 'DevOpsUsach2020',
                  groupId: 'com.devopsusach2020',
                  packaging: 'jar',
                  version: '0.1.2'
              ]
          ]
      ]
  }
  stage("Paso 5: Descargar Nexus"){
      sh ' curl -X GET -u $NEXUS_USER:$NEXUS_PASSWORD "http://nexus:8081/repository/devops-usach-nexus/com/devopsusach2020/DevOpsUsach2020/0.1.2/DevOpsUsach2020-0.1.2.jar" -O'
  }
  stage("Paso 6: Levantar Artefacto Jar"){
      sh 'nohup bash java -jar DevOpsUsach2020-0.1.2.jar & >/dev/null'
  }
  stage("Paso 7: Testear Artefacto - Dormir(Esperar 20sg) "){
      sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
  }
}
return this;