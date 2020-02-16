package com.xjf.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xjf.sharding.entity.User;
import com.xjf.sharding.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xjf
 * @date 2020/2/16 18:02
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public List<User> select(){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.err::println);

        return userList;
    }

    @GetMapping("/add")
    public boolean add(){
        User user = new User();
        user.setId(2L);
        user.setCity("弗雷尔卓德");
        user.setName("布隆");

        int insert = userMapper.insert(user);

        return insert > 0;
    }
}
