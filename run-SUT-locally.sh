#!/bin/bash

VERSION=1.0.3

docker pull sadsamba/heatclinic:$VERSION
docker run -i --rm -p 8080:8080 -p 8443:8443 -p 9001:9001 sadsamba/heatclinic:$VERSION

#
#Open Git Bash (if you have it installed).
#Navigate to the directory where your script is located using the cd command, e.g., cd C:/FH_Ost/HSR-SW-Testing-Code.
#Execute your Bash script by typing ./run-SUT-locally.sh.