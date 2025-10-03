package hcmute.edu.service.inter;

import java.util.List;

import hcmute.edu.entity.Product;

public interface ProductService {
	void deleteProductById(Long id);
	Product getProductById(Long id);
	Product setProductById(Product product);
	
	List<Product> getAllProducts();
	

}
