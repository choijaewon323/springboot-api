package com.project.crud.tag;

import com.project.crud.tag.dto.TagCreateDto;
import com.project.crud.tag.dto.TagReadByBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tag")
public class TagApiController {
    private final TagService tagService;

    @PostMapping
    public void create(@RequestBody TagCreateDto dto) {
        tagService.create(dto);
    }

    @GetMapping("/{boardId}")
    public List<TagReadByBoardDto> readByBoard(@PathVariable Long boardId) {
        Objects.requireNonNull(boardId, "tag: boardId가 null입니다");

        return tagService.readByBoardId(boardId);
    }

    @PutMapping("/{tagId}")
    public void update(@PathVariable Long tagId, String name) {
        Objects.requireNonNull(tagId, "tag: id가 null입니다");
        checkTagName(name);

        tagService.update(tagId, name);
    }

    private void checkTagName(String name) {
        Objects.requireNonNull(name, "tag: 태그명이 없습니다");

        if (name.isBlank()) {
            throw new IllegalArgumentException("tag: 태그명이 없습니다");
        }
    }
}
