package com.example.test_web.domain.sinner.service;

import com.example.test_web.domain.sinner.dto.SinnerDTO;
import com.example.test_web.domain.sinner.repository.SinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinnerService {
    private final SinnerRepository sinnerRepository;

    @Autowired
    public SinnerService(SinnerRepository sinnerRepository){
        this.sinnerRepository = sinnerRepository;
    }

    public List<SinnerDTO> getSinnerList(){
        return sinnerRepository.findAll().stream()
                .map(SinnerDTO::toDTO)
                .toList();
    }
}
