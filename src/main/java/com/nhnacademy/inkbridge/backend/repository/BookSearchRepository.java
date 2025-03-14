package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Search;
import com.nhnacademy.inkbridge.backend.repository.custom.BookSearchRepositoryCustom;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * class: BookSearchRepository.
 *
 * @author choijaehun
 * @version 2024/03/10
 */
@ConditionalOnProperty(name="elasticsearch.enabled", havingValue = "true", matchIfMissing = false)
public interface BookSearchRepository extends ElasticsearchRepository<Search, Long>,
    BookSearchRepositoryCustom {

}
