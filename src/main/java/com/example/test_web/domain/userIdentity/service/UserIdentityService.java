package com.example.test_web.domain.userIdentity.service;

import com.example.test_web.domain.Identity.entity.Identity;
import com.example.test_web.domain.Identity.projection.IdentityProjection;
import com.example.test_web.domain.Identity.repository.IdentityRepository;
import com.example.test_web.domain.sinner.repository.SinnerRepository;
import com.example.test_web.domain.user.entity.UserInfo;
import com.example.test_web.domain.user.repository.UserInfoRepository;
import com.example.test_web.domain.userIdentity.dto.UserDictionaryDTO;
import com.example.test_web.domain.userIdentity.dto.UserIdentityDTO;
import com.example.test_web.domain.userIdentity.dto.UserIdentityRequestDTO;
import com.example.test_web.domain.userIdentity.dto.UserSinnerDTO;
import com.example.test_web.domain.userIdentity.entity.UserIdentity;
import com.example.test_web.domain.userIdentity.repository.UserIdentityRepository;
import com.example.test_web.global.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserIdentityService {

    private final UserInfoRepository userInfoRepository;
    private final SinnerRepository sinnerRepository;
    private final IdentityRepository identityRepository;
    private final UserIdentityRepository userIdentityRepository;

    @Autowired
    public UserIdentityService(UserInfoRepository userInfoRepository, SinnerRepository sinnerRepository, IdentityRepository identityRepository, UserIdentityRepository userIdentityRepository){
        this.userInfoRepository = userInfoRepository;
        this.sinnerRepository = sinnerRepository;
        this.identityRepository = identityRepository;
        this.userIdentityRepository = userIdentityRepository;
    }

    public UserDictionaryDTO getUsreIdentity(String accessToken){
        Long id = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        List<IdentityProjection> projections = identityRepository.findAllWithOwnershipByUserId(id);

        // Sinner ID 기준으로 그룹핑
        Map<Long, List<UserIdentityDTO>> groupedIdentities = new LinkedHashMap<>();
        Map<Long, String> sinnerNames = new HashMap<>();

        for (IdentityProjection projection : projections) {
            Long sinnerId = projection.getSinnerId();

            // Identity 리스트에 추가
            groupedIdentities.computeIfAbsent(sinnerId, k -> new ArrayList<>()).add(
                    UserIdentityDTO.builder()
                            .id(projection.getIdentityId())
                            .name(projection.getIdentityName())
                            .grade(projection.getIdentityGrade())
                            .imgPath(projection.getIdentityImgPath())
                            .isOwned(projection.getIsOwned() == 1)
                            .build()
            );

            // Sinner 이름 저장
            sinnerNames.putIfAbsent(sinnerId, projection.getSinnerName());
        }

        List<UserSinnerDTO> userSinnerList = groupedIdentities.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> UserSinnerDTO.builder()
                        .id(entry.getKey())
                        .name(sinnerNames.get(entry.getKey()))
                        .userIdentities(entry.getValue())
                        .build())
                .toList();
        int identityTotalCount = 0;
        int ownedCount = 0;
        for (UserSinnerDTO sinner : userSinnerList) {
            identityTotalCount += sinner.getUserIdentities().size();

            ownedCount += (int) sinner.getUserIdentities().stream()
                    .filter(UserIdentityDTO::isOwned)
                    .count();
        }

        return UserDictionaryDTO.builder()
                .prisonerGuid(String.valueOf(ownedCount))
                .sinnerIdentityCount(identityTotalCount)
                .rate(String.valueOf((ownedCount* 100/identityTotalCount)))
                .userSinnerList(userSinnerList)
                .build();
    }

    @Transactional
    public void insertUsreIdentity(UserIdentityRequestDTO userIdentityRequestDTO, String accessToken){
        Long userId = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        UserInfo user = userInfoRepository.getReferenceById(userId);

        List<UserIdentity> toSave = new ArrayList<>();

        // 1. 삭제할 인격 제거
        if (userIdentityRequestDTO.getDeleteIdentity() != null && !userIdentityRequestDTO.getDeleteIdentity().isEmpty()) {
            userIdentityRepository.deleteByUserIdAndIdentityIds(userId, userIdentityRequestDTO.getDeleteIdentity());
        }

        // 2. 추가할 인격 insert
        if (userIdentityRequestDTO.getAddIdentity() != null && !userIdentityRequestDTO.getAddIdentity().isEmpty()) {
            List<Identity> identities = identityRepository.findAllById(userIdentityRequestDTO.getAddIdentity());
            for (Identity identity : identities) {
                UserIdentity userIdentity = UserIdentity.builder()
                                            .user(user)
                                            .identity(identity)
                                            .registDate(LocalDate.now())
                                            .build();
                toSave.add(userIdentity);
            }
            userIdentityRepository.saveAll(toSave);
        }
    }
}
