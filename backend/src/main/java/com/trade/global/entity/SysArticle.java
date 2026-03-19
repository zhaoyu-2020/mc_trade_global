package com.trade.global.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_article")
public class SysArticle implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private String title;
    private String titleEn;
    private String author;
    private String coverImage;
    private String summary;
    private String summaryEn;
    private String content;
    private String contentEn;
    private Integer viewCount;
    private Integer isTop;
    private Integer isPublish;
    private Integer sort;
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
