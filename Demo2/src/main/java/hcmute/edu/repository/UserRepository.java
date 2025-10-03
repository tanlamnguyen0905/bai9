package hcmute.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.edu.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByUsername(String username);

	Optional<Users> findByEmail(String email);

	boolean existsByUsername(String username);
	<S extends Users> S save(S user);
}

