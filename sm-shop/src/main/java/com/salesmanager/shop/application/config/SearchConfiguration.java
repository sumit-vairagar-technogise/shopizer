package com.salesmanager.shop.application.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.search.SearchService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;

import modules.commons.search.request.Document;
import modules.commons.search.request.SearchRequest;
import modules.commons.search.request.SearchResponse;

/**
 * Provides a no-op search service when Elasticsearch is disabled
 */
@Configuration
public class SearchConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(name = "elasticsearch.enabled", havingValue = "false", matchIfMissing = true)
    public SearchService noOpSearchService() {
        return new SearchService() {
            @Override
            public void index(MerchantStore store, Product product) throws ServiceException {
                // No-op
            }

            @Override
            public void deleteDocument(MerchantStore store, Product product) throws ServiceException {
                // No-op
            }

            @Override
            public SearchResponse searchKeywords(MerchantStore store, String language, 
                    SearchRequest search, int entriesCount) throws ServiceException {
                return new SearchResponse();
            }

            @Override
            public SearchResponse search(MerchantStore store, String language, 
                    SearchRequest search, int entriesCount, int startIndex) throws ServiceException {
                return new SearchResponse();
            }

            @Override
            public Optional<Document> getDocument(String language, MerchantStore store, Long id) throws ServiceException {
                return Optional.empty();
            }
        };
    }
}
