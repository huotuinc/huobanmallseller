package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 区域配置
 */
@Entity
@Cacheable(value = false)
@Table(name = "TS_ConfigArea", indexes = {@Index(columnList = "code")})
public class ConfigArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ParentId")
    private ConfigArea superArea;

    @Column(length = 20, name = "Name")
    private String name;

    @Column(name = "Level")
    private Integer level;

    @Column(length = 10, name = "AreaCode")
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public ConfigArea getSuperArea() {
        return superArea;
    }

    public void setSuperArea(ConfigArea superArea) {
        this.superArea = superArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}