package io.github.mouday.entity;

import io.github.mouday.annotation.JsonTable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonTable(name = "article")
public class Article {
    /**
     * 唯一id
     */
    private String uid;

    /**
     * 标题
     */
    private String title;

    /**
     * 原文地址 url
     */
    private String url;

    /**
     * 分类
     */
    private String category;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
