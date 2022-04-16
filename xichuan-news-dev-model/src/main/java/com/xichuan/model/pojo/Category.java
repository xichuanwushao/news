package com.xichuan.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 新闻资讯文章的分类（或者称之为领域）
 * </p>
 *
 * @author wuxiao
 * @since 2022-04-16
 */
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名，比如：科技，人文，历史，汽车等等
     */
    private String name;

    /**
     * 标签颜色
     */
    private String tagColor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Category{" +
        ", id=" + id +
        ", name=" + name +
        ", tagColor=" + tagColor +
        "}";
    }
}
