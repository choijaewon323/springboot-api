package com.project.crud;

import com.project.crud.account.domain.Account;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.Stream;

import static io.swagger.v3.oas.models.PathItem.HttpMethod.*;

@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CrudApplication.class, args);

		BoardRepository boardRepository = context.getBean(BoardRepository.class);
		AccountRepository accountRepository = context.getBean(AccountRepository.class);

		accountRepository.save(Account.makeAdmin("admin", "1234"));

		Stream.iterate(1, i -> i + 1)
				.map(i -> Board.of("title" + i, "content" + i, "writer" + i))
				.limit(50)
				.forEach(boardRepository::save);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods(
								DELETE.name(),
								GET.name(),
								PUT.name(),
								POST.name(),
								HEAD.name()
						);
			}
		};
	}

}
