package com.example.duynguyen.sample.model;

public class CloudImage {
    String url;
    String name;
    Boolean enable;
    int pts;

    public CloudImage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public CloudImage(int i) {
        switch (i){
            case 1:
                generateReward1();
                break;
            case 2:
                generateReward2();
                break;
            case 3:
                generateReward3();
                break;
            case 4:
                generateReward4();
                break;
            case 5:
                generateReward5();
                break;

            case 6:
                generateReward6();
                break;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
    public void generateReward1(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F1.png?alt=media&token=9a55a700-147c-4f7f-a756-20a890e54037";
        enable = true;
        pts = 20;
    }

    public void generateReward2(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F2.png?alt=media&token=effa773f-de27-4b4d-90eb-d7c97ba60669";
        enable = true;
        pts = 30;
    }

    public void generateReward3(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F3.png?alt=media&token=b27e47f8-f521-4769-8a17-a6c5df242570";
        enable = true;
        pts = 40;
    }

    public void generateReward4(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F4.png?alt=media&token=b8499a5d-b7f9-4f6f-843f-33b3f1fc0191";
        enable = true;
        pts = 50;
    }

    public void generateReward5(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F5.png?alt=media&token=9845bb5c-f78e-4e29-a5a1-130dd7c9a307";
        enable = true;
        pts = 60;
    }

    public void generateReward6(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F6.png?alt=media&token=e6a1097d-e656-459b-b9bc-a960f8f74fd1";
        enable = true;
        pts = 70;
    }

    public void generateReward7(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F7.png?alt=media&token=78e6f945-6c0d-4a97-8b5b-c1ec480fb924";
        enable = true;
        pts = 80;
    }

    public void generateReward8(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F8.png?alt=media&token=09bb8321-c020-4568-852a-27a07601cd59";
        enable = true;
        pts = 90;
    }

    public void generateReward9(){
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F9.png?alt=media&token=b441e569-9f7f-49ea-bdf4-97a1160dc433";
        enable = true;
        pts = 100;
    }


    public void generateDefaultProfile() {
        url = "https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F9.png?alt=media&token=b441e569-9f7f-49ea-bdf4-97a1160dc433";
        enable = true;
        pts = 0;
    }

}
