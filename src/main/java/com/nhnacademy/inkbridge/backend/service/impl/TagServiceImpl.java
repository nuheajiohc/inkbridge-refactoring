package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagDeleteResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.enums.TagMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookTagRepository;
import com.nhnacademy.inkbridge.backend.repository.TagRepository;
import com.nhnacademy.inkbridge.backend.service.TagService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: TagServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final BookTagRepository bookTagRepository;

    /**
     * {@inheritDoc}
     * 새로운 태그를 생성합니다. 태그의 이름이 이미 존재할 경우 {@link AlreadyExistException}을 발생시킵니다.
     *
     * @param newTag 생성할 태그의 정보를 담고 있는 {@link TagCreateRequestDto} 객체
     * @return 생성된 태그의 정보를 담은 {@link TagCreateResponseDto}
     * @throws AlreadyExistException 태그의 이름이 이미 존재할 경우 발생
     */
    @Override
    @Transactional
    public TagCreateResponseDto createTag(TagCreateRequestDto newTag) {
        if (tagRepository.existsByTagName(newTag.getTagName())) {
            throw new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.getMessage());
        }
        Tag tag = Tag.builder().tagName(newTag.getTagName()).build();
        Tag savedTag = tagRepository.save(tag);
        return TagCreateResponseDto.builder().tag(savedTag).build();
    }

    /**
     * 저장된 모든 태그의 리스트를 조회합니다.
     *
     * @return 조회된 태그 리스트를 담고 있는 {@link TagReadResponseDto} 객체의 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<TagReadResponseDto> getTagList() {
        return tagRepository.findAll().stream().map(TagReadResponseDto::new)
            .collect(Collectors.toList());
    }

    /**
     * 지정된 ID를 가진 태그를 업데이트합니다. 태그 ID에 해당하는 태그가 존재하지 않거나, 업데이트할 정보의 태그 이름이 이미 존재할 경우 예외를 발생시킵니다.
     *
     * @param tagId  업데이트할 태그의 ID
     * @param newTag 업데이트할 태그의 정보를 담고 있는 {@link TagUpdateRequestDto} 객체
     * @return 업데이트된 태그의 정보를 담은 {@link TagUpdateResponseDto}
     * @throws NotFoundException     지정된 ID를 가진 태그가 존재하지 않을 경우 발생
     * @throws AlreadyExistException 업데이트할 정보의 태그 이름이 이미 존재할 경우 발생
     */
    @Override
    @Transactional
    public TagUpdateResponseDto updateTag(Long tagId, TagUpdateRequestDto newTag) {
        if (!tagRepository.existsById(tagId)) {
            throw new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.getMessage());
        }
        if (Boolean.TRUE.equals(tagRepository.existsByTagName(newTag.getTagName()))) {
            throw new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.getMessage());
        }
        Tag tag = Tag.builder().tagId(tagId).tagName(newTag.getTagName()).build();
        return TagUpdateResponseDto.builder().tag(tagRepository.save(tag)).build();
    }

    /**
     * 지정된 ID를 가진 태그를 삭제합니다. 태그 ID에 해당하는 태그가 존재하지 않을 경우 {@link NotFoundException}을 발생시킵니다.
     *
     * @param tagId 삭제할 태그의 ID
     * @return 삭제 성공 메시지를 담은 {@link TagDeleteResponseDto}
     * @throws NotFoundException 지정된 ID를 가진 태그가 존재하지 않을 경우 발생
     */
    @Override
    @Transactional
    public TagDeleteResponseDto deleteTag(Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.getMessage());
        }
        bookTagRepository.deleteAllByPk_TagId(tagId);
        tagRepository.deleteById(tagId);
        return TagDeleteResponseDto.builder().message(tagId + " is deleted").build();
    }
}
