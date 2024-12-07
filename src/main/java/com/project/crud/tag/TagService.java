package com.project.crud.tag;

import com.project.crud.tag.dto.TagCreateDto;
import com.project.crud.tag.dto.TagReadByBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public void create(TagCreateDto tagCreateDto) {
        tagRepository.save(Tag.of(
                tagCreateDto.name(),
                tagCreateDto.boardId()
        ));
    }

    @Transactional(readOnly = true)
    public List<TagReadByBoardDto> readByBoardId(Long boardId) {
        Objects.requireNonNull(boardId, "tag: boardId가 null입니다");

        return tagRepository.findAllByBoardId(boardId)
                .stream()
                .map(TagReadByBoardDto::of)
                .toList();
    }

    public void update(Long tagId, String after) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("tag: 해당 id의 태그가 없습니다"));

        tag.updateName(after);
    }
}
