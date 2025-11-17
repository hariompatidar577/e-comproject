package com.hariom.e_com.project.controller;

import com.hariom.e_com.project.product.Product;
import com.hariom.e_com.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
//@Service


public class ProductController {
    @Autowired

    private ProductService service;

//    @RequestMapping("/")
//    public String greet() {
//        return "Hi , Hariom ";
//    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {

        return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);

    }

//    @PostMapping
//    public Product addProduct(@RequestBody Product product) {
//

    /// /        Product product1=service.getProductById()
//
//        return service.addProduct(product);
//
//    }
    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {

        Product product = service.getProductById(id);

        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

//    @PutMapping("/{id}")
//    public Product UpdateProduct(@PathVariable int id, @RequestBody Product newProduct) {
//        return service.updateProduct(id, newProduct);
//    }


    //@GetMapping("/product/{id}")
//public ResponseEntity<Product> getProduct(@PathVariable int id){
//
//        Product product=service.getProductById(id);
//
//        if (product !=null){
//            return new ResponseEntity<>(product , HttpStatus.OK);
//
//        }else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//        }
//}
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {

        try {
            System.out.println("🟢 Product received: " + product);
            System.out.println("🟢 Image received: " + imageFile.getOriginalFilename());

//            System.out.println(product);
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {

        Product product = service.getProductById(productId);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (product.getImageData() == null || product.getImageType() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(product.getImageData());
    }



    @PostMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) {

        Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);

        } catch (IOException e) {
            return new ResponseEntity<>("failed t update", HttpStatus.BAD_REQUEST);
        }

        if (product1 != null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("failed to update", HttpStatus.BAD_REQUEST);

    }



    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {

        Product product = service.getProductById(id);

        if (product != null){
            service.deleteProduct(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
        else
            return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);


    }

    @GetMapping("/products/search")
    public ResponseEntity <List<Product>> searchProducts(@RequestParam String keyword) {

//        System.out.println("search with"+keyword);
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products , HttpStatus.OK);



    }}

