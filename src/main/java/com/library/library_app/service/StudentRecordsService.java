package com.library.library_app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentRecordsService {

    private Map<String, String> studentRecords = new HashMap<>();

    @PostConstruct
    public void init() {
        loadStudentRecords();
    }

    private void loadStudentRecords() {
        try {
            // resources klasöründen student_records.json dosyasını yükle
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("student_records.json");

            if (inputStream != null) {
                studentRecords = mapper.readValue(inputStream, new TypeReference<Map<String, String>>() {});
                System.out.println(studentRecords.size() + " öğrenci kaydı yüklendi");
            } else {
                System.err.println("student_records.json dosyası bulunamadı");
            }
        } catch (IOException e) {
            System.err.println("Öğrenci kayıtları yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    public boolean isValidStudentNumber(Integer studentNumber) {
        // Map içinde arama yapmak için int değeri String'e dönüştür
        String studentId = String.valueOf(studentNumber);
        return studentRecords.containsKey(studentId);
    }

    public String getStudentName(InputStream studentNumber) {
        String studentId = String.valueOf(studentNumber);
        return studentRecords.get(studentId);
    }
}
