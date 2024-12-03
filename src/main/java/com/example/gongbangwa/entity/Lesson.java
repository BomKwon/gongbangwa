package com.example.gongbangwa.entity;

import com.example.gongbangwa.constant.LessonStatus;
import com.example.gongbangwa.constant.ResStatus;
import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "lesson")
@NoArgsConstructor
public class Lesson extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lno;

    @Column
    private String lessonNm;

    @Lob
    @Column
    private String lessonDetail;

    @Column
    private int lessonPrice;

    @Column
    private int lessonDifficulty;

    @Column
    private int lessonStock;



    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus; //예약 상태 (가능 불가)

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ano")
    private Atelier atelier;

    @Column(columnDefinition = "Integer default 0", nullable = false)
    private int lessonView;   //조회수

    //앞 나 to 뒤 데려오는애
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LessonImg> lessonImgList = new ArrayList<>();


    //추후 추가함
    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveLesson> reserveLessons;



    public Lesson(int lno, String lessonNm, String lessonDetail, int lessonPrice, int lessonDifficulty, int lessonStock, Atelier atelier) {
        this.lno = lno;
        this.lessonNm = lessonNm;
        this.lessonDetail = lessonDetail;
        this.lessonPrice = lessonPrice;
        this.lessonDifficulty = lessonDifficulty;
        this.lessonStock = lessonStock;
//        this.atelier = atelier;
    }



    //수량을 입력받아서 db의 저장된 개수확인과 , 수량변경
    public void removeStock(int lessonStock) throws Exception {   //구매수량
        //이미 이 entity는 select를 통해서 값을 가져와서
        //entitymanager가 데이터를 가지고 있다.
        //그래서 수정이 가능하다

        int restStock = this.lessonStock - lessonStock;

        if(restStock < 0) {
            throw new Exception("상품의 재고가 부족합니다. " +
                    "(현재 재고수량 : " + this.lessonStock + ")");
        }

        this.lessonStock = restStock;
        this.lessonStatus = lessonStatus.valueOf("IMPOSSIBLE");

    }



    //승낙을 눌렀을 때
    public void consent(ResStatus resStatus){  //승낙할때

        this.lessonStatus = lessonStatus.valueOf("IMPOSSIBLE");
    }

    //취소를 눌렀을때
    public void cancel(int count){  //의뢰 취소할때

        this.lessonStatus = lessonStatus.valueOf("POSSIBLE");
        this.lessonStock += lessonStock;
    }

}
