package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Favorite;
import com.example.gongbangwa.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
