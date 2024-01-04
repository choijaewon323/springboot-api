package com.project.crud.board.service;

import com.project.crud.board.domain.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @AfterEach
    void afterEach() throws Exception {
        boardRepository.deleteAll();
    }
}
