package hcmute.edu.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import hcmute.edu.entity.Role;
import hcmute.edu.entity.Users;
import hcmute.edu.repository.RoleRepository;
import hcmute.edu.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {

    // 1) Password encoder (bắt buộc: lưu/match password đã mã hoá)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//     2) In-memory users
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("tan"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("tanadmin")
//                .password(passwordEncoder.encode("adminpass"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    // 3) SecurityFilterChain: thay thế WebSecurityConfigurerAdapter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // demo: tắt CSRF cho dễ test (KHÔNG khuyến nghị cho production)
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/user/new").permitAll() // static resources
                .requestMatchers("/hello").permitAll()          // public
                .requestMatchers("/admin/**").hasRole("ADMIN")  // admin-only
                .anyRequest().authenticated()                   // còn lại cần đăng nhập
            )
            .formLogin(Customizer.withDefaults()) // bật form-login (Spring sẽ cung cấp login page mặc định). 
            .httpBasic(Customizer.withDefaults()); // cho curl/basic auth testing

        return http.build();
    }
    
    // Cấu hình DaoAuthenticationProvider (nếu dùng UserDetailsService)
    @SuppressWarnings("deprecation")
	@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider prov = new DaoAuthenticationProvider();
        prov.setUserDetailsService(uds);
        prov.setPasswordEncoder(encoder);
        return prov;
    }

    // Expose AuthenticationManager (nếu cần)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Security filter chain
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
//        http.authenticationProvider(authProvider)
//            .csrf(csrf -> csrf.disable()) // demo: tắt CSRF để test form bằng curl (KHÔNG khuyến nghị production)
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/login","/register","/css/**","/js/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//            )
//            .formLogin(form -> form
//                .loginPage("/login")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/login?error=true")
//            )
//            .logout(logout -> logout
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/login?logout=true")
//            );
//        return http.build();
//    }
//    
//    @Bean
//    CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
//        return args -> {
//            // Sử dụng orElse thay vì orElseGet
//            Role rUser = roleRepo.findByName("USER")
//                    .orElse(roleRepo.save(new Role("USER")));
//            
//            Role rAdmin = roleRepo.findByName("ADMIN")
//                    .orElse(roleRepo.save(new Role("ADMIN")));
//
//            // Phần còn lại giữ nguyên...
//            if (!userRepo.existsByUsername("user")) {
//                Users u = new Users();
//                u.setUsername("user"); 
//                u.setPassword(encoder.encode("password")); 
//                u.setEnabled(true);
//                u.getRoles().add(rUser);
//                userRepo.save(u);
//            }
//
//            if (!userRepo.existsByUsername("admin")) {
//                Users a = new Users();
//                a.setUsername("admin"); 
//                a.setPassword(encoder.encode("adminpass")); 
//                a.setEnabled(true);
//                a.getRoles().add(rAdmin);
//                userRepo.save(a);
//            }
//        };
//    }

}
