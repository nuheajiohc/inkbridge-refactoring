package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.entity.Search;
import com.nhnacademy.inkbridge.backend.repository.custom.BookSearchRepositoryCustom;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * class: BookSearchRepositoryImpl.
 *
 * @author choijaehun
 * @version 2024/03/11
 */


@Repository
@RequiredArgsConstructor
public class BookSearchRepositoryImpl implements BookSearchRepositoryCustom {

    private final ElasticsearchOperations elasticsearchOperations;

    /**
     *{@inheritDoc}
     */
    @Override
    public Page<Search> searchByText(String text, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
            .should(QueryBuilders.matchQuery("book_title", text).boost(100.0f))
            .should(QueryBuilders.matchQuery("book_title.nori", text).boost(80.0f))
            .should(QueryBuilders.matchQuery("book_title.ngram", text).boost(80.0f))
            .should(QueryBuilders.matchQuery("description", text).boost(50.0f))
            .should(QueryBuilders.matchQuery("description.nori", text).boost(5.0f))
            .should(QueryBuilders.matchQuery("description.ngram", text).boost(5.0f))
            .should(QueryBuilders.matchQuery("publisher_name", text).boost(90.0f))
            .should(QueryBuilders.nestedQuery("tags",
                QueryBuilders.queryStringQuery(text).field("tags.tag_name"),
                ScoreMode.None).boost(90.0f))
            .should(QueryBuilders.nestedQuery("authors",
                QueryBuilders.queryStringQuery(text).field("authors.author_name"), ScoreMode.None).boost(90.f));

        Query searchQuery = new NativeSearchQueryBuilder()
            .withQuery(boolQueryBuilder)
            .withPageable(pageable)
            .build();

        SearchHits<Search> searchHits = elasticsearchOperations.search(searchQuery,
            Search.class);
        List<Search> books = searchHits.get().map(SearchHit::getContent)
            .collect(Collectors.toList());
        return new PageImpl<>(books, pageable, searchHits.getTotalHits());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Page<Search> searchByAll(Pageable pageable) {
        Query searchQuery = new NativeSearchQueryBuilder()
            .withPageable(pageable)
            .build();

        SearchHits<Search> searchHits = elasticsearchOperations.search(searchQuery, Search.class);
        List<Search> books = searchHits.get().map(SearchHit::getContent)
            .collect(Collectors.toList());
        return new PageImpl<>(books, pageable, searchHits.getTotalHits());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Page<Search> searchByCategory(String category, Pageable pageable) {
        QueryBuilder queryBuilder = QueryBuilders.nestedQuery("categories",
            QueryBuilders.queryStringQuery(category).field("categories.category_name"),
            ScoreMode.None);

        Query searchQuery = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();

        SearchHits<Search> searchHits = elasticsearchOperations.search(searchQuery,
            Search.class);
        List<Search> books = searchHits.get().map(SearchHit::getContent)
            .collect(Collectors.toList());
        return new PageImpl<>(books, pageable, searchHits.getTotalHits());
    }
}
