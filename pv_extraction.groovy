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

def getJsonProperty(String jsonText,String locator){		
    def slurper = new JsonSlurper()
    def result = slurper.parseText(jsonText)
    println("responseObject:"+result)
    Binding binding = new Binding();
    binding.setVariable("result", result);
    GroovyShell shell = new GroovyShell(binding)
    return shell.evaluate("result."+locator)
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
			    echo "${getJsonProperty(info,"credential_id")}"
			    def credentials_id="${getJsonProperty(info,"credential_id")}"
			    echo "${credentials_id}"
			    withCredentials([usernamePassword(credentialsId: credentials_id, passwordVariable: 'CATA_PASS', usernameVariable: 'CATA_USER')])
				{
		  
						
						//echo info_object.toString()
						//echo "the DataBase is: ${info_object.credentials_id}"
						//echo "the DataBase is: ${CATA_PASS}" 
						//echo "the DataBase is: ${CATA_USER}"  
					echo "inside with credentials"
					
				}
		    } 
            }
        }
    }
}
