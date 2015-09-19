package com.huotu.huobanmall.entity;

import javax.persistence.*;

/**
 * 商品分类
 * 说明：对应表Mall_Goods_Cat 实体GoodsCatModel
 * Created by lgh on 2015/9/14.
 */
@Entity
@Table(name = "Mall_Goods_Cat")
@Cacheable(value = false)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cat_Id")
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "Cat_Name")
    private String title;

    /**
     * 父Id
     */
    @Column(name = "Parent_Id")
    private Integer parentId;

    /**
     * 排序号
     */
    @Column(name = "P_Order")
    private Integer sortId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}
