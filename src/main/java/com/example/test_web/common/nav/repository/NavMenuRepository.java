package com.example.test_web.common.nav.repository;

import com.example.test_web.common.nav.entity.NavMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NavMenuRepository extends JpaRepository<NavMenu, Long> {
}
