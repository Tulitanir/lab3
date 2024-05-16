package com.example.demo.service;

import com.example.demo.entity.ImportantRecord;
import com.example.demo.repository.ImportantEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class Service {
    private final StringRedisTemplate redisTemplate;
    private final ImportantEntityRepository repo;

    public void run() throws InterruptedException {
        String data = redisTemplate.opsForValue().get("lock");
        if (data == null) {
            int len1 = ((Collection<ImportantRecord>)repo.findAll()).size();
            redisTemplate.opsForValue().set("lock", "lock");
            redisTemplate.expire("lock", 10, TimeUnit.SECONDS);

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

