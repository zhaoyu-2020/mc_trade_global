package com.trade.global.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_message")
public class SysMessage implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String content;
    private Integer isRead;
    private Long replyBy;
    private String replyContent;
    private LocalDateTime replyTime;
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
