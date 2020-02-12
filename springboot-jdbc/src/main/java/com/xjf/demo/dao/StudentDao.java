package com.xjf.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xjf
 * @date 2020/2/12 20:20
 */
@Service
public class StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询个数
     * @return
     */
    public Long count(){
        return jdbcTemplate.queryForObject("select count(*) from student", Long.class);
    }
}
