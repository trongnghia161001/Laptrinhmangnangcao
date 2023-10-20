package com.laptrinhmangnangcao.ltm.repository;

import com.laptrinhmangnangcao.ltm.entity.DocumentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentsRepository extends JpaRepository<DocumentsEntity, Long> {

    List<DocumentsEntity> findByUserId(Long id);
}
