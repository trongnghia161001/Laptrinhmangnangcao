package com.laptrinhmangnangcao.ltm.repository;

import com.laptrinhmangnangcao.ltm.entity.ErrorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ErrorsRepository extends JpaRepository<ErrorsEntity, Long> {

    List<ErrorsEntity> findByDocumentId(Long id);
}
