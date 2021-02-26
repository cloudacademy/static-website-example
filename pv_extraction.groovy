

def dbname(){
    if ($ENVIRONMENT == 'INT'){
        echo "Function Working"
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
                dbname()
		    } 
            }
        }
    }
}
