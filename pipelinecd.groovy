/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/
def call(){
  stage("Paso 5: Descargar Nexus"){
      sh "echo ${NEXUS_USER}"
      sh "echo ${NEXUS_PASSWORD}"
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