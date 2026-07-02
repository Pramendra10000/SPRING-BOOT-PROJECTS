package com.parammart.serviceTest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;

@Service
public class FirebaseTestService {

    @Autowired
    private Firestore firestore;

    public String test() {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "ParamMart Firebase Connected");
            data.put("status", "SUCCESS");

            firestore.collection("test")
                    .document("demo")
                    .set(data);

            return "Data written successfully to Firestore";

        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    
}