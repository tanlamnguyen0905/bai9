package hcmute.edu.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


import hcmute.edu.service.impl.CustomUserDetailService;
import hcmute.edu.service.impl.UserInfoUserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
@EnableMethodSecurity
public class SecurityConfig {

//    private final UserInfoUserDetailsService userInfoUserDetailsService;

//    private final UserInfoUserDetailsService userInfoUserDetailsService;

    @Autowired
	private CustomUserDetailService userDetailService;

//    SecurityConfig(UserInfoUserDetailsService userInfoUserDetailsService) {
//        this.userInfoUserDetailsService = userInfoUserDetailsService;
//    }

//    SecurityConfig(UserInfoUserDetailsService userInfoUserDetailsService) {
//        this.userInfoUserDetailsService = userInfoUserDetailsService;
//    }
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
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable()) // demo: tắt CSRF cho dễ test (KHÔNG khuyến nghị cho production)
//            .authorizeHttpRequests(auth -> auth
//            	.requestMatchers("/user/new").permitAll() // static resources
//                .requestMatchers("/hello").permitAll()          // public
//                .requestMatchers("/admin/**").hasRole("ADMIN")  // admin-only
//                .anyRequest().authenticated()                   // còn lại cần đăng nhập
//            )
//            .formLogin(Customizer.withDefaults()) // bật form-login (Spring sẽ cung cấp login page mặc định). 
//            .httpBasic(Customizer.withDefaults()); // cho curl/basic auth testing
//
//        return http.build();
//    }
    
    // Cấu hình DaoAuthenticationProvider (nếu dùng UserDetailsService)

	@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider prov = new DaoAuthenticationProvider();
        prov.setUserDetailsService(UserDetailService());
        prov.setPasswordEncoder(passwordEncoder());
        return prov;
    }
    
    @Bean
    public UserInfoUserDetailsService UserDetailService() {
		return new UserInfoUserDetailsService();
	}
//    protected void configure(AuthenticationManager auth) throws Exception {
//		auth.userDetailsService(UserDetailService()).passwordEncoder(passwordEncoder());
//	}

    // Expose AuthenticationManager (nếu cần)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    
    private void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
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
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // Public URLs - nên đặt đầu tiên
                .requestMatchers("/login").permitAll()
                
                // API URLs - public cho tất cả methods
//                .requestMatchers("/api/**").permitAll()
//                
//                // Role-based URLs - đặt cụ thể trước, chung sau
//                .requestMatchers("/delete/**").hasRole("ADMIN")
//                .requestMatchers("/edit/**").hasRole( "ADMIN")
//                .requestMatchers("/create/**").hasRole("ADMIN")
//                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // General URLs
                .requestMatchers("/").hasRole("ADMIN")
                
                // Tất cả request còn lại cần authentication
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/") // URL sau khi login thành công
                .failureUrl("/login?error=true") // URL khi login thất bại
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .exceptionHandling(handling -> handling
                .accessDeniedPage("/403")
            );

        return http.build();
    }
    
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
