package com.xjf.demo.controller;

import com.xjf.demo.entity.Student;
import com.xjf.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟为学生服务
 *
 * @author xjf
 * @date 2020/2/15 14:05
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 修改学生
     *
     * @param student
     * @return
     */
    @PostMapping("/update")
    public String update(@RequestBody Student student){
        return String.valueOf(studentService.update(student));
    }
}
