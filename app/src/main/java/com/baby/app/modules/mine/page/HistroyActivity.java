package com.baby.app.modules.mine.page;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.service.bean.mine.MyHistoryBean;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.util.StringUtils;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.HistoryAdapter;
import com.baby.app.modules.mine.presenter.HistoryPresenter;
import com.baby.app.modules.mine.view.IHistoryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class HistroyActivity extends IBaseActivity implements IHistoryView {

    private int type;// 0我的喜欢 1历史
    @BindView(R.id.lh_recycle_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.controll_bottom_view)
    LinearLayout controll_bottom_view;
    @BindView(R.id.control_seleted_view)
    RelativeLayout control_seleted_view;
    @BindView(R.id.control_delete_view)
    RelativeLayout control_delete_view;
    @BindView(R.id.control_seleted_text_view)
    TextView control_seleted_text_view;

    private List<HistoryBean>historyBeanList = new ArrayList<>();
    private HistoryAdapter mHistoryAdapter;

    private HistoryPresenter mHistoryPresenter;
    private boolean isEdit = false;
    private boolean isAll = false;

    private View emptyView;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_histroy;
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
                isEdit = !isEdit;
                if (isEdit) {
                    mTitleBuilder.setRightText("完成");
                    controll_bottom_view.setVisibility(View.VISIBLE);
                    mHistoryAdapter.setIsEdit(true);
                } else {
                    mTitleBuilder.setRightText("编辑");
                    controll_bottom_view.setVisibility(View.GONE);
                    mHistoryAdapter.setIsEdit(false);
                    for (HistoryBean historyBean:historyBeanList) {
                        historyBean.setSelected(false);
                    }
                }

                mHistoryAdapter.notifyDataSetChanged();

                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        type = getIntent().getExtras().getInt(Constants.KEY_INTENT_ACTIVITY);
        if (type == 0) {
            mTitleBuilder.setMiddleTitleText("我的喜欢")
                    .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("编辑").setRightTextColor(Color.parseColor("#FFFA7334"));
        } else {
            mTitleBuilder.setMiddleTitleText("历史记录")
                    .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("编辑").setRightTextColor(Color.parseColor("#FFFA7334"));
        }

    }

    @Override
    public void initUiAndListener() {

        control_seleted_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAll = !isAll;
                if (isAll) {
                    control_seleted_text_view.setText("取消全选");
                    for (HistoryBean historyBean:historyBeanList) {
                        historyBean.setSelected(true);
                    }
                } else {
                    control_seleted_text_view.setText("全选");
                    for (HistoryBean historyBean:historyBeanList) {
                        historyBean.setSelected(false);
                    }
                }
                mHistoryAdapter.notifyDataSetChanged();
            }
        });

        control_delete_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHave = false;

                ArrayList<String> idList = new ArrayList<>();
                for (HistoryBean historyBean:historyBeanList) {
                    if (historyBean.getSelected()) {
                        isHave = true;
                        idList.add(String.valueOf(historyBean.getId()));
                    }
                }
                if (isHave){
                    String ids = StringUtils.arrayList2String(idList,",");
                    mHistoryPresenter.delete(ids,idList.size());
                } else {
                    ToastUtil.showToast("请选择");
                }
            }
        });


        mHistoryPresenter = new HistoryPresenter(this);
        mHistoryPresenter.setType(type);
        type = getIntent().getExtras().getInt(Constants.KEY_INTENT_ACTIVITY);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ScreenUtil.dip2px(mContext, 6);
                outRect.bottom = ScreenUtil.dip2px(mContext, 0);
            }
        });
        mHistoryAdapter = new HistoryAdapter(R.layout.item_like_histroy_layout, historyBeanList);
        mRecyclerView.setAdapter(mHistoryAdapter);
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryBean data = historyBeanList.get(position);
                jumpToVideo(data.getVideoId(),data.getVideoName(),null);
            }
        });
        mHistoryPresenter.fetchData();

        //失败页面
        emptyView = getLayoutInflater().inflate(R.layout.cache_error_layout, null);
        TextView empty_msg_text_view = (TextView) emptyView.findViewById(R.id.empty_msg_text_view);
        empty_msg_text_view.setText("暂无记录");
        TextView empty_sub_msg_text_view = (TextView) emptyView.findViewById(R.id.empty_sub_msg_text_view);
        empty_sub_msg_text_view.setText("");
//        emptyView.setVisibility(View.INVISIBLE);
        mHistoryAdapter.setEmptyView(emptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void refresh(MyHistoryBean myHistoryBean) {
        historyBeanList.clear();
        mHistoryAdapter.addData(myHistoryBean.getData());
    }

    @Override
    public void deleteSuccess(int count) {
        ToastUtil.showToast("删除了"+count+"条记录");
        mHistoryPresenter.fetchData();
    }
}
