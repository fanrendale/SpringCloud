package com.xjf.demo.service;

import com.xjf.demo.entity.Student;

/**
 * @author xjf
 * @date 2020/2/15 13:54
 */
public interface StudentService {

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    boolean update(Student student);
}
