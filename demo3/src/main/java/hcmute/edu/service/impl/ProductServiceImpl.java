package hcmute.edu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import hcmute.edu.entity.Product;
import hcmute.edu.repository.ProductRepository;
import hcmute.edu.service.inter.ProductService;

public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	@Override
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
		
	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product setProductById(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

}
