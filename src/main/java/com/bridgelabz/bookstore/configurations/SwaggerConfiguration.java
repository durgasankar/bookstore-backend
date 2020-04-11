package com.bridgelabz.bookstore.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger is a set of open-source tools built around the OpenAPI Specification
 * that can help you design, build, document and consume REST APIs. ... Swagger
 * UI – renders OpenAPI specks as interactive API documentation.
 *
 * @author Durgasankar Mishra
 * @created 2020-04-11
 * @version 1.0
 * @see {@link Docket} swagger SwaggerConfiguration class
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket( DocumentationType.SWAGGER_2 )
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.bridgelabz.bookstore.controllers" ) )
                .build()
                .apiInfo( apiInfo() );
    }

    /**
     * Documentation for swagger api
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title( "Book Store Application" )
                .description( "Book Store Shopping cart application with registration management APi" )
                .contact( new Contact( "Duragasankar Mishra", "https://github.com/durgasankar", "durgasankar.raja500@gmail.com" ) )
                .license( "Apache 2.0" ).licenseUrl( "http://www.apache.org/licenses/LICENSE-2.0.html" )
                .version( "1.0.0" )
                .build();
    }

}
