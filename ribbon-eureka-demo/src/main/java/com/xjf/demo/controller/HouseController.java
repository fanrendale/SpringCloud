package com.xjf.demo.controller;

import com.xjf.demo.entity.HouseInfo;
import org.springframework.web.bind.annotation.*;

/**
 * 房子的控制层
 *
 * @author xjf
 * @date 2020/1/23 11:20
 */
@RestController
public class HouseController {

    /**
     * 使用@RequestParam方式传参
     *
     * @param id
     * @return
     */
    @GetMapping("/house/data")
    public HouseInfo getData(@RequestParam("id") String id){
        return new HouseInfo("1","四川省成都市");
    }

    /**
     * 使用@PathVariable方式传参
     *
     * @param id
     * @return
     */
    @GetMapping("/house/data/{id}")
    public String getData2(@PathVariable("id") String id){
        return id;
    }

    /**
     * 新增接口
     *
     * @param houseInfo
     * @return
     */
    @PostMapping("/house/save")
    public String addData(@RequestBody HouseInfo houseInfo){
        System.out.println(houseInfo.getAddr());

        return "100";
    }


}
