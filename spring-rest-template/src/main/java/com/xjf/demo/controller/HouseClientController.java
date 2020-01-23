package com.xjf.demo.controller;

import com.xjf.demo.entity.HouseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 使用RestTemplate来调用接口
 *
 * @author xjf
 * @date 2020/1/23 11:31
 */
@RestController
public class HouseClientController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 使用@RequestParam传递参数
     *
     * @param id
     * @return
     */
    @GetMapping("/call/data")
    public HouseInfo getData(@RequestParam("id") String id){
        return restTemplate.getForObject("http://localhost:8080/house/data?id=" + id, HouseInfo.class);
    }

    /**
     * 使用@PathVariable传递参数
     *
     * @param id
     * @return
     */
    @GetMapping("/call/data/{id}")
    public String getData2(@PathVariable("id") String id){
        return restTemplate.getForObject("http://localhost:8080/house/data/{id}", String.class, id);
    }

    /**
     * 使用getForEntity来获取数据，
     * getForEntity中可以获取返回的状态码、请求头等信息，通过getBody获取响应的内容。
     * 其余的和getForObject一样
     *
     * @param id
     * @return
     */
    @GetMapping("/call/dataEntity")
    public HouseInfo getData3(@RequestParam("id") String id){
        ResponseEntity<HouseInfo> responseEntity = restTemplate.getForEntity("http://localhost:8080/house/data?id=" + id, HouseInfo.class);

        System.out.println(responseEntity);

        if (responseEntity.getStatusCodeValue() == 200){
            return responseEntity.getBody();
        }

        return null;
    }

    /**
     * 添加数据，使用post方式调用
     * @return
     */
    @GetMapping("/call/save")
    public String add(){
        HouseInfo houseInfo = new HouseInfo("广东省深圳市");

        String id = restTemplate.postForObject("http://localhost:8080/house/save",houseInfo, String.class);

        return id;
    }
}
