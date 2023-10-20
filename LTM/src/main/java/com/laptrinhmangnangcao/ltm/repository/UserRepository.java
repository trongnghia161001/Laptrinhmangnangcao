package com.laptrinhmangnangcao.ltm.repository;

import com.laptrinhmangnangcao.ltm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByUserNameAndStatus(String name, int status);
    Page<UsersEntity> findByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(String userName, String fullName, int status,
                                                                                                   Pageable pageable);
    Page<UsersEntity> findByStatusNot(int status, Pageable pageable);
    long countByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(String userName, String fullName, int status);
    long countByStatusNot(int status);
    UsersEntity findByUserName(String userName);

}
