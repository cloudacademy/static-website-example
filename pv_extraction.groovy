import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def dbhostname
def destdb
def Map dbname(){
    def env_info=["db_name":"","credentials_id":"","user_name":"","password":""]	
    if (params.ENVIRONMENT == 'INT'){
	env_info["db_name"] = "int_database"  
	//def json = new groovy.json.JsonBuilder()
	//json rootKey: env_info     
        //return groovy.json.JsonOutput.prettyPrint(json.toString()) 
	//return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))   
	return env_info   
    }else if (params.ENVIRONMENT == 'PPR'){
        env_info["db_name"] = "PPR_database"  
	//def json = new groovy.json.JsonBuilder()
	//json rootKey: env_info     
        //return groovy.json.JsonOutput.prettyPrint(json.toString())
	//return JsonOutput.prettyPrint(JsonOutput.toJson(env_info)) 
	return env_info    
    }else if (params.ENVIRONMENT == 'PRD'){
      	env_info["db_name"] = "PRD_database"  
	//def json = new groovy.json.JsonBuilder()
	//json rootKey: env_info     
        //return groovy.json.JsonOutput.prettyPrint(json.toString())
	//return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))     
	return env_info    
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
		    	echo "Hello World"
			Map info="${dbname()}"    
		    	//def info="${dbname()}"
			//def info=JsonOutput.prettyPrint(JsonOutput.toJson("${dbname()}"))    
			echo info    
		    	//echo "the DataBase is: ${env_info["db_name"]}"
			// dbname()
			echo "The Destination DB is : ${destdb}"
		    } 
            }
        }
    }
}
