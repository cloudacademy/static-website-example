def dbhostname
def intDBhostname = "lpesipo1000int.schneider-electric.com:1521:pesv2int"
def pprDBhostname = "lpesipo1009ppr.schneider-electric.com:1521:pesv2ppr"
def prdDBhostname = "lpesipo1002prd.schneider-electric.com:1521:pesv2prd"
def dbname(){
    if (params.ENVIRONMENT == 'INT'){
        return 'lpesipo1000int.schneider-electric.com:1521:pesv2int'
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
		    } 
            }
        }
    }
}
