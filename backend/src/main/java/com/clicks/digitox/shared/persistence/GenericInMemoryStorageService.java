package com.clicks.digitox.shared.persistence;

import java.util.concurrent.TimeUnit;

public interface GenericInMemoryStorageService {

    void storeTemporaryData(String key, Object value, long timeout, TimeUnit timeUnit);

    boolean exists(String key);

    Object retrieveData(String key);

    void deleteData(String key);
}
