package com.example.my_spring_boot_demo1.controller.clockRecordController.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClockRecordRequest {

    @NotBlank
    private String userId;
    @NotNull
    private LocalDateTime createTime;
    private String remark;
}
