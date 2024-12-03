package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Lesson;
import com.example.gongbangwa.entity.LessonImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonImgRepository extends JpaRepository<LessonImg, Integer> {

    List<LessonImg> findByLesson_Lno(int lno);

    Integer deleteByLesson_Lno(int lno);

}
