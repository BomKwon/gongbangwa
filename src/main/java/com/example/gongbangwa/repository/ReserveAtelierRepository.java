package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Favorite;
import com.example.gongbangwa.entity.ReserveAtelier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveAtelierRepository extends JpaRepository<ReserveAtelier, Integer> {
}
