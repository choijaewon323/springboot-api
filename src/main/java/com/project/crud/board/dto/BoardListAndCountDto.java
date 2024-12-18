package com.project.crud.board.dto;

import java.util.List;

public record BoardListAndCountDto(long count,
                                   int pageSize,
                                   List<BoardListDto> boardList) {
}
