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
		
		stage('Checkout latest Code')
		{
			checkout scm
		}
	}
}
