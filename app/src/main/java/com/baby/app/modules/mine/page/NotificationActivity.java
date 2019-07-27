package com.baby.app.modules.mine.page;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.NotificationAdapter;
import com.baby.app.modules.mine.presenter.NoticePresenter;
import com.baby.app.modules.mine.view.INoticeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class NotificationActivity extends IBaseActivity implements INoticeView {

    @BindView(R.id.n_recycler_view)
    RecyclerView mRecyclerView;

    private NoticePresenter mNoticePresenter;

    private NotificationAdapter mNotificationAdapter;

    private List<NotificationBean.Data>notificationBeanList = new ArrayList<>();

    @Override
    protected int getLayoutView() {
        return R.layout.activity_notification;
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
        mTitleBuilder.setMiddleTitleText("通知")
                .setLeftDrawable(R.mipmap.ic_back_brown);
    }

    @Override
    public void initUiAndListener() {
        mNoticePresenter = new NoticePresenter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mNotificationAdapter = new NotificationAdapter(R.layout.item_notification_layout, notificationBeanList);
        mRecyclerView.setAdapter(mNotificationAdapter);
        mNoticePresenter.fetchData();
    }


    @Override
    public void refresh(NotificationBean notificationBean) {
        mNotificationAdapter.addData(notificationBean.getData());
    }
}
