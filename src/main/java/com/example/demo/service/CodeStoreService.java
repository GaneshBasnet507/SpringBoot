package com.example.demo.service;
import org.springframework.stereotype.Service;

import  java.util.Map;
import java.util.concurrent.*;

@Service
public class CodeStoreService {
    private final Map<String, String> emailCodeMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public void storeCode(String email,String code){
        emailCodeMap.put(email, code);
        scheduler.schedule(() -> emailCodeMap.remove(email),5, TimeUnit.MINUTES);
    }
    public String getCode(String email)
    {
        return emailCodeMap.get(email);
    }
    public void removeCode(String email)
    {
        emailCodeMap.remove(email);
    }
}
