package org.alvin.opsdev.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Administrator on 2017/3/15.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("org.alvin.opsdev.monitor.controller")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("运维监控系统")
                .description("运维微服务的各种服务和消息中间件监控")
                .termsOfServiceUrl("http://localhost:9001")
                .version("1.0").build();
    }

//    private ApiInfo demoApiInfo() {
//        ApiInfo apiInfo = new ApiInfo("Electronic Health Record(EHR) Platform API",//大标题
//                "EHR Platform's REST API, for system administrator",//小标题
//                "1.0",//版本
//                "NO terms of service",
//                "365384722@qq.com",//作者
//                "The Apache License, Version 2.0",//链接显示文字
//                "http://www.apache.org/licenses/LICENSE-2.0.html"//网站链接
//        );
//
//        return apiInfo;
//    }
}
