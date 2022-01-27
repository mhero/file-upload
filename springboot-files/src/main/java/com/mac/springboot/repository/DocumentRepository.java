package com.mac.springboot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mac.springboot.domain.Document;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

}
