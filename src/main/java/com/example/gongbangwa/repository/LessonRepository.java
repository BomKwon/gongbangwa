package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    Page<Lesson> findByAcNmContaining(String keyword, Pageable pageable);
    Page<Lesson> findByAcDifficultyContainingAndAcNmContaining(String keyword, String keyword1, Pageable pageable);
    Page<Lesson> findByAcDifficultyContaining(String keyword, Pageable pageable);

}
