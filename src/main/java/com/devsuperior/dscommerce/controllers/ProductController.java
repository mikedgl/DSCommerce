package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        try {
            ProductDTO productDTO = productService.findById(id);
            return ResponseEntity.ok(productDTO);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> pagedSearch(Pageable pageable){
        return ResponseEntity.ok(productService.pagedSearch(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO){
        ProductDTO persistedProduct = productService.saveProduct(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(persistedProduct.getId()).toUri();
        return ResponseEntity.created(uri).body(persistedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
