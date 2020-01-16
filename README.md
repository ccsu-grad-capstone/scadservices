# scadservices
Web Application to manage Fantasy Football Services


# To run sql: 

Make sure you have installed mysql on your computer

Go to project folder 

$cd scadservices, then run below command

$ mysql -u root -p < sql/scad-schema.sql 


# To run project:

$ java -jar target/scadservices-thorntail.jar -S local

# Swagger url:

http://localhost:8080/swagger-ui/index.html?url=http://localhost:8080/scadservices/api/swagger.json