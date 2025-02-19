package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.Search;
import com.nhnacademy.inkbridge.backend.repository.BookSearchRepository;
import com.nhnacademy.inkbridge.backend.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * class: BookSearchServiceImpl.
 * @author choijaehun
 * @version 2024/03/11
 */

@Service
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {

    private final BookSearchRepository bookSearchRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Search> searchByText(String text, Pageable pageable) {
        return bookSearchRepository.searchByText(text,pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Search> searchByAll(Pageable pageable) {
        return bookSearchRepository.searchByAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Search> searchByCategory(String category, Pageable pageable) {
        return bookSearchRepository.searchByCategory(category, pageable);
    }
}
