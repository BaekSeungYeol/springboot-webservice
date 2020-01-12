package com.whitewin.service.posts;

import com.whitewin.domain.posts.Posts;
import com.whitewin.domain.posts.PostsRepository;
import com.whitewin.web.dto.PostsListResponseDto;
import com.whitewin.web.dto.PostsResponseDto;
import com.whitewin.web.dto.PostsSaveRequestDto;
import com.whitewin.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("해당 사용자가 없습니다. id=" + id);
                });

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("해당 사용자가 없습니다. id=" + id);
                });
        return new PostsResponseDto(entity);
    }
}

