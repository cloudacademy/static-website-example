def dbhostname
def destdb
def dbname(){	
    if (params.ENVIRONMENT == 'INT'){
	def env_info=[db_name='',credentials_id='',user_name='',password='']
	env_info["db_name"] = 'int_database'        
        return env_info
    }else if (params.ENVIRONMENT == 'PPR'){
	def env_info=[db_name='',credentials_id='',user_name='',password='']
        env_info["db_name"] = 'PPR_database'        
        return env_info
    }else if (params.ENVIRONMENT == 'PRD'){
	def env_info=[db_name='',credentials_id='',user_name='',password='']    
        env_info["db_name"] = 'PRD_database'        
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
