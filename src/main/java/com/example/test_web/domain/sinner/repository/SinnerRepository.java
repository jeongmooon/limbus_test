package com.example.test_web.domain.sinner.repository;

import com.example.test_web.domain.sinner.entity.Sinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinnerRepository extends JpaRepository<Sinner, Long> {
}
