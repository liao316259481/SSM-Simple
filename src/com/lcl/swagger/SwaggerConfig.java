package com.lcl.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*��Ҫ����������Ŀ����junit���ԣ��˴���Ҫʹ��@WebAppConfiguration�����û��ʹ��junitʹ��@Configuration(�ܶ�Ĳ��Ͷ�û��ע��������⣬Ϊ���һ��˷ǳ����ʱ��������)*/
@WebAppConfiguration
@EnableSwagger2 //�������
@EnableWebMvc //�������
@ComponentScan(basePackages = {"com.xiaoming.SpringMVC.controller"}) //������� ɨ���API Controller package name Ҳ����ֱ��ɨ��class (basePackageClasses)
public class SwaggerConfig {

	
//    @Bean
//    public Docket customDocket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("С��", "http://www.cnblogs.com/getupmorning/", "zhaoming0018@126.com");
//        return new ApiInfoBuilder()
//                .title("ǰ̨API�ӿ�")
//                .description("ǰ̨API�ӿ�")
//                .contact(contact)
//                .version("1.1.0")
//                .build();
//    }
}
