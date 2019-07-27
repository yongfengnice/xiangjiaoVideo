/*
 *
 *  * Copyright (C) 2015 Eason.Lai (easonline7@gmail.com)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.android.baselibrary.picture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.baselibrary.R;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagesGridFragment;

import java.util.ArrayList;


public class ImagesGridActivity extends FragmentActivity implements View.OnClickListener,AndroidImagePicker.OnImageSelectedListener {

    private TextView mBtnOk;

    ImagesGridFragment mFragment;
    AndroidImagePicker androidImagePicker;
    String imagePath;

    /** 默认选择集 */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    private ArrayList<String> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_grid);

        Intent intent = getIntent();
        resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        androidImagePicker = AndroidImagePicker.getInstance();
        androidImagePicker.clear();
        androidImagePicker.setShouldShowCamera(false);
        androidImagePicker.setSelectLimit(9);
        androidImagePicker.setCurrentSelectedImageSetPosition(0);
        androidImagePicker.clearSelectedImages();//most of the time you need to clear the last selected images or you can comment out this line
        if(resultList != null)
            androidImagePicker.setSelectImageCount(resultList.size());
        else
            androidImagePicker.setSelectImageCount(0);

        mBtnOk = (TextView) findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
        int selectedItemsCount = androidImagePicker.getSelectImageCount();
        int maxSelectLimit = androidImagePicker.getSelectLimit();
        if(selectedItemsCount > 0)
            mBtnOk.setText(getResources().getString(R.string.select_complete,selectedItemsCount,maxSelectLimit));

        if(androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_SINGLE){
            mBtnOk.setVisibility(View.GONE);
        }else{
            mBtnOk.setVisibility(View.VISIBLE);
        }



        findViewById(R.id.btn_backpress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imagePath = getIntent().getStringExtra(AndroidImagePicker.KEY_PIC_PATH);
        mFragment = new ImagesGridFragment();

        mFragment.setOnImageItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position = androidImagePicker.isShouldShowCamera() ? position-1 : position;

                if(androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_MULTI){
                    go2Preview(position);
                }

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();

        androidImagePicker.addOnImageSelectedListener(this);

        int selectedCount = androidImagePicker.getSelectImageCount();
        onImageSelected(0, null, selectedCount, androidImagePicker.getSelectLimit());

    }

    /**
     * 预览页面
     * @param position
     */
    private void go2Preview(int position) {
        Intent intent = new Intent();
        intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, position);
        intent.setClass(ImagesGridActivity.this, ImagePreviewActivity.class);
        startActivityForResult(intent, AndroidImagePicker.REQ_PREVIEW);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ok){
            setResult(RESULT_OK);
            androidImagePicker.notifyOnImagePickComplete(androidImagePicker.getSelectedImages());
            finish();
        }
    }


    @SuppressLint("StringFormatMatches")
    @Override
    public void onImageSelected(int position, ImageItem item, int selectedItemsCount, int maxSelectLimit) {
        if(selectedItemsCount > 0){
            mBtnOk.setEnabled(true);
            //mBtnOk.setText("完成("+selectedItemsCount+"/"+maxSelectLimit+")");
            mBtnOk.setText(getResources().getString(R.string.select_complete,selectedItemsCount,maxSelectLimit));
        }else{
            mBtnOk.setText(getResources().getString(R.string.complete));
            mBtnOk.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        androidImagePicker.removeOnImageItemSelectedListener(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){

            if(requestCode == AndroidImagePicker.REQ_CAMERA){
                Bitmap bmp = (Bitmap)data.getExtras().get("bitmap");
            }else if(requestCode == AndroidImagePicker.REQ_PREVIEW){
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
