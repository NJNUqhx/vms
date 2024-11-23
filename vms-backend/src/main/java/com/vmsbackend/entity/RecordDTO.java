package com.vmsbackend.entity;

import lombok.Data;

@Data
public class RecordDTO {
    private Integer recordId;      // record 表的 id
    private Integer goodsId;       // record 表的 goods_id
    private String goodsName;      // goods 表的 name
    private Integer userId;        // record 表的 user_id
    private String userName;       // users 表的 username
    private Integer adminId;       // record 表的 admin_id
    private String adminName;      // admins 表的 admin_name
    private Integer count;         // record 表的 count
    private String createTime;     // record 表的 create_time
    private String remark;         // record 表的 remark
}
