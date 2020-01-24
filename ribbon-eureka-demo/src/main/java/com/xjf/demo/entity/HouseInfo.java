package com.xjf.demo.entity;

import java.io.Serializable;

/**
 * 房子信息
 *
 * @author xjf
 * @date 2020/1/23 11:19
 */
public class HouseInfo implements Serializable {
    private static final long serialVersionUID = 6684550393735791145L;

    /**
     * ID
     */
    private String id;

    /**
     * 地址
     */
    private String addr;

    public HouseInfo(String id, String addr) {
        this.id = id;
        this.addr = addr;
    }

    public HouseInfo(String addr) {
        this.addr = addr;
    }

    public HouseInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
