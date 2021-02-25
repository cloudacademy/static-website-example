def envName = params.ENVIRONMENT
def intDBhostname = "lpesipo1000int.schneider-electric.com:1521:pesv2int"
def pprDBhostname = "lpesipo1009ppr.schneider-electric.com:1521:pesv2ppr"
def prdDBhostname = "lpesipo1002prd.schneider-electric.com:1521:pesv2prd"
def intcredentialsId = "d0d8e9f8-96c4-42dd-a38b-a549a204dbc9"
def pprcredentialsId = "3ed29a02-7552-491b-8a4c-d1b1b52d3f71"
def prdcredentialsId = "50319cf9-32dc-49e8-bec3-70010c89b9bb"
def kaas_int_mongo =  se-variants-mongo
def kaas_prod_mongo = se-variants-extractor
 
 if (envName == "INT") {
    extractEnv = "INT"
	hostname = "$intDBhostname"
 
} else if (envName == "PPR") {
    extractEnv = "PPR"
	hostname= "$pprDBhostname"
} else if (envName == "PRD") {
    extractEnv = "PRD"
	hostname= "$PPRDBhostname"
}
def destination(){
if (envName == "INT") {
       return mongo_db = $int_mongo
       }else if (envName == "PRD") {
       return mongo_db = $prod_mongo 
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

            echo $hostname
            // echo $username
            // echo $password

            }
        }
    }
}
