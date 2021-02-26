def dbhostname
def destdb
def dbname(){
    if (params.ENVIRONMENT == 'INT'){
        return 'lpesipo1000int.schneider-electric.com:1521:pesv2int'
    }else if (params.ENVIRONMENT == 'PPR'){
        return 'lpesipo1009ppr.schneider-electric.com:1521:pesv2ppr'
    }else if (params.ENVIRONMENT == 'PRD'){
        return 'lpesipo1002prd.schneider-electric.com:1521:pesv2prd'
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
		    	echo "the DataBase is: ${dbhostname}"
                // dbname()
                echo "The Destination DB is : ${destdb}"
		    } 
            }
        }
    }
}
