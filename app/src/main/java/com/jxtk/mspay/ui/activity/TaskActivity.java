package com.jxtk.mspay.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.MessageListBean;
import com.jxtk.mspay.entity.TaskBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class TaskActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.bt_signin)
    Button btSignin;
    @BindView(R.id.tv_tasktitle1)
    TextView tvTasktitle1;
    @BindView(R.id.tv_point1)
    TextView tvPoint1;
    @BindView(R.id.bt_goto)
    Button btGoto;
    @BindView(R.id.tv_tasktitle2)
    TextView tvTasktitle2;
    @BindView(R.id.tv_point2)
    TextView tvPoint2;
    @BindView(R.id.bt_goto2)
    Button btGoto2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getTask(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<TaskBean> list = JSON.parseArray(result, TaskBean.class);
                if (null!=list&&list.size()>0){
                    for (int i=0;i<list.size();i++){
                        TaskBean taskBean=list.get(i);
                        if (i==0){
                            tvTasktitle1.setText(taskBean.getTitle());
                            tvPoint1.setText("送"+taskBean.getPoint()+"积分");
                            if (taskBean.getFinish()==1){
                                btGoto.setEnabled(false);
                                btGoto.setText("已完成");
//                                btGoto.setBackgroundColor(Color.parseColor("#ffffff"));
                                btGoto.setTextColor(Color.parseColor("#999999"));
                            }
                        }
                        if (i==1){
                            tvTasktitle2.setText(taskBean.getTitle());
                            tvPoint2.setText("送"+taskBean.getPoint()+"积分");
                            if (taskBean.getFinish()==1){
                                btGoto2.setEnabled(false);
                                btGoto2.setText("已完成");
//                                btGoto.setBackgroundColor(Color.parseColor("#ffffff"));
                                btGoto.setTextColor(Color.parseColor("#999999"));
                            }
                        }


                    }
                }

            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
            }

        }));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_signin, R.id.bt_goto, R.id.bt_goto2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_signin:
                startActivity(SignInActivity.class);
                break;
            case R.id.bt_goto:
                startActivity(RechargeActivity.class);
                break;
            case R.id.bt_goto2:
                break;
        }
    }
}
