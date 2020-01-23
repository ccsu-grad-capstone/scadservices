# scadservices
Web Application to manage Fantasy Football Services


# To run sql: 

Make sure you have installed mysql on your computer

Go to project folder 

$cd scadservices, then run below command

$ mysql -u root -p < sql/scad-schema.sql 


# To run project:

$ mvn clean package

$ java -jar target/scadservices-thorntail.jar -S local

$java -Xdebug -agentlib:jdwp=transport=dt_socket,address=9080,server=y,suspend=n -jar target/scadservices-thorntail.jar -S local

# Swagger url:

http://localhost:8080/swagger-ui/index.html?url=http://localhost:8080/scadservices/api/swagger.json