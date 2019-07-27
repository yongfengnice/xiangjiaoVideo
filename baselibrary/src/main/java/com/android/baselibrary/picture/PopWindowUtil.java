package com.android.baselibrary.picture;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.baselibrary.R;
import com.android.baselibrary.base.Constants;
import com.pizidea.imagepicker.AndroidImagePicker;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by pc on 2015/11/30.
 */
public class PopWindowUtil extends PopupWindow{

    private View conentView;
    private SingleImageInterface imageInterface;
    private BlackListener mBlackListener;

    public static String TEMP_PHOTO;

    private Boolean isSingle = false;

    private Button item_popupwindows;

    private LayoutInflater inflater;
    /**
     * 选中的照片路径
     */
    private ArrayList<String> mSelectPath = new ArrayList<>();

    public void setIsSingle(Boolean single){
        this.isSingle = single;
    }

    public Boolean getIsSingle(){
        return isSingle;
    }

    public void setSingleImageLisener(SingleImageInterface imageInterface) {
        this.imageInterface = imageInterface;
    }

    public void setmBlackListener(BlackListener blackListener) {
        this.mBlackListener = blackListener;
    }

    public void changeBlackTitle(Activity context,boolean isBlack) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        if (isBlack) {
            item_popupwindows.setText("移除黑名单");
        } else {
            item_popupwindows.setText("加入黑名单");
        }

    }

    public PopWindowUtil(Activity context,boolean isBlack){

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_popupwindows, null);
        final LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        LinearLayout photoBack = (LinearLayout) view.findViewById(R.id.item_popupwindows_photo_back);
        photoBack.setVisibility(View.GONE);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //pop.setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        item_popupwindows = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        if (isBlack) {
            item_popupwindows.setText("移除黑名单");
        } else {
            item_popupwindows.setText("加入黑名单");
        }

        Button item_popupwindows_cancel = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                PopWindowUtil.this.dismiss();
                ll_popup.clearAnimation();
            }
        });
        item_popupwindows.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopWindowUtil.this.dismiss();
                if (mBlackListener != null) {
                    mBlackListener.doBlack();
                }
                ll_popup.clearAnimation();
            }
        });

        item_popupwindows_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopWindowUtil.this.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    private Button item_popupwindows_camera;
    private Button item_popupwindows_Photo;

    public Button getItem_popupwindows_camera() {
        return item_popupwindows_camera;
    }

    public Button getItem_popupwindows_Photo() {
        return item_popupwindows_Photo;
    }

    public PopWindowUtil(final Activity context){

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_popupwindows, null);
        final LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //pop.setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        item_popupwindows_camera = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        item_popupwindows_Photo = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button item_popupwindows_cancel = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                PopWindowUtil.this.dismiss();
                ll_popup.clearAnimation();
            }
        });
        item_popupwindows_camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIsSingle()) {
                    imageInterface.gotoCamera();
                } else {
                    // TODO Auto-generated method stub
                    gotoCamera(context);
                }

                PopWindowUtil.this.dismiss();
                ll_popup.clearAnimation();
            }
        });
        item_popupwindows_Photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIsSingle()) {
                    imageInterface.gotoSelectImgActivity();
                } else {
                    // TODO Auto-generated method stub
                    gotoSelectImgActivity(context);
                }
                PopWindowUtil.this.dismiss();
                ll_popup.clearAnimation();
            }
        });
        item_popupwindows_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopWindowUtil.this.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }


    private void gotoCamera(Activity context){
        String sdStatus = Environment.getExternalStorageState();
        /* 检测sd是否可用 */
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡不可用！", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Constants.SD_PATH + "/" + Constants.APP_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }

//        // 发表话题设置
//        SubmitTopicActivity.fileNameBase = Constants.SD_PATH + "/" + Constants.APP_NAME + "/"
//                + AppCompressImageUtil.getRandomFileName();
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(SubmitTopicActivity.fileNameBase)));
//
//        // 保修设置
//        BaoXiuSendActivity2.fileNameBase = Constants.SD_PATH + "/" + Constants.APP_NAME + "/"
//                + AppCompressImageUtil.getRandomFileName();
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(BaoXiuSendActivity2.fileNameBase)));

        // 公用临时文件路径
        TEMP_PHOTO = Constants.SD_PATH + "/" + Constants.APP_NAME + "/"  + AppCompressImageUtil.getRandomFileName()+".jpg";
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(TEMP_PHOTO)));
//
//        context.startActivityForResult(intent, Constants.TAKE_PICTURE);

        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, new File(TEMP_PHOTO).getAbsolutePath());
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, Constants.TAKE_PICTURE);
    }

    public void gotoSelectImgActivity(Activity context) {
        /*int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        context.startActivityForResult(intent, Constants.REQUEST_IMAGE);*/

        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
//        AndroidImagePicker.getInstance().setShouldShowCamera(true);

        Intent intent = new Intent();

        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(ImagesGridActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        intent.setClass(context, ImagesGridActivity.class);
        context.startActivityForResult(intent,Constants.REQUEST_IMAGE);

    }

    public void setmSelectPath(ArrayList<String> mSelectPath){

        this.mSelectPath = mSelectPath;
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {

    }

    public interface BlackListener{
        void doBlack();
    }

}
