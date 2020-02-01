package com.xjf.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class DemoApplication {

    @Value("${myname}")
    private String name;

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/name")
    public String getName(){

        // 1. 使用 rest 调用服务端的 API，可以获取配置的信息
        String uri = "http://localhost:8090/rest/conf/list/dev/smconf-demo/EurekaConf";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","dale");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        System.out.println(result.getBody());

        // 2. 通过代码获取配置值
        String map = System.getProperty("spring.data.mongodb.map");
        System.out.println("map: " + map);

        return name;
    }

}
