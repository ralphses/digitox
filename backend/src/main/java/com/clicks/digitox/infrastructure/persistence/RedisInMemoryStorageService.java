package com.clicks.digitox.infrastructure.persistence;

import com.clicks.digitox.domain.user.repository.UserInMemoryRepository;
import com.clicks.digitox.shared.persistence.GenericInMemoryStorageService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisInMemoryStorageService implements
        GenericInMemoryStorageService,
        UserInMemoryRepository {

//    private final RedisTemplate<String, Object> redisTemplate;
    private static final String USER_ID_KEY = "userId";




    @Override
    public void addUserId(String email) {

    }

    @Override
    public boolean userIdExists(String email) {
        return false;
    }

    @Override
    public boolean removeUserId(String email) {
        return false;
    }

    @Override
    public void storeTemporaryData(String key, Object value, long timeout, TimeUnit timeUnit) {
    }

    @Override
    public boolean exists(String key) {
       return false;
    }

    @Override
    public Object retrieveData(String key) {
        return null;
    }

    @Override
    public void deleteData(String key) {
    }
}
