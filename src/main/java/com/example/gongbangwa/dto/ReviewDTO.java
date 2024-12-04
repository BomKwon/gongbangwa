package com.example.gongbangwa.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private int arno;

    private String content;

    private String writer;

    private int ano;

    private int lno;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;


}
