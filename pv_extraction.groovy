def envName = params.ENVIRONMENT
if (envName == "INT") {
	hostname = "AnandReddy"
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
		    	echo ${hostname}
		    } 
            }
        }
    }
}
