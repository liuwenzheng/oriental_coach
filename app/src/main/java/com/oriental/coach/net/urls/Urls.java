package com.oriental.coach.net.urls;

/**
 * Created by wenzheng.liu on 2016/11/28.
 * 网络请求接口
 */

public class Urls {
    public static final String SERVER = "http://china-wx.wicp.net/drivers-app-main/";
    public static final String SERVER_IMAGE = "http://644905071qq.oicp.net/drivers-school-main";
    public static final String LOGIN = SERVER + "user/app/dologin.do";
    public static final String GET_TEACHER = SERVER + "driversApp/driversTeacher/getTeacher.do";
    public static final String GET_TEACHER_CAR = SERVER + "driversApp/driversCar/initTeacherCar.do";
    public static final String GET_BESPEAK = SERVER + "driversApp/driversStudentBespeak/initBespeakList.do";
    public static final String GET_STUDENT = SERVER + "driversApp/driversTeacher/selectSudentList.do";
    public static final String GET_APPOINTMENT = SERVER + "driversApp/driversTeacherTime/selectOneDayTime.do";
    public static final String GET_MESSAGE = SERVER + "driversApp/driversMessage/initList.do";
    public static final String DELETE_MESSAGE = SERVER + "driversApp/driversMessage/deleteMessage.do";
    public static final String GET_CARRECORD = SERVER + "driversApp/driversCar/initCarRecordList.do";
}
