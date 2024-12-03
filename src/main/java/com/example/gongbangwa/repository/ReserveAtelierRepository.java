package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.ReserveLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveAtelierRepository extends JpaRepository<ReserveLesson, Integer> {
}
