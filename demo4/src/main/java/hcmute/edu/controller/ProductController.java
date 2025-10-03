package hcmute.edu.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import hcmute.edu.entity.Product;
import hcmute.edu.repository.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {
	private final ProductRepository repo;

	public ProductController(ProductRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("products", repo.findAll());
		return "products";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String newForm(Model model) {
		model.addAttribute("product", new Product());
		return "new_product";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public String create(@Valid @ModelAttribute Product product, BindingResult br) {
		if (br.hasErrors())
			return "new_product";
		repo.save(product);
		return "redirect:/products";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("product", repo.findById(id).orElseThrow());
		return "edit_product";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id, @Valid @ModelAttribute Product product, BindingResult br) {
		if (br.hasErrors())
			return "edit_product";
		product.setId(id);
		repo.save(product);
		return "redirect:/products";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		repo.deleteById(id);
		return "redirect:/products";
	}
}
