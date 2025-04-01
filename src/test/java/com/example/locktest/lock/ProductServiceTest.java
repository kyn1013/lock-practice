package com.example.locktest.lock;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        // 테스트용 상품 생성
        product = new Product();
        product.setName("Test Product");
        product.setQuantity(10);
        product = productRepository.save(product);
    }

    @Test
    void testOptimisticLocking() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Boolean> task1 = () -> {
            try {
                productService.updateProductQuantity(product.getId(), 5); // +5
                return true;
            } catch (OptimisticLockingFailureException e) {
                System.out.println("낙관적 락 때문에 트랜젝션 1 실패");
                return false;
            }
        };

        Callable<Boolean> task2 = () -> {
            try {
                productService.updateProductQuantity(product.getId(), 10); // +10
                return true;
            } catch (OptimisticLockingFailureException e) {
                System.out.println("낙관적 락 때문에 트랜젝션 2 실패");
                return false;
            }
        };


        Future<Boolean> future2 = executor.submit(task2);
        Future<Boolean> future1 = executor.submit(task1);
//        Future<Boolean> future2 = executor.submit(task2);

        boolean result1 = future1.get();
        boolean result2 = future2.get();

        executor.shutdown();

        // 하나는 성공하고, 하나는 실패해야 한다.
        Assertions.assertTrue(result1 != result2, "트랜젝션 1개는 낙관적 락 때문에 실패해야 함!");

        // 최종적으로 저장된 상품의 수량 확인
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        System.out.println("Final Quantity: " + updatedProduct.getQuantity());
    }
}