package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagDeleteResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.enums.TagMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.TagService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: TagRestController.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */

@RestController
@RequestMapping(value = "/api/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagService tagService;

    /**
     * 새로운 태그를 생성합니다. 유효하지 않은 태그 정보가 전달되면 {@link ValidationException}을 발생시키며,
     * 이미 존재하는 태그 이름으로 생성을 시도할 경우 AlreadyExistException 을 발생시킵니다.
     *
     * @param newTag        생성할 태그의 정보를 담고 있는 {@link TagCreateRequestDto} 객체.
     * @param bindingResult 바인딩 결과를 담고 있는 {@link BindingResult} 객체. 유효성 검증에 실패한 경우,
     *                      상세한 오류 정보를 포함합니다.
     * @return 생성된 태그의 정보를 담고 있는 {@link TagCreateResponseDto} 객체와 함께
     *         HTTP 상태 코드 201 을 반환합니다. 유효성 검증에 실패한 경우 HTTP 상태 코드 422 (Unprocessable Entity),
     *         이미 존재하는 태그 이름으로 생성을 시도한 경우 HTTP 상태 코드 409 (Conflict)를 반환합니다.
     * @throws ValidationException 태그 정보가 유효하지 않을 경우 발생합니다. 이 예외는 유효성 검증 실패 시에 발생하며,
     *                             HTTP 상태 코드 422와 함께 처리됩니다.
     */
    @PostMapping
    public ResponseEntity<TagCreateResponseDto> createTag(
        @RequestBody @Valid TagCreateRequestDto newTag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(TagMessageEnum.TAG_TYPE_VALID_FAIL.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(newTag));
    }

    /**
     * 저장된 모든 태그의 리스트를 조회합니다.
     *
     * @return 태그 리스트를 담고 있는 {@link TagReadResponseDto} 객체의 리스트와 함께 HTTP 상태 코드 200을 반환합니다.
     */
    @GetMapping
    public ResponseEntity<List<TagReadResponseDto>> getTagList() {
        return ResponseEntity.ok(tagService.getTagList());
    }

    /**
     * 지정된 ID를 가진 태그를 업데이트합니다. 태그 ID와 업데이트할 태그 정보를 요청 본문으로 전달받아 처리합니다.
     * 태그 ID에 해당하는 태그가 존재하지 않거나, 업데이트할 정보가 유효하지 않을 경우, 적절한 예외가 발생하며, 각각 다른 HTTP 상태 코드를 반환합니다.
     *
     * @param tagId         업데이트할 태그의 ID. 해당 ID를 가진 태그가 존재하지 않을 경우 NotFoundException 이 발생합니다.
     * @param newTag        업데이트할 태그의 정보를 담고 있는 {@link TagUpdateRequestDto} 객체. 요청 본문으로부터 받은 태그 정보가
     *                      유효하지 않을 경우, {@link ValidationException}을 발생시킵니다.
     * @param bindingResult 바인딩 결과를 담고 있는 {@link BindingResult} 객체. 유효성 검증에 실패한 경우,
     *                      상세한 오류 정보를 포함합니다.
     * @return 업데이트된 태그의 정보를 담고 있는 {@link TagUpdateResponseDto} 객체와 함께 HTTP 상태 코드 200 (OK)을 반환합니다.
     *         지정된 ID를 가진 태그가 존재하지 않을 경우, HTTP 상태 코드 404 (Not Found)를 반환합니다.
     *         업데이트할 정보가 유효하지 않을 경우, HTTP 상태 코드 422 (Unprocessable Entity)를 반환합니다.
     * @throws ValidationException 태그 정보가 유효하지 않을 경우 발생합니다. 이 예외는 유효성 검증에 실패한 경우 발생하며,
     *                             HTTP 상태 코드 422 (Unprocessable Entity)와 함께 처리됩니다.
     */
    @PutMapping("/{tagId}")
    public ResponseEntity<TagUpdateResponseDto> modifyTag(@PathVariable(name = "tagId") Long tagId,
        @RequestBody @Valid TagUpdateRequestDto newTag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(TagMessageEnum.TAG_TYPE_VALID_FAIL.getMessage());
        }
        return ResponseEntity.ok(tagService.updateTag(tagId, newTag));
    }

    /**
     * 지정된 ID를 가진 태그를 삭제합니다. 지정된 ID를 가진 태그가 존재하지 않을 경우, NotFoundException 을 발생시킵니다.
     *
     * @param tagId 삭제할 태그의 ID. 해당 ID를 가진 태그가 존재하지 않을 경우 NotFoundException 이 발생하며,
     *              이는 HTTP 상태 코드 404 (Not Found)와 함께 처리됩니다.
     * @return 삭제 성공 메시지를 담고 있는 {@link TagDeleteResponseDto} 객체와 함께 HTTP 상태 코드 200 (OK)을 반환합니다.
     *         지정된 ID를 가진 태그가 존재하지 않을 경우, HTTP 상태 코드 404 (Not Found)를 반환합니다.
     */
    @DeleteMapping("/{tagId}")
    public ResponseEntity<TagDeleteResponseDto> deleteTag(
        @PathVariable(name = "tagId") Long tagId) {
        return ResponseEntity.ok(tagService.deleteTag(tagId));
    }

}
