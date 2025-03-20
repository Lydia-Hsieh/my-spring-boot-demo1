package com.example.my_spring_boot_demo1.controller.clockRecordController;

import com.example.my_spring_boot_demo1.controller.clockRecordController.pojo.ClockRecordRequest;
import com.example.my_spring_boot_demo1.controller.clockRecordController.service.ClockRecordService;
import com.example.my_spring_boot_demo1.service.ResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClockRecordController {

    @Autowired
    private ClockRecordService clockRecordService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/clock-in-and-out")
    public ResponseEntity<Map<String, Object>> clockInAndOut(@RequestBody @Valid ClockRecordRequest request) {
        clockRecordService.clockInAndOut(request);
        return responseService.ok(new HashMap<>());
    }

    //一定會失敗的打卡API
    @PostMapping("/clock-must-fail")
    public ResponseEntity<Map<String, Object>> mustFailClock(@RequestBody @Valid ClockRecordRequest request) {
        try {
            int j = 1/0; //create situation that must be failed
            clockRecordService.clockInAndOut(request);
            return responseService.ok(new HashMap<>());
        } catch (Exception e) {
            request.setRemark(e.getMessage());
            clockRecordService.clockInAndOut(request);
            throw new RuntimeException(e);
        }
    }
}
