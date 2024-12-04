package com.example.gongbangwa.repository.search;

import com.example.gongbangwa.dto.search.UserSearchDTO;
import com.example.gongbangwa.entity.Memberuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberuserSearch {

    Page<Memberuser> jpqlQuerygetMemberuserPage(UserSearchDTO userSearchDTO, Pageable pageable);
}
