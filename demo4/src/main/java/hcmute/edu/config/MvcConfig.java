package hcmute.edu.config;

public class MvcConfig implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

	@Override
	public void addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/403").setViewName("403");
	}
	
}
