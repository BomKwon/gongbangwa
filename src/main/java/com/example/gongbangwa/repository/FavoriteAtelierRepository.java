package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Favorite;
import com.example.gongbangwa.entity.FavoriteAtelier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteAtelierRepository extends JpaRepository<FavoriteAtelier, Integer> {
}
