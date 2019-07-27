package com.android.baselibrary.picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author jiay
 *         <p/>
 *         图片压缩共通类
 */
public class AppCompressImageUtil {

    private static final String TAG = "compressimage";// 压缩图片为最高100k
    private static final int COMPRESS_SIZE = 200;

    // SDCard路径
    public static final String SD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    public static String ALBUM_PATH = SD_PATH + "/" + "bitmap" + "/";

    /**
     * 压缩图片
     *
     * @param image    原图
     * @param fileSize 压缩的图片大小限定
     * @return 压缩后图片流
     */
    public static byte[] compressImage(Bitmap image, int fileSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100; // 此处可尝试用90%开始压缩，跳过100%压缩
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        int length = baos.toByteArray().length;
        Log.i(TAG, options + "--" + length);
        while ((length = baos.toByteArray().length) / 1024 > fileSize) {
            // 每次都减少10
            options -= 10;
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            Log.i(TAG, options + "--" + length);
        }
        return baos.toByteArray();
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        // ByteArrayInputStream isBm = new
        // ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        // Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        // return bitmap;
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath
     * @return
     */
    public static byte[] getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        // 另外一种算压缩比的方法：
        // 图片比例压缩倍数 就是 （宽度压缩倍数+高度压缩倍数）/2
        // be = (int) ((w / STANDARD_WIDTH + h/ STANDARD_HEIGHT) / 2);
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, 100);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 图片按比例大小压缩方法（根据Bitmap图片压缩）
     *
     * @param image
     * @return
     */
    public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        byte[] b = compressImage(bitmap, 100);// 压缩好比例大小后再进行质量压缩
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        return BitmapFactory.decodeStream(in, null, null);
    }


    /**
     * 批量压缩并生成图片，返回压缩生成的图片路径
     *
     * @param imgPath 图片路径
     * @return CImgPath 压缩后生成的图片路径集合
     */
    public static List<File> batchCompressAndcreateFile(final List<String> imgPath, final float height, final float width) {
        final List<File> fileList = new ArrayList() {
        };
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (imgPath != null && imgPath.size() > 0) {
                    for (int i = 0; i < imgPath.size(); i++) {

				/*
                 * BitmapFactory.Options options = new BitmapFactory.Options();
				 * options.inJustDecodeBounds = true; // 获取这个图片的宽和高 Bitmap
				 * bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
				 * options.inJustDecodeBounds = false; // 计算缩放比 int be = (int)
				 * (options.outHeight / (float) 200); if (be <= 0) be = 1; //
				 * inSampleSize为1 保持原图 2 1/4大小 options.inSampleSize = be;
				 */

                        BitmapFactory.Options newOpts = new BitmapFactory.Options();
                        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
                        newOpts.inJustDecodeBounds = true;
                        Bitmap bitmap = BitmapFactory.decodeFile(imgPath.get(i), newOpts);// 此时返回bm为空

                        newOpts.inJustDecodeBounds = false;
                        int w = newOpts.outWidth;
                        int h = newOpts.outHeight;
                        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
                        float hh = height;// 这里设置高度为800f
                        float ww = width;// 这里设置宽度为480f
                        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                        int be = 1;// be=1表示不缩放
                        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                            be = (int) (newOpts.outWidth / ww);
                        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                            be = (int) (newOpts.outHeight / hh);
                        }
                        if (be <= 0)
                            be = 1;
                        newOpts.inSampleSize = be;// 设置缩放比例

                        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
                        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
                        bitmap = BitmapFactory.decodeFile(imgPath.get(i), newOpts);

                        // 控制图片大小为100k
                        Bitmap tempBitmap = compressImage(bitmap);

                        try {
                            File dir = new File(ALBUM_PATH);
                            if (!dir.exists()) {
                                if (!dir.mkdir()) {

                                }
                            }
                            String fileName = ALBUM_PATH + getRandomFileName() + ".jpg";
                            File file = new File(fileName);
                            if (!file.exists()) {
                                file.createNewFile();
                            } else {
                                file.delete();
                                file.createNewFile();
                            }
                            FileOutputStream out = new FileOutputStream(file);
                            // 保持100%
                            tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                            // 保存
                            fileList.add(file);
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
        return fileList;
    }

    /**
     * 批量压缩并生成图片，返回压缩生成的图片路径
     *
     * @param imgPath 图片路径
     * @return CImgPath 压缩后生成的图片路径集合
     */
    public static String batchCompressAndcreateFile(final String imgPath, final float height, final float width) {

        if (imgPath != null) {

            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);// 此时返回bm为空

            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = height;// 这里设置高度为800f
            float ww = width;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例

            // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

            // 控制图片大小为100k
            Bitmap tempBitmap = compressImage(bitmap);

            try {
                File dir = new File(ALBUM_PATH);
                if (!dir.exists()) {
                    if (!dir.mkdir()) {

                    }
                }
                String fileName = ALBUM_PATH + getRandomFileName() + ".jpg";
                File file = new File(fileName);
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    file.delete();
                    file.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(file);
                // 保持100%
                tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                // 保存
                Log.e("test", "*************add success************");
                return fileName;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return null;
    }

    /**
     * 批量压缩并生成图片，返回压缩生成的图片路径
     *
     * @param imgPath 图片路径
     * @return CImgPath 压缩后生成的图片路径集合
     */
    public static String batchCompressAndcreateFile(final String imgPath) {

        if (imgPath != null) {

            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);// 此时返回bm为空

            newOpts.inJustDecodeBounds = false;
            int imageWidth = newOpts.outWidth;
            int imageHeight = newOpts.outHeight;
            // 现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
            int windowHeight = 1280;// 这里设置高度为1280f
            int windowWidth = 720;// 这里设置宽度为720f
            /*// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
           int be = 1;// be=1表示不缩放
            if (imageWidth > imageHeight && imageWidth > windowWidth) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / windowWidth);
            } else if (imageWidth < imageHeight && imageHeight > windowHeight) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / windowHeight);
            }
            if (be <= 0)
                be = 1;*/
            newOpts.inSampleSize = computeSampleSize(newOpts,windowWidth,windowWidth*windowHeight);
            Log.e("test","**************inSampleSize="+newOpts.inSampleSize);

            // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

            //纠正图片角度
            bitmap = rotateBitmap(bitmap,readPictureDegree(imgPath));

            // 控制图片大小为100k
            Bitmap tempBitmap = compressImage(bitmap);

            try {
                File dir = new File(ALBUM_PATH);
                if (!dir.exists()) {
                    if (!dir.mkdir()) {

                    }
                }
                String fileName = ALBUM_PATH + getRandomFileName() + ".jpg";
                File file = new File(fileName);
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    file.delete();
                    file.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(file);
                // 保持100%
                if(tempBitmap != null)
                    tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                // 保存
                if(tempBitmap != null){
                    if(!tempBitmap.isRecycled()){
                        tempBitmap.recycle();
                        System.gc();
                    }
                }
                return fileName;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (OutOfMemoryError error){
                error.printStackTrace();
            }
        }


        return null;
    }

    /**
     * 质量压缩
     * @return
     */

    private static Bitmap compressImage(Bitmap image) {
        if(image != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > COMPRESS_SIZE) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;// 每次都减少10
                if(options <= 50){
                    //options = 50;
                    break;
                }
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
            BitmapFactory.Options opts=new BitmapFactory.Options();
            opts.inTempStorage = new byte[100 * 1024];
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            opts.inSampleSize = 1;
            Bitmap bitmap = null;
            try{
                bitmap = BitmapFactory.decodeStream(isBm, null, opts);// 把ByteArrayInputStream数据生成图片
            }catch (OutOfMemoryError error){
                error.printStackTrace();
            }

            return bitmap;
        }

        return null;
    }

    /**
     * 随机名字
     * @return
     */

    public static String getRandomFileName() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;// 当前时间
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90 :
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180 :
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270 :
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    /**
     * 旋转图片，使图片保持正确的方向。
     * @param bitmap 原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }


    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;

    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math

                .sqrt(w * h / maxNumOfPixels));

        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math

                .floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;

        }

    }


    /**
     *  处理图片
     * @param bm 所要转换的bitmap
     * @param newWidth 新的宽
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        if(height * scaleWidth >=4096){
            scaleWidth = 4096/(float)height;
        }
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
