import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def dbhostname
def destdb
def dbname(){
    def env_info=["db_name":"","credentials_id":"","user_name":"","password":""]	
    if (params.ENVIRONMENT == 'INT'){
	env_info["db_name"] = "int_database"
	env_info["credentials_id"] = "int_credentials_id"
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
			def jsonSlurper = new JsonSlurper()
     			def info_object = jsonSlurper.parseText(info) 
			echo info_object.toString()        
		    	echo "the DataBase is: ${info_object.db_name}"
			echo "the DataBase is: ${info_object.credentials_id}"
			echo "the DataBase is: ${info_object.user_name}" 
			echo "the DataBase is: ${info_object.password}"     
			// dbname()
			//echo "The Destination DB is : ${destdb}"
		    } 
            }
        }
    }
}
