import groovy.json.JsonOutput

def dbhostname
def destdb
def dbname(){
    def env_info=["db_name":"","credentials_id":"","user_name":"","password":""]	
    if (params.ENVIRONMENT == 'INT'){
	env_info["db_name"] = "int_database"  
	//def json = new groovy.json.JsonBuilder()
	//json rootKey: env_info     
        //return groovy.json.JsonOutput.prettyPrint(json.toString()) 
	return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))    
    }else if (params.ENVIRONMENT == 'PPR'){
        env_info["db_name"] = "PPR_database"  
	//def json = new groovy.json.JsonBuilder()
	//json rootKey: env_info     
        //return groovy.json.JsonOutput.prettyPrint(json.toString())
	 return JsonOutput.prettyPrint(JsonOutput.toJson(env_info))    
    }else if (params.ENVIRONMENT == 'PRD'){
      	env_info["db_name"] = "PRD_database"  
	//def json = new groovy.json.JsonBuilder()
	//json rootKey: env_info     
        //return groovy.json.JsonOutput.prettyPrint(json.toString())
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
		    	echo "Hello World"
		    	def info="${dbname()}"
			echo info.db_name    
		    	//echo "the DataBase is: ${env_info["db_name"]}"
			// dbname()
			echo "The Destination DB is : ${destdb}"
		    } 
            }
        }
    }
}
