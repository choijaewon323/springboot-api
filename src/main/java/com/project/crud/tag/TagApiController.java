package com.project.crud.tag;

import com.project.crud.tag.dto.TagCreateDto;
import com.project.crud.tag.dto.TagReadByBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        checkId(boardId, "boardId");

        return tagService.readByBoardId(boardId);
    }

    @PutMapping("/{tagId}")
    public void update(@PathVariable Long tagId, String name) {
        checkId(tagId, "tagId");
        checkTagName(name);

        tagService.update(tagId, name);
    }

    private void checkId(Long id, String idName) {
        if (id == null) {
            throw new IllegalArgumentException("tag: " + idName + "가 null입니다");
        }

        if (id < 0) {
            throw new IllegalArgumentException("tag: " + idName + "는 음수일 수 없습니다");
        }
    }

    private void checkTagName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("tag: 태그명이 없습니다");
        }
    }
}
