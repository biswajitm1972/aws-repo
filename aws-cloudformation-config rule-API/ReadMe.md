
This Cloudformation template will create a Lambda Function for the Custom Config Rule, which checks that all private APIs use resource policy restricted to VPC endpoints or VPC in the same AWS account and create a supporting IAM Role to allow the execution of the function.

In this CloudFormation template, index.py (python script) is used to deploy the lambda function
