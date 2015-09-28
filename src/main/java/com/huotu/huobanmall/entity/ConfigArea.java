package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 区域配置
 * todo 确认此表
 */
@Entity
@Table( indexes = {@Index(columnList = "code")})
public class ConfigArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "superArea", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ConfigArea> subAreas;
    @ManyToOne
    private ConfigArea superArea;

    @Column(length = 20)
    private String name;

    private Integer level;
    @Column(length = 10)
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ConfigArea> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(List<ConfigArea> subAreas) {
        this.subAreas = subAreas;
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