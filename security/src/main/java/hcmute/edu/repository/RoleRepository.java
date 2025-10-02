package hcmute.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.edu.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
	<S extends Role> S save(S role);
}
