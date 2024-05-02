package com.example.demo.service;

import com.example.demo.repository.ImportantEntityRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
@AllArgsConstructor
public class Service {
    private final RedisTemplate<String, Date> redisTemplate;
    private final String homeDirectory;

    public void run() {
        Date date = redisTemplate.opsForValue().get("lock");
        if (date == null) {
            Path path = Paths.get(homeDirectory, "logs.txt");
            try {
                var res = Files.readAllLines(path);
                System.out.println("File lines before:");
                System.out.println(res);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            date = new Date(System.currentTimeMillis());
            redisTemplate.opsForValue().set("lock", date);

            try(FileWriter writer = new FileWriter(path.toFile(), true))
            {
                String text = date.toString();
                writer.write(text);
            }
            catch(IOException ex){
                throw new RuntimeException(ex);
            }

            try {
                var res = Files.readAllLines(path);
                System.out.println("File lines after:");
                System.out.println(res);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Lock is already active");
        }
    }
}
