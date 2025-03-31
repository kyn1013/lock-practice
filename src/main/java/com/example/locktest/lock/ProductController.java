package com.example.locktest.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 생성
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
            productService.createProduct(product);
            return ResponseEntity.ok("Product created successfully.");
    }

    // 상품 수량 업데이트
    @PutMapping("/{productId}/quantity")
    public ResponseEntity<String> updateProductQuantity(@PathVariable Long productId, @RequestParam int quantity) {
        productService.updateProductQuantity(productId, quantity);
        return ResponseEntity.ok("Product quantity updated successfully.");
    }
}
