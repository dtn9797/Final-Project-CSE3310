package com.example.duynguyen.sample.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerator {

    //Create QR code image
   // https://chart.googleapis.com/chart?chl=Hello123&chs=200x200&cht=qr&chld=H%7C0
    //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
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
