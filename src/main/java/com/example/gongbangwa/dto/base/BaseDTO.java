package com.example.gongbangwa.dto.base;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public class BaseDTO {

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;

}
