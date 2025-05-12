package com.example.test_web.domain.Identity.dto;

import com.example.test_web.global.validator.GradeVaild;
import com.example.test_web.global.validator.ImgValid;
import com.example.test_web.global.validator.SeasonVaild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class IdentityRequestDTO {
    private String name;

    @SeasonVaild
    private Integer season;

    @GradeVaild
    private Integer grade;
    private Long sinnerId;

    @ImgValid
    private MultipartFile file;
}
