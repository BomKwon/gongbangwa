package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
}
