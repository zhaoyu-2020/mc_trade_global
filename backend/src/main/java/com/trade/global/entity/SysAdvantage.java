package com.trade.global.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_advantage")
public class SysAdvantage implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String icon;
    private String title;
    private String titleEn;
    private String description;
    private String descriptionEn;
    private Integer sort;
    private Integer status;
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
