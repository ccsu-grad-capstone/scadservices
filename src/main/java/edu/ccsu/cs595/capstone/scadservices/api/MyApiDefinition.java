package edu.ccsu.cs595.capstone.scadservices.api;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition (
info = @Info (
        title = "Salary Cap Dynasty Fantasy Football (SCAD) Service",
        version = "1.0.0",
        description = "Contact Details:",
        contact = @Contact (
            name = "Ramesh Kappera, Phil Murray and Ryan Lauzon",
            email = "capstone-spr20@my.ccsu.edu",
            url = "https://www.ccsu.edu/"
        )
	),
	consumes = {"application/json" },
	produces = {"application/json" },
    host = "localhost",
    basePath = "/api/v1",
    schemes = {SwaggerDefinition.Scheme.HTTP}
)
public interface MyApiDefinition {

}
