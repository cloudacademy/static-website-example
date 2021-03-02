import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def dbhostname
def destdb
def dbname(){
    def env_info=["db_name":"","credentials_id":"","user_name":"","password":""]	
    if (params.ENVIRONMENT == 'INT'){
	env_info["db_name"] = "int_database"
	env_info["credentials_id"] = "5e3e48a8-651d-48bd-813c-a3735c8af832"
	env_info["user_name"] = "int_user_name"
	env_info["password"] = "int_password"    
	return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))     
    }else if (params.ENVIRONMENT == 'PPR'){
        env_info["db_name"] = "ppr_database"
	env_info["credentials_id"] = "ppr_credentials_id"
	env_info["user_name"] = "ppr_user_name"
	env_info["password"] = "ppr_password"  
	return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))     
    }else if (params.ENVIRONMENT == 'PRD'){
      	env_info["db_name"] = "prd_database"
	env_info["credentials_id"] = "prd_credentials_id"
	env_info["user_name"] = "prd_user_name"
	env_info["password"] = "prd_password"   
	return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))        
    }
}

pipeline {
    agent any

    options {
        disableConcurrentBuilds()
        timestamps()
    }

    stages {
        stage("Create job template") {
            steps {
                script {
                    def info="${dbname()}"
				    def jsonSlurper = new JsonSlurper()
				    def info_object = jsonSlurper.parseText(info) 
				    def cred = info_object.credentials_id
                    withCredentials([usernamePassword(credentialsId: cred, passwordVariable: 'INT_CATA_PASS', usernameVariable: 'INT_CATA_USER')]) {
                        if (ALL_CATALOG == 'true') {
                            sh '''
                                cat <<EOF > se-variants-extractor.yml
                                apiVersion: batch/v1
                                kind: Job
                                metadata:
                                  name: se-variants-extractor
                                  namespace: epam
                                spec:
                                  ttlSecondsAfterFinished: 20
                                  template:
                                    metadata:
                                      labels:
                                        job-name: se-variants-extractor
                                    spec:
                                      containers:
                                      - args:
                                        - --spring.data.mongodb.host=se-variants-mongo
                                        - --spring.datasource.url=jdbc:oracle:thin:@lpesipo1000int.schneider-electric.com:1521:pesv2int
                                        - --spring.datasource.username=$cred
                                        - --spring.datasource.password=$INT_CATA_PASS
                                        command:
                                        - java
                                        - -Xms2g
                                        - -Xmx6g
                                        - -XX:+UseG1GC
                                        - -Doracle.jdbc.fanEnabled=false
                                        - -jar
                                        - /app/product-variants-1.0.0.jar
                                        image: registry.us.se.com/epam/product-data-generator
                                        imagePullPolicy: Always
                                        name: se-variants-extractor
                                        resources:
                                          requests:
                                            memory: "4096Mi"
                                            cpu: "1000m"
                                          limits:
                                            memory: "12288Mi"
                                            cpu: "2000m"
                                        securityContext:
                                          allowPrivilegeEscalation: false
                                          capabilities: {}
                                          privileged: false
                                          readOnlyRootFilesystem: false
                                          runAsNonRoot: false
                                      imagePullSecrets:
                                      - name: se
                                      restartPolicy: Never
                                EOF
                            '''.stripIndent()
                        } else {
                            sh '''
                                cat <<EOF > se-variants-extractor.yml
                                apiVersion: batch/v1
                                kind: Job
                                metadata:
                                  name: se-variants-extractor
                                  namespace: epam
                                spec:
                                  ttlSecondsAfterFinished: 20
                                  template:
                                    metadata:
                                      labels:
                                        job-name: se-variants-extractor
                                    spec:
                                      containers:
                                      - args:
                                        - --spring.data.mongodb.host=se-variants-mongo
                                        - --spring.datasource.url=jdbc:oracle:thin:@lpesipo1000int.schneider-electric.com:1521:pesv2int
                                        - --spring.datasource.username=$cred
                                        - --spring.datasource.password=$INT_CATA_PASS
                                        - locales=${LOCALES}
                                        - sourceIds=${SOURCE_IDS}
                                        - type=${TYPE}
                                        command:
                                        - java
                                        - -Xms2g
                                        - -Xmx6g
                                        - -XX:+UseG1GC
                                        - -Doracle.jdbc.fanEnabled=false
                                        - -jar
                                        - /app/product-variants-1.0.0.jar
                                        image: registry.us.se.com/epam/product-data-generator
                                        imagePullPolicy: Always
                                        name: se-variants-extractor
                                        resources:
                                          requests:
                                            memory: "4096Mi"
                                            cpu: "1000m"
                                          limits:
                                            memory: "12288Mi"
                                            cpu: "2000m"
                                        securityContext:
                                          allowPrivilegeEscalation: false
                                          capabilities: {}
                                          privileged: false
                                          readOnlyRootFilesystem: false
                                          runAsNonRoot: false
                                      imagePullSecrets:
                                      - name: se
                                      restartPolicy: Never
                                EOF
                            '''.stripIndent()
                        }
                    }
                }
            }
        }
    }
}
