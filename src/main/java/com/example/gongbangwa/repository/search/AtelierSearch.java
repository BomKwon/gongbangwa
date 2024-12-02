package com.example.gongbangwa.repository.search;


import com.example.gongbangwa.dto.search.AtelierSearchDTO;
import com.example.gongbangwa.entity.Atelier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtelierSearch {


    Page<Atelier> jpqlQuerygetAtelierPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable);

//    Page<MainQuestDTO> searchQuest(String[] types, String keyword, Pageable pageable);


}
