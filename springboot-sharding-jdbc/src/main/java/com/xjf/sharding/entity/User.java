package com.xjf.sharding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author xjf
 * @date 2020/2/16 18:00
 */
@Data
public class User {
    private Long id;

    private String city;

    private String name;

}
