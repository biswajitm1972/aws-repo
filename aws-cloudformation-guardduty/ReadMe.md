Cloudformation Configuration package to establish AWS threat detection services. 

Amazon GuardDuty: To monitors the malicious activity and unauthorized behavior and protect  AWS accounts and workloads. 
Amazon Inspector: Enable security assessment service which helps to improve the security and compliance of applications deployed on EC2 instances. By default, the (CVE) package is configured to run against all EC2 instances. every Saturday at Midnight. 
AWS Security Hub: Provides a comprehensive view of high-priority security alerts and compliance status across AWS accounts. The CIS AWS Foundations compliance standard is enabled by default.

Email Notifications: Email notifications has been Enable for GuardDuty and Security Hub using CloudWatch Event Rules and SNS.
Security Hub Prerequisites:  AWS Config must be enabled.