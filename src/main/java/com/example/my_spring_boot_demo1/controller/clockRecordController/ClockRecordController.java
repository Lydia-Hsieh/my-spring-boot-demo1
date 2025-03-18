package com.example.my_spring_boot_demo1.controller.clockRecordController;

import com.example.my_spring_boot_demo1.controller.clockRecordController.pojo.ClockRecordVo;
import com.example.my_spring_boot_demo1.controller.clockRecordController.service.ClockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClockRecordController {

    @Autowired
    private ClockRecordService clockRecordService;

    @PostMapping("/clockInAndOut")
    public ResponseEntity<Map<String, Object>> clockInAndOut(@RequestBody ClockRecordVo vo) {
        Map<String, Object> resultMap = new HashMap<>();
        clockRecordService.clockInAndOut(vo);
        resultMap.put("status", true);
        return ResponseEntity.ok(resultMap);
    }
}
