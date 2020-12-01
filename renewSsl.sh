#!/bin/bash
export PATH=/usr/local/bin:/usr/bin:/bin:/usr/local/games:/usr/games

echo "Updating SSL cert" >> /home/philipmurray80/scadservices/foo.txt

cd /etc/letsencrypt/live/scadservices.xyz
sudo rm cert.p12 cert.jks
sudo openssl pkcs12 -password pass:password -export -in combined.pem -out cert.p12
sudo keytool -importkeystore -srckeystore cert.p12 -srcstorepass password -srcstoretype pkcs12 -destkeystore cert.jks -deststorepass password
sudo mv cert.jks /home/philipmurray80/scadservices/src/main/resources/security

cd /home/philipmurray80/scadservices
ps -eF | grep java | head -n1 | awk '{print $2}' | sudo xargs kill -9
ps -eF | grep java | head -n1 | awk '{print $2}' | sudo xargs kill -9

sudo rm -rf target

echo "About to run scad.sh...." >> foo.txt
./scad.sh

