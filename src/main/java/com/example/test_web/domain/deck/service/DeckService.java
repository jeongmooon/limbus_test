package com.example.test_web.domain.deck.service;

import com.example.test_web.domain.Identity.entity.Identity;
import com.example.test_web.domain.Identity.projection.IdentityProjection;
import com.example.test_web.domain.Identity.repository.IdentityRepository;
import com.example.test_web.domain.deck.dto.DeckDTO;
import com.example.test_web.domain.deck.dto.DeckIdentityDTO;
import com.example.test_web.domain.deck.dto.DeckListDTO;
import com.example.test_web.domain.deck.dto.DeckRequestDTO;
import com.example.test_web.domain.deck.entity.Deck;
import com.example.test_web.domain.deck.entity.DeckIdentity;
import com.example.test_web.domain.deck.entity.DeckList;
import com.example.test_web.domain.deck.repository.DeckListRepository;
import com.example.test_web.domain.user.entity.UserInfo;
import com.example.test_web.domain.user.repository.UserInfoRepository;
import com.example.test_web.domain.userIdentity.dto.UserIdentityDTO;
import com.example.test_web.domain.userIdentity.dto.UserSinnerDTO;
import com.example.test_web.domain.userIdentity.entity.UserIdentity;
import com.example.test_web.global.exception.BizException;
import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DeckService {
    private final UserInfoRepository userInfoRepository;
    private final IdentityRepository identityRepository;
    private final DeckListRepository deckListRepository;

    @Autowired
    public DeckService(UserInfoRepository userInfoRepository, IdentityRepository identityRepository, DeckListRepository deckListRepository){
        this.userInfoRepository = userInfoRepository;
        this.identityRepository = identityRepository;
        this.deckListRepository = deckListRepository;
    }

    public List<UserSinnerDTO> getIdentityList(String accessToken){
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

        return groupedIdentities.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> UserSinnerDTO.builder()
                        .id(entry.getKey())
                        .name(sinnerNames.get(entry.getKey()))
                        .userIdentities(entry.getValue())
                        .build())
                .toList();
    }

    @Transactional
    public String addDeckList(List<DeckRequestDTO> request, String accessToken){
        Long userId = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        UserInfo user = userInfoRepository.getReferenceById(userId);
        Set<Long> identityIdSet = new HashSet<>();
        Set<Long> sinnerIdSet = new HashSet<>();
        List<Deck> decks = new ArrayList<>();
        String uuid = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        DeckList deckList = null;

        if(request.get(0).getUuid() != null && !request.get(0).getUuid().isEmpty()){
            uuid = request.get(0).getUuid();
            // 기존 덱 리스트 조회
            deckList = deckListRepository.findByUserIdAndUuid(userId, uuid)
                    .orElseThrow(() -> new BizException(ErrorCode.NO_DECK_LIST_DATA));
        }

        for (DeckRequestDTO deckRequest : request) {
            List<DeckIdentity> deckIdentities = new ArrayList<DeckIdentity>();
            List<Identity> identities = identityRepository.findAllById(deckRequest.getIdentityList());

            int i = 0;
            for (Identity identity : identities) {
                if (!sinnerIdSet.add(identity.getSinner().getId())) {
                    throw new BizException(ErrorCode.DUPLICATED_DECK_SINNER);
                }
                if (!identityIdSet.add(identity.getId())) {
                    throw new BizException(ErrorCode.DUPLICATED_DECK_IDENTITY);
                }
                deckIdentities.add(DeckIdentity.builder()
                        .identity(identity)
                        .slotOrder(i++)
                        .build());
            }

            // Deck 객체 생성
            Deck deck = Deck.builder()
                    .name(deckRequest.getName())
                    .deckIdentities(deckIdentities)
                    .build();

            // 양방향 관계 설정: Deck -> DeckIdentity
            for (DeckIdentity di : deckIdentities) {
                di.setDeck(deck); // DeckIdentity에 Deck 설정
            }

            decks.add(deck);
            identityIdSet.clear();
            sinnerIdSet.clear();
        }

        if(request.get(0).getUuid() != null && !request.get(0).getUuid().isEmpty()){
            deckList.updateDeckList(decks);
        } else {
            // DeckList 객체 생성
            deckList = DeckList.builder()
                    .uuid(uuid)
                    .deckList(decks)
                    .user(user)
                    .registDate(LocalDate.now())
                    .build();

            // 양방향 관계 설정: Deck -> DeckList
            for (Deck deck : decks) {
                deck.setDeckList(deckList); // Deck에 DeckList 설정
            }

            // 저장
            deckListRepository.save(deckList);
        }

        return "success";
    }

    public void getDeckList(String accessToken){
        Long id = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        List<DeckList> list = deckListRepository.findByUserId(id);
        for(DeckList deckList : list){
            for(Deck deck : deckList.getDeckList()){
                System.out.println(deck.getName());
                for(DeckIdentity deckIdentity : deck.getDeckIdentities()){
                    System.out.println(deckIdentity.getIdentity().getName()+"/"+deckIdentity.getIdentity().getSinner().getName());
                }
            }
        }
    }

    public List<String> getDeckListUuidByUserId(String accessToken){
        Long id = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        List<DeckList> list = deckListRepository.findByUserId(id);
        //if(list.isEmpty()) throw new BizException(ErrorCode.NO_DECK_LIST_DATA);
        return list.stream().map(DeckList::getUuid).toList();
    }

    public DeckListDTO getDeckListByUserIdAndUuid(String accessToken, String uuid){
        Long id = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        DeckList deckList = deckListRepository.findByUserIdAndUuid(id, uuid)
                .orElseThrow(() -> new BizException(ErrorCode.NO_DECK_LIST_DATA));

        List<DeckDTO> deckListDTO = new ArrayList<>();

        for(Deck deck : deckList.getDeckList()){
            List<DeckIdentity> deckIdentities = deck.getDeckIdentities();
            deckIdentities.sort(Comparator.comparing(DeckIdentity::getSlotOrder));
            List<DeckIdentityDTO> deckIdentityDto = new ArrayList<>();

            for(DeckIdentity deckIdentity : deckIdentities){
                Identity identity = deckIdentity.getIdentity();
                Long sinnerId = identity.getSinner().getId();
                Long identityId = identity.getId();
                String imgName = identity.getImgPath();

                String imgPath = String.format("/image/prisoner/%d/%s.webp", sinnerId, imgName);

                deckIdentityDto.add(DeckIdentityDTO.builder()
                        .identityId(identityId)
                        .sinnerId(sinnerId)
                        .imgPath(imgPath)
                        .build());
            }

            deckListDTO.add(DeckDTO.builder()
                    .name(deck.getName())
                    .deckIdentity(deckIdentityDto)
                    .build());
        }

        return DeckListDTO.builder()
                .uuid(deckList.getUuid())
                .deckList(deckListDTO)
                .build();
    }
}
