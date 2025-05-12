package com.example.test_web.domain.deck.controller;

import com.example.test_web.domain.deck.dto.DeckListDTO;
import com.example.test_web.domain.deck.dto.DeckRequestDTO;
import com.example.test_web.domain.deck.dto.DeckResponseDTO;
import com.example.test_web.domain.deck.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/deck")
public class DeckController {

    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService){
        this.deckService = deckService;
    }

    @GetMapping
    public String getDeckPage(@CookieValue("accessToken") String accessToken, Model model){
        model.addAttribute("body", "deck/deck");
        model.addAttribute("deckListCode",deckService.getDeckListUuidByUserId(accessToken));
        model.addAttribute("sinnerList",deckService.getIdentityList(accessToken));
        return "layout";
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DeckListDTO> getDeckListByUserIdAndUuid(@PathVariable("uuid")String uuid, @CookieValue("accessToken") String accessToken){
        return ResponseEntity.ok(deckService.getDeckListByUserIdAndUuid(accessToken, uuid));
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<DeckResponseDTO> addDeckList(@RequestBody List<DeckRequestDTO> request, @CookieValue("accessToken") String accessToken){
//        deckService.getDeckList(accessToken);
        return ResponseEntity.ok(DeckResponseDTO.builder().message(deckService.addDeckList(request, accessToken)).build());
    }
}
