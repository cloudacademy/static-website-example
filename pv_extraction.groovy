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
		    	echo "The branch is $env.BARNCH_NAME"
		    } 
            }
        }
    }
}
