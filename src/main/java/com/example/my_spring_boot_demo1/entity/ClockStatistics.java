package com.example.my_spring_boot_demo1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLOCK_STATISTICS")
@Getter
@Setter
public class ClockStatistics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "RECORD_DAY", nullable = false)
    private LocalDate recordDay;

    @Column(name = "EARLIEST_TIME")
    private LocalDateTime earliestTime;

    @Column(name = "LATEST_TIME")
    private LocalDateTime latestTime;

    @Column(name = "TIME_DIFF", nullable = false, precision = 5, scale = 2)
    private BigDecimal timeDiff;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "REMARK")
    private String remark;
}
