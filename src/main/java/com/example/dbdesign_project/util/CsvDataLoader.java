//package com.example.dbdesign_project.util;
//
//import com.example.dbdesign_project.util.CsvToDatabaseUtil;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class CsvDataLoader implements ApplicationRunner {
//    private final CsvToDatabaseUtil csvToDatabaseUtil;
//
//    public CsvDataLoader(CsvToDatabaseUtil csvToDatabaseUtil) {
//        this.csvToDatabaseUtil = csvToDatabaseUtil;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) {
//        String filePath = "C:\\YHY\\dev\\DBdesign_project\\src\\main\\java\\com\\example\\dbdesign_project\\util\\music.csv"; // CSV 파일 경로
//        int playlistId = 2; // 삽입할 재생목록 ID
//
//        System.out.println("CSV 데이터 삽입 시작...");
//        List<String> errors = csvToDatabaseUtil.insertSongsFromCsv(filePath, playlistId);
//
//        if (errors.isEmpty()) {
//            System.out.println("CSV 데이터 삽입 완료!");
//        } else {
//            System.out.println("일부 데이터 삽입 실패:");
//            errors.forEach(System.out::println);
//        }
//    }
//}
