package com.trade.global.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_product")
public class SysProduct implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private String productName;
    private String productNameEn;
    private String model;
    private String modelEn;
    private String shortDescription;
    private String shortDescriptionEn;
    private String description;
    private String descriptionEn;
    private String price;
    private Integer stock;
    private String mainImage;
    private String images;
    private String parameters;
    private Integer isRecommend;
    private Integer isTop;
    private Integer sort;
    private Integer status;
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
