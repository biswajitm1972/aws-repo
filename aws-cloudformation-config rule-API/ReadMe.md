
This Cloudformation template will create a Lambda Function for the Custom Config Rule, which checks that all private APIs use resource policy restricted to VPC endpoints or VPC in the same AWS account. As well as its create supporting IAM Role to allow the execution of the function.


Lambda Function: https://s3-ap-southeast-1.amazonaws.com/biswa-aws-cf/API_GW_PRIVATE_RESTRICTED.zip 