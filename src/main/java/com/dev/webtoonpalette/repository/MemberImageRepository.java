package com.dev.webtoonpalette.repository;

import com.dev.webtoonpalette.domain.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
    void deleteByFileName (String fileName);
}
