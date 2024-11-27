package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Favorite;
import com.example.gongbangwa.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
}
