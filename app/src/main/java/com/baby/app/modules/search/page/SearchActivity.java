package com.baby.app.modules.search.page;


import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.search.SearchBean;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.search.adapter.SearchAdapter;
import com.baby.app.modules.search.presenter.SearchPresenter;
import com.baby.app.modules.search.view.SearchView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class SearchActivity extends IBaseActivity implements SearchView {

    @BindView(R.id.search_right_view)
    RelativeLayout rightBtn;

    @BindView(R.id.search_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.searc_flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.search_edit_text)
    EditText searchEditText;

    @BindView(R.id.cancel_button)
    ImageView cancelButton;

    @BindView(R.id.search_top_text_view)
    TextView topTextView;

    private View emptyView;
    private List<DetailListBean.Data>dataList = new ArrayList<>();

    private SearchAdapter mSearchAdapter;

    private String[] mVals;

    private InputMethodManager mInputMethodManager;

    private SearchPresenter mSearchPresenter;
    private TagAdapter mTagAdapter;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_search;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
        setToolBarVisible(View.GONE);
    }

    @Override
    public void initUiAndListener() {

        mSearchPresenter = new SearchPresenter(this);
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.setVisibility(View.VISIBLE);
                mFlowLayout.setVisibility(View.VISIBLE);
                topTextView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                searchEditText.setText("");
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {

            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (mVals.length > 0) {
                    mSearchPresenter.selectOPenVideo(mVals[position]);
                    mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
// TODO Auto-generated method stub
                if(arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchEditText.getText()!=null && searchEditText.getText().toString().length() > 0) {
                        mSearchPresenter.selectOPenVideo(searchEditText.getText().toString());
                        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }

                return false;
            }

        });

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ScreenUtil.dip2px(mContext, 6);
                outRect.bottom = ScreenUtil.dip2px(mContext, 6);
            }
        });
        mSearchAdapter = new SearchAdapter(R.layout.item_video_guess_layout, dataList);
        mRecyclerView.setAdapter(mSearchAdapter);
        emptyView = getLayoutInflater().inflate(R.layout.cache_error_layout, null);
        emptyView.setVisibility(View.INVISIBLE);
        TextView empty_msg_text_view = (TextView) emptyView.findViewById(R.id.empty_msg_text_view);
        empty_msg_text_view.setText("暂无搜索结果");
        TextView empty_sub_msg_text_view =(TextView) emptyView.findViewById(R.id.empty_sub_msg_text_view);
        empty_sub_msg_text_view.setVisibility(View.GONE);
        mSearchAdapter.setEmptyView(emptyView);
        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DetailListBean.Data data = dataList.get(position);
                jumpToVideo(data.getId(),data.getVideoName(),data.getVideoUrl());
            }
        });

        mSearchPresenter.fetchHotSearchList();

    }

    @Override
    public void refersh(SearchBean searchBean) {
        mVals = new String[searchBean.getData().size()];
        for (int i = 0; i < searchBean.getData().size(); i++) {
            SearchBean.Data temp = searchBean.getData().get(i);
            mVals[i] = temp.getSearchName();
        }
        mTagAdapter = new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_search_hot_layout,
                        mFlowLayout, false);
                TextView tv = (TextView) layout.findViewById(R.id.search_item_name_view);
                tv.setText(s);
                return layout;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return s.equals("Android");
            }
        };
        mFlowLayout.setAdapter(mTagAdapter);
    }

    @Override
    public void refreshList(DetailListBean listBean) {
        cancelButton.setVisibility(View.VISIBLE);
        mFlowLayout.setVisibility(View.GONE);
        topTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        if (listBean.getData()!=null && listBean.getData().size() > 0) {
            emptyView.setVisibility(View.GONE);
            dataList.addAll(listBean.getData());
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}
