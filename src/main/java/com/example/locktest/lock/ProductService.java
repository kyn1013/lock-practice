package com.example.locktest.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void createProduct(Product product) {
        // 상품 생성 시 버전 값은 자동으로 0으로 설정됩니다.
        productRepository.save(product);
    }

    @Transactional
    public void updateProductQuantity(Long productId, int quantity) {
        try {
            // 1. 상품 조회 (버전 정보 포함)
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

            // 2. 수량 업데이트
            product.setQuantity(product.getQuantity() + quantity);

            // 3. 저장 (낙관적 락을 통해 충돌을 감지)
            productRepository.save(product);
        } catch (OptimisticLockingFailureException e) {
            // 버전 충돌이 발생한 경우 처리
            System.out.println("Optimistic lock failed, version conflict detected.");
            // 예외 처리 로직 추가 (예: 재시도, 사용자 알림 등)
        }
    }
}

