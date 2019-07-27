package com.baby.app.modules.mine.page;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.presenter.ErPresenter;
import com.baby.app.modules.mine.view.IErView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class ErActivity extends IBaseActivity implements IErView{

    private ErPresenter mErPresenter;
    private ImageView imageQRCode;
    @BindView(R.id.er_code)
    TextView er_code;
    @BindView(R.id.save_er_btn)
    RelativeLayout saveBtn;
    @BindView(R.id.copy_er_btn)
    RelativeLayout copyBtn;
    @BindView(R.id.copy_ma_btn)
    RelativeLayout copy_ma_btn;

    @BindView(R.id.er_back_view)
    LinearLayout er_back_view;


    private String mUrl;
    private String mCode;

    private File saveFile;

    private Bitmap bitmap;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_er;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {

        mTitleBuilder.seTitleBgColor(Color.parseColor("#FF000000"));

        mTitleBuilder.setMiddleTitleText("推广分享获取永久免费观影").setMiddleTitleTextColor(Color.parseColor("#FFFFFFFF"))
                .setLeftDrawable(R.mipmap.ic_white_brown);
        findViewById(com.android.baselibrary.R.id.title_line).setVisibility(View.GONE);
        mTitleBuilder.getMiddleTextView().setTextSize((float) 18.0);
        imageQRCode = (ImageView) findViewById(R.id.img_er);

    }

    @Override
    public void initUiAndListener() {
        mErPresenter = new ErPresenter(this);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUrl!=null) {
                    ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(mUrl);
                    ToastUtil.showLongToast("复制成功");
                }
            }
        });

        er_back_view.getBackground().setAlpha(50);
        copy_ma_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCode!=null) {
                    ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(mCode);
                    ToastUtil.showLongToast("复制成功");
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap!=null) {
                    //获取当前屏幕的大小
                    int width = getWindow().getDecorView().getRootView().getWidth();
                    int height = getWindow().getDecorView().getRootView().getHeight();
                    //生成相同大小的图片
                    Bitmap temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
                    //找到当前页面的跟布局
                    View view = getWindow().getDecorView().getRootView();
                    //设置缓存
                    view.setDrawingCacheEnabled(true);
                    view.buildDrawingCache();
                    //从缓存中获取当前屏幕的图片
                    temBitmap = view.getDrawingCache();
                    saveImage(temBitmap);
                }
            }
        });
        mErPresenter.fetchData();

    }

    @Override
    public void refreshErUrl(String url,String code) {
        mUrl = url;
        mCode = code;
        er_code.setText(code);
        createEr(url);
    }

    private void createEr(String url){
        try {
            // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（310*310）
            bitmap = createQRCode(url, 170);
            imageQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    private static final int BLACK = 0xff000000; //二维码颜色
    private static final int WHITE = 0xffffffff; //二维码颜色
    public static Bitmap createQRCode(String str, int widthAndHeight)
            throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                } else {
                    pixels[y * width + x] =WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private void saveImage(Bitmap bitmap){
        File pictureDir = new File(checkDirPath(Constants.SYSTEM_IMAGE_CACHE));
        if(!pictureDir.exists()){
            //如果该文件夹不存在，则进行创建
            boolean mkdirs = pictureDir.mkdirs();//创建文件夹
        }
        String fileName = String.valueOf(System.currentTimeMillis())+".jpg";
        saveFile = new File(pictureDir.getPath(), fileName);
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 第二步：其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), saveFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 第三步：最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + saveFile)));
        ToastUtil.showToast("保存成功");
        mErPresenter.saveQrcode();
    }

    /**
     * 检查文件是否存在
     */
    private String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

}
