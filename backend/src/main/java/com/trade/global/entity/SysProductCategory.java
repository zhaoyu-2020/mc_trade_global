package com.trade.global.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("sys_product_category")
public class SysProductCategory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String categoryName;
    private String categoryNameEn;
    private Long parentId;
    private Integer sort;
    private Integer status;
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private List<SysProductCategory> children;
}
