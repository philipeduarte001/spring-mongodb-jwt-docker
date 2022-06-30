package com.covid;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    private final Logger log = LoggerFactory.getLogger(SpringFoxConfig.class);
    
	@Bean
    public Docket api() { 
		log.info("Starting Swagger");
        Contact contact = new Contact(
            "Serasa",
            "thiago.com",
            "thfigueiredo.developer@gmail.com");

        List<VendorExtension> vext = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfo(
            "Backend API",
            "Type in the **Value** input box **Bearer &lt;JWT&gt;**, where JWT is the token in **Auhtorize** section",
            "v1.0",
            "",
            contact,
            "MIT",
            "",
            vext);

		   Docket docket = new Docket(DocumentationType.SWAGGER_2)
		            .apiInfo(apiInfo)
		            .securityContexts(Lists.newArrayList(securityContext()))
		            .securitySchemes(Lists.newArrayList(apiKey()))
		            .useDefaultResponseMessages(false);   
		   return docket.select()                                  
			          .apis((RequestHandlerSelectors.basePackage( "com.covid.controller") ))
			          .paths(PathSelectors.any())                          
			          .build(); 
    }
	

    private ApiKey apiKey() {
        return new ApiKey("Bearer", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
            new SecurityReference("Bearer", authorizationScopes));
    }
}
