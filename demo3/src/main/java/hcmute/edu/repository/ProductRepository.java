package hcmute.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.edu.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	

}
