package com.example.demo.repository;

import com.example.demo.entity.ImportantEntity;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJdbcRepositories
public interface ImportantEntityRepository extends CrudRepository<ImportantEntity, Long> {
}
