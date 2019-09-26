package com.example.littlevideo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public final class PhotoFetcher {
    private PhotoFetcher() {
    }

    public static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public static String getUrlString(String urlSpec, String charsetName) throws IOException {
        return new String(getUrlBytes(urlSpec), charsetName);
    }




    public static Bitmap getUrlBitMap(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            return BitmapFactory.decodeStream(in);
        }catch (Exception e){
            System.out.println("下载图片出错！");
            e.printStackTrace();
           return null;
        } finally{
            connection.disconnect();
        }
    }





    //
    public static PhotoResult getAPIOpenPhotoItems(String urlSpec, String charsetName) {
        PhotoResult photoResult = new PhotoResult();
        try {
            String result = getUrlString(urlSpec, charsetName);
            JSONObject jsonObject = new JSONObject(result);
            String message =jsonObject.getString("message");
            if(!message.equals("成功!"))
                return photoResult;
            JSONArray jsonDatas = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonDatas.length(); i++) {
                jsonObject = jsonDatas.getJSONObject(i);
                PhotoItem photoItem = new PhotoItem();
                photoItem.setUrl(jsonObject.getString("thumbnail"));
                photoItem.setVideoName(jsonObject.getString("text"));
                photoItem.setVideoURL(jsonObject.getString("video"));

                if(photoItem.getUrl()!=null && photoItem.getVideoURL()!=null && photoItem.getVideoName()!=null
                && !photoItem.getUrl().equals("null") && !photoItem.getVideoURL().equals("null") && !photoItem.getVideoName().equals("null")){
                    System.out.println(photoItem);
                    photoResult.getResults().add(photoItem);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return photoResult;
        }
        return photoResult;
    }
}
