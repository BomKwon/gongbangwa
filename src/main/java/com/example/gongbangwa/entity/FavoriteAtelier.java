package com.example.gongbangwa.entity;

import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "favorite_atelier")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
// 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FavoriteAtelier extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fano; //상품코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fno")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Favorite favorite;              // 즐겨찾기

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ano")
    private Atelier atelier;              //아이템



    // 카트에 담길 아이템을 참조하는 cartItem
    public static FavoriteAtelier createFavoriteAtelier (Favorite favorite, Atelier atelier) {
        FavoriteAtelier favoriteAtelier = new FavoriteAtelier();
        favoriteAtelier.setFavorite(favorite);
        favoriteAtelier.setAtelier(atelier);
        return favoriteAtelier;
    }


}
