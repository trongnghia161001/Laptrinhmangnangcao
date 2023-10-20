package com.laptrinhmangnangcao.ltm.repository;

import com.laptrinhmangnangcao.ltm.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RolesEntity, Long> {
    RolesEntity findByCode(String code);
}
