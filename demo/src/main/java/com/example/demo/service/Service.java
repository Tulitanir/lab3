package com.example.demo.service;

import com.example.demo.entity.ImportantRecord;
import com.example.demo.repository.ImportantEntityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Service {
    private final StringRedisTemplate redisTemplate;
    private final ImportantEntityRepository repo;
    private final String LOCK_KEY = "lock";
    private final long TIMEOUT = 10;

    public Service(StringRedisTemplate redisTemplate, ImportantEntityRepository repo) {
        this.redisTemplate = redisTemplate;
        this.repo = repo;
    }

    public void run() throws InterruptedException {
        Boolean isAcquired = redisTemplate.opsForValue().setIfAbsent("lock", "locked", TIMEOUT, TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(isAcquired)) {
            int len1 = ((Collection<ImportantRecord>)repo.findAll()).size();

            ImportantRecord entity = new ImportantRecord();
            entity.setDate(LocalDateTime.now());
            repo.save(entity);

            int len2 = ((Collection<ImportantRecord>)repo.findAll()).size();
            System.out.println("Data was successfully inserted. Before: " + len1 + ", after: " + len2);
        } else {
            System.out.println("Lock is already active");
        }
    }
}

