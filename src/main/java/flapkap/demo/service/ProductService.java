package flapkap.demo.service;

import flapkap.demo.model.Product;
import flapkap.demo.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product createProduct(Product product){
        return productRepo.save(product);
    }
    public Optional<Product> getProductById(Long id){
        return productRepo.findById(id);
    }
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }
    public void deleteProduct(Long id){
        productRepo.deleteById(id);
    }
}
