package com.example.test_web.domain.Identity.service;

import com.example.test_web.domain.Identity.dto.IdentityRequestDTO;
import com.example.test_web.domain.Identity.entity.Identity;
import com.example.test_web.domain.Identity.repository.IdentityRepository;
import com.example.test_web.domain.sinner.entity.Sinner;
import com.example.test_web.domain.sinner.repository.SinnerRepository;
import com.example.test_web.global.exception.BizException;
import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IdentityService {

    private final SinnerRepository sinnerRepository;
    private final IdentityRepository identityRepository;

    @Autowired
    public IdentityService(SinnerRepository sinnerRepository, IdentityRepository identityRepository){
        this.sinnerRepository = sinnerRepository;
        this.identityRepository = identityRepository;
    }

    @Transactional
    public String insertIdentity(IdentityRequestDTO identityRequestDTO, String accessToken) throws IOException {
        Sinner sinner = sinnerRepository.findById(identityRequestDTO.getSinnerId())
                .orElseThrow(() -> new BizException(ErrorCode.NO_IDENTITY_FOUND));

        // Entity 변환 및 저장
        Identity identity = Identity.toEntity(identityRequestDTO, sinner);
        identity = identityRepository.save(identity);

        if(identityRequestDTO.getImgUrl().isEmpty()){
            ImageUtil.saveImage(identityRequestDTO.getFile(), String.valueOf(sinner.getId()), String.valueOf(identity.getId()));
        } else {
            ImageUtil.downloadImage(identityRequestDTO.getImgUrl(), String.valueOf(sinner.getId())+ "\\" +String.valueOf(identity.getId()));
        }

        identity.uploadImgPath(String.valueOf(identity.getId()));

        return null;
    }
}
