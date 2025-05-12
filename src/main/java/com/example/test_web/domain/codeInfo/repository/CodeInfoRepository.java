package com.example.test_web.domain.codeInfo.repository;

import com.example.test_web.domain.codeInfo.entity.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeInfoRepository extends JpaRepository<CodeInfo, String> {
}

