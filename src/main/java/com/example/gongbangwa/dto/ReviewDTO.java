package com.example.gongbangwa.dto;

import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

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

    private int acno;

}
