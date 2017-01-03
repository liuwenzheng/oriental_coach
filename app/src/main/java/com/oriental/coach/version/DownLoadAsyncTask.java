package com.oriental.coach.version;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 异步下载文件
 */
public class DownLoadAsyncTask extends AsyncTask<String, Integer, byte[]> {

    private ProgressDialog progressDialog;//进度条
    private boolean flag; //是否被取消
    private Handler handler;


    public DownLoadAsyncTask(Context context, Handler handler, ProgressDialog progressDialog) {
        super();
        this.handler = handler;
        this.progressDialog = progressDialog;
    }

    /**
     * 任务执行之前回调
     */
    @Override
    protected void onPreExecute() {
//    	progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("正在操作中，请稍候……");
//        progressDialog.setMax(100);//进度条最大值
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//水平样式
//        progressDialog.setIndeterminate(false);//进度条的动画效果（有动画则无进度值）

        flag = true;
        //退出对话框事件
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                flag = false;
            }
        });
        progressDialog.show();
    }

    @Override
    protected byte[] doInBackground(String... params) {
        int count;//读取当前批次的字节数
        try {
            //创建URL对象，用于访问网络资源
            URL url = new URL(params[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            //获得总长度
            int lenghtOfFile = conexion.getContentLength();
            //开始读取数据
            InputStream input = new BufferedInputStream(url.openStream());
            //写入数据对象并指定写入路径文件
            OutputStream output = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "DrivingTeacher.apk"));
            //每次读取字节数
            byte data[] = new byte[1024];
            int total = 0;//每次读取的数据
            while ((count = input.read(data)) != -1) {
                //指针向后移
                total += count;
                //计算进度
                int progress = (int) ((double) total / lenghtOfFile * 100);//先计算出百分比在转换成整型
                //更新进度
                publishProgress(progress, total, lenghtOfFile);
                //写入文件
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
        return null;
    }

    /**
     * 更新进度条回调
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setMessage("下载中，请稍后！");
        progressDialog.setProgress(values[0]);//进度动态提示

        //如果是自定义可以使用以下方式：handler通知UI更新进度
//        progressDialog.setMessage(
//                String.format("已读%d M, 共%d M",//字节为单位
//                        values[1] / 1024 / 1024, values[2] / 1024 / 1024));//将values[1]赋给第一个%d,第二个同理
//        
//        Message msg = new Message();
//        msg.what=11;
//        msg.obj=values[0];
//        handler.sendMessage(msg);
    }

    /**
     * 任务被取消时回调
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
//        Toast.makeText(context,"任务已取消",Toast.LENGTH_SHORT).show();
    }

    /**
     * 任务结束后回调
     */
    @Override
    protected void onPostExecute(byte[] result) {
        progressDialog.dismiss();//关闭对话框
        //通知程序安装新版本
        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

}
