package com.example.gongbangwa.entity;

import com.example.gongbangwa.constant.ResStatus;
import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "reserve")
public class Reserve extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cno")
    private Memberuser memberuser;

    @OneToMany(mappedBy = "reserve" , cascade = CascadeType.ALL, orphanRemoval = true
            , fetch = FetchType.LAZY)
    private List<ReserveLesson> reserveLessons = new ArrayList<>();

    private LocalDateTime reserveDate;

    private int reservePrice;

    //예약 대기, 완료, 취소
    @Enumerated(EnumType.STRING)
    private ResStatus reserveStatus;


    public void addReserveItem(ReserveLesson reserveLesson){//아이템 하나를 받음
        reserveLessons.add(reserveLesson);      //위에 아이템리스트에 넣는다.
        //주문은 주문아이템들을  가지고 있다.
        reserveLesson.setReserve(this);   //주문아이템을 주문을 참조한다.
    }

    public static Reserve createreserve(Memberuser memberuser, List<ReserveLesson> reserveLessonList){
        Reserve reserve = new Reserve();  //저장에 쓰일 주문엔티티 만드는 메소드
        reserve.setMemberuser(memberuser);

        for (ReserveLesson reserveLesson : reserveLessonList){
            reserve.addReserveItem(reserveLesson);      //주문에 있는 주문아이템리스트 넣기 반복해서
        }

        reserve.setReserveStatus(ResStatus.WAITING);    //주문상태
        reserve.setReserveDate(LocalDateTime.now());    //주문시간

        //그렇게 만들어진 엔티티를 반환한다.
        return reserve;
    }

    public void cacelReserve(){
        this.reserveStatus = reserveStatus.CANCEL;

        for (ReserveLesson reserveLesson : reserveLessons){
            reserveLesson.cancel();
        }
    }



}
