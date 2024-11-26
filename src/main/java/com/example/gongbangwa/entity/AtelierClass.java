package com.example.gongbangwa.entity;

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
@Table(name = "atelier_class")
@NoArgsConstructor
public class AtelierClass extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int acno;

    @Column
    private String acNm;

    @Lob
    @Column
    private String acDetail;

    @Column
    private int acPrice;

    @Column
    private int acDifficulty;

    @Column
    private int acStock;



    @Enumerated(EnumType.STRING)
    private ResStatus resStatus; //예약 상태

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ano")
    private Atelier atelier;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int acView;   //조회수

    //앞 나 to 뒤 데려오는애
    @OneToMany(mappedBy = "atelier_class", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AtelierClassImg> atelierClassImgList = new ArrayList<>();


    //추후 추가함
    @OneToMany(mappedBy = "atelier_class", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveAtelier> reserveAteliers;



    public AtelierClass(int acno, String acNm, String acDetail, int acPrice, int acDifficulty, int acStock, Atelier atelier) {
        this.acno = acno;
        this.acNm = acNm;
        this.acDetail = acDetail;
        this.acPrice = acPrice;
        this.acDifficulty = acDifficulty;
        this.acStock = acStock;
        this.atelier = atelier;
    }



    //수량을 입력받아서 db의 저장된 개수확인과 , 수량변경
    public void removeStock(int acStock) throws Exception {   //구매수량
        //이미 이 entity는 select를 통해서 값을 가져와서
        //entitymanager가 데이터를 가지고 있다.
        //그래서 수정이 가능하다

        int restStock = this.acStock - acStock;

        if(restStock < 0) {
            throw new Exception("상품의 재고가 부족합니다. " +
                    "(현재 재고수량 : " + this.acStock + ")");
        }

        this.acStock = restStock;
        this.resStatus = ResStatus.valueOf("WAITING");

    }



    //승낙을 눌렀을 때
    public void consent(ResStatus resStatus){  //승낙할때

        this.resStatus = ResStatus.valueOf("SUCCESS");
    }

    //취소를 눌렀을때
    public void cancel(int count){  //의뢰 취소할때

        this.resStatus = ResStatus.valueOf("CANCEL");
        this.acStock += acStock;
    }

}
