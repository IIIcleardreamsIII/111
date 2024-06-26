package com.example.myblog.demos.Repository;

import com.example.myblog.demos.pojo.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TUserRepository extends JpaRepository<TUser, Long> {
    Optional<TUser> findByUsername(String username);
}
