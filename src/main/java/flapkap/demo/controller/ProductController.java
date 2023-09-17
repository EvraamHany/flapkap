package flapkap.demo.controller;

import flapkap.demo.model.Product;
import flapkap.demo.model.User;
import flapkap.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/product")
    public ResponseEntity<Product> createUser(Product product) {
        return ResponseEntity.status(200).body(productService.createProduct(product));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent())
            return ResponseEntity.status(200).body(product.get());
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<User> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(200).build();
    }


}
