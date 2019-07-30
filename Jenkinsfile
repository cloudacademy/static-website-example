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
			checkout scm
		}
	}
}
