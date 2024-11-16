package com.project.crud;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.stream.Stream;

@EnableJpaAuditing
@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CrudApplication.class, args);

		BoardRepository boardRepository = context.getBean(BoardRepository.class);

		Stream.iterate(1, i -> i + 1)
				.map(i -> Board.builder()
						.title("title" + i)
						.content("content" + i)
						.writer("writer" + i)
						.build())
				.limit(50)
				.forEach(boardRepository::save);
	}

}
