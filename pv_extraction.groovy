import groovy.json.JsonOutput

def dbhostname
def destdb
def dbname(){
    def env_info=["db_name":"","credentials_id":"","user_name":"","password":""]	
    if (params.ENVIRONMENT == 'INT'){
	env_info["db_name"] = "lpesipo1000int.schneider-electric.com:1521:pesv2int"
	env_info["credentials_id"] = "5e3e48a8-651d-48bd-813c-a3735c8af832"  
    }else if (params.ENVIRONMENT == 'PPR'){
        env_info["db_name"] = "ppr_database"
	env_info["credentials_id"] = "ppr_credentials_id"
	env_info["user_name"] = "ppr_user_name"
	env_info["password"] = "ppr_password"       
    }else if (params.ENVIRONMENT == 'PRD'){
      	env_info["db_name"] = "prd_database"
	env_info["credentials_id"] = "prd_credentials_id"
	env_info["user_name"] = "prd_user_name"
	env_info["password"] = "prd_password"    
    }
    return JsonOutput.prettyPrint(JsonOutput.toJson(env_info)) 
}
def dbdestdb(){
    if (params.ENVIRONMENT == 'INT'){
        destdb = 'kaasmongo'
    }
}

pipeline {
    agent any
    options {
        disableConcurrentBuilds()
        timestamps()
    }

    stages {
        stage("Display the Env variables") {
            steps {
		    script{
			    def info="${dbname()}"
			    def props = readJSON text: info, returnPojo: true
                credentials_id=props['credentials_id']
                db_name=props['db_name']
			    db_name=db_name.replace('\n','')
			    echo db_name
			    withCredentials([usernamePassword(credentialsId: credentials_id, passwordVariable: 'CATA_PASS', usernameVariable: 'CATA_USER')])
				{ 
					echo "db name : ${db_name}"
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
                                          - --spring.datasource.url=jdbc:oracle:thin:@'''+db_name+'''``
                                          - --spring.datasource.username=$CATA_USER
                                          - --spring.datasource.password=$CATA_PASS
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
	    stage("File") {
            steps {
				script{
					sh 'cat se-variants-extractor.yml'
				}
			}
        }
    }
}
