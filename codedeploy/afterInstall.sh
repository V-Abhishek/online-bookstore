#!/bin/bash

sudo chown tomcat:tomcat /var/lib/tomcat9/webapps/ROOT.war
sudo chown amazon-cloudwatch-agent:amazon-cloudwatch-agent /opt/aws/amazon-cloudwatch-agent/bin/cloudwatch-config.json
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -c file:/opt/aws/amazon-cloudwatch-agent/bin/cloudwatch-config.json -s