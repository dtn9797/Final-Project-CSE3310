package com.example.duynguyen.sample.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MessageChannel implements Parcelable {
    private String parentName;
    private String studentName;
    private String teacherName;
    private String id;


    public MessageChannel(String parentName, String studentName, String teacherName,String id) {
        this.parentName = parentName;
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MessageChannel() {
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentName);
        dest.writeString(this.studentName);
        dest.writeString(this.teacherName);
        dest.writeString(this.id);
    }

    protected MessageChannel(Parcel in) {
        this.parentName = in.readString();
        this.studentName = in.readString();
        this.teacherName = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<MessageChannel> CREATOR = new Parcelable.Creator<MessageChannel>() {
        @Override
        public MessageChannel createFromParcel(Parcel source) {
            return new MessageChannel(source);
        }

        @Override
        public MessageChannel[] newArray(int size) {
            return new MessageChannel[size];
        }
    };
}
