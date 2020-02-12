package com.xjf.demo;

import com.xjf.demo.dao.StudentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private StudentDao studentDao;

    @Test
    public void test1(){
        System.out.println("学生个数：" + studentDao.count());
    }

}
