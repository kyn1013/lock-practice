package com.example.locktest.lock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 기본적인 CRUD 메서드들이 제공됩니다.
}
