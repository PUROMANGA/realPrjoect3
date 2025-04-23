package com.example.minzok.menu.Repository;

import com.example.minzok.menu.Entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
