package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> pagedSearch(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO){
        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        productRepository.delete(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        copyProperties(productDTO, product);
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    private void copyProperties(ProductDTO origin, Product destiny){
        destiny.setName(origin.getName());
        destiny.setDescription(origin.getDescription());
        destiny.setPrice(origin.getPrice());
        destiny.setImgUrl(origin.getImgUrl());
    }

}
