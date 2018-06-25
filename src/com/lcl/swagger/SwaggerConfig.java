package com.lcl.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*重要！如果你的项目引入junit测试，此处需要使用@WebAppConfiguration，如果没有使用junit使用@Configuration(很多的博客都没有注明这个问题，为此我花了非常多的时间解决问题)*/
@WebAppConfiguration
@EnableSwagger2 //必须存在
@EnableWebMvc //必须存在
@ComponentScan(basePackages = {"com.xiaoming.SpringMVC.controller"}) //必须存在 扫描的API Controller package name 也可以直接扫描class (basePackageClasses)
public class SwaggerConfig {

	
//    @Bean
//    public Docket customDocket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("小明", "http://www.cnblogs.com/getupmorning/", "zhaoming0018@126.com");
//        return new ApiInfoBuilder()
//                .title("前台API接口")
//                .description("前台API接口")
//                .contact(contact)
//                .version("1.1.0")
//                .build();
//    }
}
