package com.android.baselibrary.service.http;


import android.util.Log;

import com.android.baselibrary.service.AES256;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.BufferedSource;
import retrofit2.Converter;

public class JsonResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {

        try {
            String response = value.string();
            JSONObject jsonObject = null;
            String dataObj = null;
            boolean isHasData = false;
            try {
                jsonObject = new JSONObject(response);
                Iterator<?> iterator = jsonObject.keys();// 应用迭代器Iterator 获取所有的key值
                while (iterator.hasNext()) { // 遍历每个key
                    String key = (String) iterator.next();
                    if (key.equals("data")) {
                        isHasData = true;
                        dataObj = jsonObject.getString("data")+"";
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonReader jsonReader = null;
            MediaType mediaType = value.contentType();
            Charset charset = mediaType != null ? mediaType.charset(Util.UTF_8) : Util.UTF_8;
//            if (isHasData) {
//                Object rsaData2 = AES256.decrypt(dataObj);
//                try {
//                    Object json = new JSONTokener((String) rsaData2).nextValue();
//                    if (json instanceof Map) {
//                        JSONObject jsonrsaData = new JSONObject((Map) json);
//                        jsonObject.put("data",jsonrsaData);
//                        InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
//                        jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
//                        return adapter.read(jsonReader);
//                    } else if (json instanceof JSONArray) {
//                        JSONArray jsonrsaData = (JSONArray)json;
//                        jsonObject.put("data",jsonrsaData);
//                        InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
//                        jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
//                        return adapter.read(jsonReader);
//                    } else if (json instanceof JSONObject) {
//                        JSONObject jsonrsaData =  (JSONObject)json;
//                        jsonObject.put("data",jsonrsaData);
//                        InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
//                        jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
//                        return adapter.read(jsonReader);
//                    }
//                    InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
//                    jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
//                    return adapter.read(jsonReader);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return adapter.read(jsonReader);
//                InputStream inputStream = new ByteArrayInputStream(response.getBytes());
//                jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
//                return adapter.read(jsonReader);
//            } else {
//                InputStream inputStream = new ByteArrayInputStream(response.getBytes());
//                jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
//                return adapter.read(jsonReader);
//            }
            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
            jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

//    private T jsonValues(ResponseBody value) throws IOException {
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            T result = adapter.read(jsonReader);
//            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
//                throw new JsonIOException("JSON document was not fully consumed.");
//            }
//            return result;
//        } finally {
//            value.close();
//        }
//    }


}
