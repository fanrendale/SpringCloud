package com.xjf.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xjf
 * @date 2020/2/6 15:50
 */
@Data
@ApiModel(value = "com.xjf.swagger.entity.AddUserParam", description = "新增用户参数")
public class AddUserParam {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty("年龄")
    private int age;

    public AddUserParam() {
    }

    public AddUserParam(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
