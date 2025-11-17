package com.hariom.e_com.project.services;

import com.hariom.e_com.project.product.Product;
import com.hariom.e_com.project.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



//@Autowired
@Service
public class ProductService {

    @Autowired
    public ProductRepository repository;


    public List<Product> getAllProduct() {
        return repository.findAll();

    }

    public Product addProduct(Product product, MultipartFile imageFile) {
try {
    product.setImageName(imageFile.getOriginalFilename());
    product.setImageType(imageFile.getContentType());
    product.setImageData((imageFile.getBytes()));
    return repository.save(product);
} catch (IOException e) {
    e.printStackTrace();

    throw new RuntimeException("faild to store image "+e.getMessage());
}
    }

    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);

    }
    public Product updateProduct(int id, Product product ,MultipartFile imageFile)
            throws IOException {
//        Product existingProduct = repository.findById(id).orElse(null);

//        try {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData((imageFile.getBytes()));
            return repository.save(product);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
        }

    public void deleteProduct(int id){
        repository.deleteById(id);

    }

    public List<Product> searchProducts(String keyword) {
        return repository.searchProduct(keyword);
    }

//        if(existingProduct == null) return null;
//
//        existingProduct.setName(newProduct.getName());
//        existingProduct.setPrice(newProduct.getPrice());
//
//        return repository.save(existingProduct);


//    public String deleteProduct(int id) {
//       repository.deleteById(id);
//        return "Product Deleted Successfully!";
//    }



}
