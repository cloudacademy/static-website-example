def dbhostname
def destdb
def dbname(){	
    def env_info=["db_name":"","credentials_id":"","user_name":"","password":""]	
    if (params.ENVIRONMENT == 'INT'){
	env_info["db_name"] = "int_database"        
        return env_info
    }else if (params.ENVIRONMENT == 'PPR'){
        env_info["db_name"] = "PPR_database"        
        return env_info
    }else if (params.ENVIRONMENT == 'PRD'){
        env_info["db_name"] = "PRD_database"        
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
        stage("Desplay the Env variables") {
            steps {
		    script{
		    	echo "Hello World"
		    	dbhostname = "${dbname()}"
		    	echo "the DataBase is: ${dbhostname["db_name"]}"
                // dbname()
                echo "The Destination DB is : ${destdb}"
		    } 
            }
        }
    }
}
