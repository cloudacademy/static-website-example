import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def dbhostname
def destdb
def dbname(){
    def env_info=["db_name":"","credentials_id":"","user_name":"","password":""]	
    if (params.ENVIRONMENT == 'INT'){
	env_info["db_name"] = "int_database"  
	return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))     
    }else if (params.ENVIRONMENT == 'PPR'){
        env_info["db_name"] = "PPR_database"  
	return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))     
    }else if (params.ENVIRONMENT == 'PRD'){
      	env_info["db_name"] = "PRD_database"  
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
			// dbname()
			//echo "The Destination DB is : ${destdb}"
		    } 
            }
        }
    }
}
