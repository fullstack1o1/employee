package net.samitkumar.hrmanagement;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class HrManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrManagementApplication.class, args);
	}

	@Bean
	CorsWebFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		//config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}


}

@Controller
class WebController {
	@GetMapping("/")
	public Mono<String> index() {
		return Mono.just("index");
	}

	@GetMapping("/album")
	public Mono<String> album() {
		return Mono.just("album");
	}
	@GetMapping("/mnc")
	public Mono<String> empManagement() {
		return Mono.just("mnc");
	}
}

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {
	@Bean
	@SneakyThrows
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
				.authorizeExchange(exchange -> exchange
						.pathMatchers("/actuator/**").permitAll()
						.anyExchange().authenticated())
				.cors(Customizer.withDefaults())
				.csrf(csrfSpec -> csrfSpec/*.disable()*/
						.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
						.requireCsrfProtectionMatcher(new PathPatternParserServerWebExchangeMatcher("^(?!\\/db\\/).*", HttpMethod.POST)))
				.formLogin(Customizer.withDefaults())
		;
		return http.build();
	}
}