package com.android.baselibrary.service.http;

import android.os.Build;
import android.util.Log;


import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by gyq
 * <p>
 * on 2016/7/12.
 */
public class LogInterceptor implements Interceptor {

    private String mVersion;

    public  LogInterceptor(String version){
        this.mVersion = version;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        try {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("version", mVersion)
                    .method(original.method(), original.body())
                    .build();

            if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                request.newBuilder().addHeader("Connection", "close");
            }
            okhttp3.Response response = chain.proceed(request);


            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            if (!bodyEncoded(response.headers())) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    return response;
                }

                if (contentLength != 0) {
                    String result = buffer.clone().readString(charset);
                    Log.e("mv","request -> " + request.url());
                    Log.e("mv","request -> " + request.url() + "\nresponse -> " + result);
                    //得到所需的string，开始判断是否异常
                    //***********************do something*****************************
                }
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}

