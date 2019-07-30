#!/groovy
def dockerImageRepo = 'anandtest/protest'
def dockerImageTag
def dockerImage

pipeline
{
	agent any
	stages
	{
		stage('Cleaning the WorkSpace')
		{
			steps
			{
				deleteDir()
				echo 'Cleanup Done'
			}
		}
		
		stage('CheckOut latest Code')
		{
			steps
			{
				checkout scm
				script 
				{
					dockerImageTag="$BUILD_NUMBER"
				}
			}
		}
		
		stage('Build the Image')
		{
			steps
			{
				script 
				{
					echo 'Starting the Image Building'
					dockerImage = docker.build "${dockerImageRepo}:${dockerImageTag}"
					sh 'docker images'
					sh 'docker ps -a'
					echo "$dockerImage"
				}
			}
		}
	}
}
