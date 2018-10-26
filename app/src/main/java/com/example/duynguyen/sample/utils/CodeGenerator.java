package com.example.duynguyen.sample.utils;

import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerator {


    public static String generateClassId (){
        String startingId  = "myStudent";
        int randomNum = ThreadLocalRandom.current().nextInt(0, 99999 + 1);
        String endingId = String.valueOf(randomNum);
        return startingId.concat(endingId);
    }

    public static String studentId (String classId, int nextStudentPos){
        String startingId  = classId;
        String endingId = String.valueOf(nextStudentPos);
        return startingId.concat(endingId);
    }



}
