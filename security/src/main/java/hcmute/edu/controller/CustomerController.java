package hcmute.edu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hcmute.edu.entity.Customers;

@RestController
@EnableMethodSecurity
public class CustomerController {
	final private List<Customers> customers = List.of(
		new Customers.Builder().id("1").name("Nguyễn Lâm Tấn").email("tan@gmail.com").build(),
		new Customers.Builder().id("2").name("Trần Văn A").email("a@gmail.com").build()
	);
	@GetMapping("/hello")
	public ResponseEntity<String> getAllCustomers() {
		return ResponseEntity.ok("hello");
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customers>> getAllCustomer() {
		return ResponseEntity.ok(customers);
	}
	@GetMapping("/customers/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Customers> getCustomerById() {
		List<Customers> customers = this.customers.stream().filter(c -> c.getId().equals("id")).toList();
		return ResponseEntity.ok(customers.get(0));
	}
	
	
}
