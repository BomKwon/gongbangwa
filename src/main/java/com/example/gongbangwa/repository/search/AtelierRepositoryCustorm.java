package com.example.gongbangwa.repository.search;

import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.dto.search.AtelierSearchDTO;
import com.example.gongbangwa.entity.Atelier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtelierRepositoryCustorm {

    Page<Atelier> getAtelierPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable);

    Page<AtelierDTO> getMainAtelierPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable);

//    Page<MainQuestDTO> getUserQuestPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable);

}
