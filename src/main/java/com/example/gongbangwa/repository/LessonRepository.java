package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    Page<Lesson> findByLessonNmContaining(String keyword, Pageable pageable);
    Page<Lesson> findByLessonDifficultyContainingAndLessonNmContaining(String keyword, String keyword1, Pageable pageable);
    Page<Lesson> findByLessonDifficultyContaining(String keyword, Pageable pageable);

}
