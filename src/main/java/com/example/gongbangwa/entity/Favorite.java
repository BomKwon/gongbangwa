package com.example.gongbangwa.entity;

import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "favorite")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
// 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Favorite extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cno")
    private Customer customer; //공방이용자

    @OneToMany(mappedBy = "favorite" , cascade = CascadeType.ALL, orphanRemoval = true
            , fetch = FetchType.LAZY)
    private List<FavoriteAtelier> favoritQuests = new ArrayList<>();

    //즐겨찾기 등록
    public static Favorite createfavorit(Customer customer) {
        Favorite favorite = new Favorite();
        favorite.setCustomer(customer);
        return favorite;
    }

}
