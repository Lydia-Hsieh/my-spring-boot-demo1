package com.example.my_spring_boot_demo1.controller.clockRecordController.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClockRecordVo {

    private String userId;
    private LocalDateTime createTime;
    private String remark;
}
