package com.xjf.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dangdang.ddframe.rdb.sharding.api.HintManager;
import com.xjf.sharding.entity.User;
import com.xjf.sharding.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author xjf
 * @date 2020/2/16 18:02
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 验证读写分离的 读
     *
     * @return
     */
    @GetMapping("/list")
    public List<User> select(){

        // 强制路由主库。 查询时去主库查询
//        HintManager.getInstance().setMasterRouteOnly();

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.err::println);

        return userList;
    }

    /**
     * 验证读写分离的 写
     *
     * @return
     */
    @GetMapping("/add")
    public boolean add(){
        User user = new User();
        user.setId(2L);
        user.setCity("弗雷尔卓德");
        user.setName("布隆");

        int insert = userMapper.insert(user);

        return insert > 0;
    }

    /**
     * 验证不分库只分表：写入
     *
     * @return
     */
    @GetMapping("/add2")
    public String add2(){

        for (int i = 100; i < 200; i++) {
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setCity("德玛西亚");
            user.setName("盖伦");

            userMapper.insert(user);
        }



        return "success";
    }

    /**
     * 验证分库分表：写入
     *
     * @return
     */
    @GetMapping("/add3")
    public String add3(){

        for (int i = 0; i < 100; i++) {
            User user = new User();
//            user.setId(Long.valueOf(i));
            // 随机设置城市
            int random = new Random().nextInt();
            if (random % 2 == 0){
                user.setCity("上海");
            }else {
                user.setCity("杭州");
            }
            user.setName("嘉文四世");

            userMapper.insert(user);
        }

        return "success";
    }
}
