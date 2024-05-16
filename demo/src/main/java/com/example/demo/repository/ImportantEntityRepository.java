package com.example.demo.repository;

import com.example.demo.entity.ImportantRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportantEntityRepository extends CrudRepository<ImportantRecord, Long> {
}
