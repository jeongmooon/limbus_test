package com.example.test_web.domain.codeInfo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "code_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CodeInfo {
    @Id
    private String cdId;
    private String value;

    public CodeInfo(String cdId, String value){
        this.cdId = cdId;
        this.value = value;
    }
}