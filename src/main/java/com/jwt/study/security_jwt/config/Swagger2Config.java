package com.jwt.study.security_jwt.config;

import io.swagger.models.Swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger2配置类
 * @author YanShen.Wu
 * @date 2018/5/25 14:19:40
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestFulAPI(){

        //参数中增加token输入项
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("JWT令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(basicConfig())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jwt.study.security_jwt"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);

    }

    @Bean
    public ApiInfo basicConfig(){
        return new ApiInfoBuilder()
                .title("SpringSecurity整合JWT API")
                .description("SpringSecurity整合JWT项目后台API接口文档")
                .version("1.0")
                .build();
    }
}
