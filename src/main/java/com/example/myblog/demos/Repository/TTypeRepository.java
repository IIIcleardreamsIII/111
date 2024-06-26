package com.example.myblog.demos.Repository;

import com.example.myblog.demos.pojo.TType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TTypeRepository extends JpaRepository<TType, Long> {
    TType findByName(String name);
}
