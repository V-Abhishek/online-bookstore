# Online Bookstore

### PROJECT DESCRIPTION

Online Bookstore is a web application that allows booksellers to sign up and sell their books on the platform. As a customer you need to sign up before making a purchase on the platform. Users (Vendor/Customer) can update their profile and change their password. The application is configured to be hosted on AWS cloud platform. DNS setup along with Route 53 is configured to access the application via the domain name.

---

### ARCHITECTURE

<img alt="Architecture" src="https://github.com/V-Abhishek/online-bookstore/blob/main/images/IaaC.png" />

---

### CI/CD Pipeline

CI/CD pipeline is set up using CircleCI that validates the code by running unit tests and later allows the valid code to be merged with main branch. On merge, CircleCI creates code artifact and uploads the same on to S3 bucket and invokes AWS CodeDeploy to deploy the latest version of the code in tomcat server.

<img alt="CICD" src="https://github.com/V-Abhishek/online-bookstore/blob/main/images/CICD.png" />

---

### AMAZON MACHINE IMAGE (AMI)

Built and deployed the AMI with required customization using Packer by HashiCorp. You can find more details on the AMI used for this project in this [link]( https://github.com/V-Abhishek/amazon-machine-image).

<img alt="ami" src="https://github.com/v-abhishek/amazon-machine-image/blob/main/images/Architecture.png" />

---

### INFRASTRUCTURE

Infrastructure for hosting the web application on AWS is built as code using **Terraform**. You can find more information regarding infrastructure in this [link](https://github.com/V-Abhishek/aws-infrastructure).

---

### AWS Lambda

Generation of reset password link feature is configured to run as a Lambda function, to adopt microservices architecture. You can find more details about the Lambda function in this [link]( https://github.com/V-Abhishek/aws-lambda).

<img alt="Lambda" src="https://github.com/V-Abhishek/aws-lambda/blob/main/images/Lambda.png" />

---

### MONTORING and LOGGING

1. Centralized logging is implemented to collect the logs from all the EC2 instances and eliminated loss of log data when EC2 instances are brought down
2. Utilized statsD to collect custom metrics like Database Access time, S3 data access time, number of API request, etc. and passed it to **AWS CloudWatch Agent**
3. Configured CPU Utilization alarms to dynamically increase/decrease the EC2 instances based on the load using **AWS Auto Scaling**

---

### TESTING

1. Developed unit test cases using **Mockito**
2. Load tested the application using **Apache JMeter**

---

### TECHNOLOGY AND TOOLS

| Category | AWS Resources, Framework & Technologies |
| --- | --- |
| Programming Languages | Java, TSQL, HCL, HTML, CSS, JS |
| Web Application | Spring MVC, Hibernate, MySQL, AWS-SDK |
| Infrastructure | Terraform, VPC, RDS, S3, ELB, Auto Scaling, Route53, AWS Lambda, DynamoDB |
| Montoring & Logging | statsD, AWS Cloud-Watch Agent, Cloud-Watch Alarm, Log4js |
| Notification | SNS, SES |
| Security | BCrypt, RDS Encryption, SSL/TLS |
| CI/CD Pipeline | Circle CI |
| Testing Framework| Mockito, Apache JMeter |
| Web Server | Apache Tomcat |