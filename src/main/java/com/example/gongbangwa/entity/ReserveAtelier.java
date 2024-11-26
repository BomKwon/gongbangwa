package com.example.gongbangwa.entity;

import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "reserve_atelier")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
// 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
@ToString
public class ReserveAtelier extends Base {  //예약한수업

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rano;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rno")
    private Reserve reserve;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "acno")
    private AtelierClass atelierClass;

    private String acNm;

    private int reservePrice;

    private int count;  //수량  예약마감을 표시하기 위해

    public static ReserveAtelier createReserveAterlierClass(AtelierClass atelierClass, int count) throws Exception {
        //atelier는 검색한 아이템 //removeStock()를 통해서 수량 변경
        ReserveAtelier reserveAterlier = new ReserveAtelier();

        reserveAterlier.setAtelierClass(atelierClass);        //예약한 수업
        reserveAterlier.setCount(count);      //구매수량

        reserveAterlier.setAcNm(atelierClass.getAcNm());

        reserveAterlier.setReservePrice(atelierClass.getAcPrice());

        atelierClass.removeStock(count);

        return reserveAterlier;
    }
    public int getTotalPrice(){     //주문한 금액
        return reservePrice * count;
    }


    public void cancel(){
        this.getAtelierClass().cancel(count);
    }

}
