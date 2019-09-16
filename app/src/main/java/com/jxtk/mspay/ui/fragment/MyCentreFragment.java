package com.jxtk.mspay.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyLazyFragment;
import com.jxtk.mspay.entity.MessageNum;
import com.jxtk.mspay.entity.UserInfoBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.jxtk.mspay.ui.activity.BillListActivity;
import com.jxtk.mspay.ui.activity.HelpActivity;
import com.jxtk.mspay.ui.activity.InviteActivity;
import com.jxtk.mspay.ui.activity.InvoiceActivity;
import com.jxtk.mspay.ui.activity.LoginActivity;
import com.jxtk.mspay.ui.activity.MessageCenterActivity;
import com.jxtk.mspay.ui.activity.MyAccoutActivity;
import com.jxtk.mspay.ui.activity.SettingActivity;
import com.jxtk.mspay.ui.activity.TaskActivity;
import com.jxtk.mspay.ui.activity.VipCenterActivity;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.jxtk.mspay.utils.BGLinearlayout;
import com.zou.fastlibrary.widget.ShapeCornerBgView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.messageNum;
import static com.jxtk.mspay.Constant.userInfoBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/29 0029
 * description:
 */public class MyCentreFragment extends MyLazyFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tv_nicknema)
    TextView tvNicknema;
    @BindView(R.id.tv_phonenumber)
    TextView tvPhonenumber;
    @BindView(R.id.tv_integral_num)
    TextView tvIntegralNum;
    @BindView(R.id.constraintLayout_bar)
    ConstraintLayout constraintLayoutBar;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_validity)
    TextView tvValidity;
    @BindView(R.id.constraintlayout)
    ConstraintLayout constraintlayout;
    @BindView(R.id.scb_messagenum)
    ShapeCornerBgView scbMessagenum;
    @BindView(R.id.bg_message)
    BGLinearlayout bgMessage;
    @BindView(R.id.bg_bill)
    BGLinearlayout bgBill;
    @BindView(R.id.bg_invoice)
    BGLinearlayout bgInvoice;
    @BindView(R.id.bg_task)
    BGLinearlayout bgTask;
    @BindView(R.id.bg_invite)
    BGLinearlayout bgInvite;
    @BindView(R.id.bg_setting)
    BGLinearlayout bgSetting;
    @BindView(R.id.im_icon)
    ImageView imIcon;
    boolean islogin = false;
    @BindView(R.id.imageView16)
    ImageView imageView16;
    @BindView(R.id.imageView15)
    ImageView imageView15;
    @BindView(R.id.bg_help)
    BGLinearlayout bgHelp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mycentre;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }


    @Override
    protected void initView() {
      //  getStatusBarConfig().statusBarColor("#ffffff").init();
        if (Constant.ISSHOPER == 1) {
            bgInvoice.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        showLoading();
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getUserInfo(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                userInfoBean = JsonUtils.stringToObject(result, UserInfoBean.class);
                setdata();
                islogin = true;
                showComplete();
            }

            @Override
            public void onFault(String errorMsg) {
                showComplete();
                toast(errorMsg);
            }
        }));
        Observable<ResponseBody> observable2 = HttpManage.getInstance().getHttpApi().getMessage(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable2, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                messageNum = JsonUtils.stringToObject(result, MessageNum.class);
                if (null != messageNum && messageNum.getAll() != 0) {
                    scbMessagenum.setText(messageNum.getAll() + "");
                    scbMessagenum.setVisibility(View.VISIBLE);
                } else {
                    scbMessagenum.setVisibility(View.GONE);
                }
                showComplete();
            }

            @Override
            public void onFault(String errorMsg) {
                if (errorMsg.equals("请重新登陆")) {
                    startActivity(LoginActivity.class);
                }
                showComplete();
                toast(errorMsg);
            }
        }));

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        statusBarConfig().init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setdata() {
        if (null != userInfoBean) {
            ImageLoader.loadImage(circleImageView, Constant.BASE_URL + userInfoBean.getAvatar());
            tvNicknema.setText(userInfoBean.getNickname());
            tvLevel.setText(userInfoBean.getLevel());
            tvMoney.setText(userInfoBean.getMoney());
            tvIntegralNum.setText(userInfoBean.getScore() + "积分");
            tvPhonenumber.setText(userInfoBean.getMobile());
            tvValidity.setText("为您保级至：" + userInfoBean.getLevel_endtime());
            changgebg(userInfoBean.getLevel());
        }
    }

    @OnClick({R.id.constraintLayout_bar, R.id.bg_message, R.id.bg_bill, R.id.bg_invoice, R.id.bg_task, R.id.bg_invite, R.id.bg_help, R.id.bg_setting, R.id.ll_info, R.id.ll_vip})
    public void onViewClicked(View view) {
//        if (!islogin) {
//            toast("网络连接失败，暂不可用");
//            return;
//        }
        switch (view.getId()) {

            case R.id.constraintLayout_bar:

                break;
            case R.id.bg_message:
                startActivity(MessageCenterActivity.class);
                break;
            case R.id.bg_bill:
                startActivity(BillListActivity.class);
                break;
            case R.id.bg_invoice:
                startActivity(InvoiceActivity.class);
                break;
            case R.id.bg_task:
                startActivity(TaskActivity.class);
                break;
            case R.id.bg_invite:
                startActivity(InviteActivity.class);
                break;
            case R.id.ll_info:
                startActivity(MyAccoutActivity.class);
                break;
            case R.id.ll_vip:
                startActivity(VipCenterActivity.class);
                break;
            case R.id.bg_help:
                startActivity(HelpActivity.class);
                break;
            case R.id.bg_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }

    private void changgebg(String level) {
        switch (level) {
            case "普通会员":
                textcolor("#192a56");
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_normal);
                constraintlayout.setBackgroundResource(R.drawable.card_normal);
                imIcon.setBackgroundResource(R.drawable.icon_card_money_navyfornormal);
                imageView15.setBackgroundResource(R.drawable.icon_card_arrow_navyfornormal);
                imageView16.setBackgroundResource(R.drawable.icon_card_arrow_navyfornormal);
                break;
            case "黄金会员":
                textcolor("#5b3b04");
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_gold);
                constraintlayout.setBackgroundResource(R.drawable.card_gold);
                imIcon.setBackgroundResource(R.drawable.icon_card_money_brownforgold);
                imageView15.setBackgroundResource(R.drawable.icon_card_arrow_brownforgold);
                imageView16.setBackgroundResource(R.drawable.icon_card_arrow_brownforgold);
                break;
            case "铂金会员":
                textcolor("#343a46");
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_platinum);
                constraintlayout.setBackgroundResource(R.drawable.card_platinum);
                imIcon.setBackgroundResource(R.drawable.icon_card_money_darkblueforplatinum);
                imageView15.setBackgroundResource(R.drawable.icon_card_arrow_darkblueforplatinum);
                imageView16.setBackgroundResource(R.drawable.icon_card_arrow_darkblueforplatinum);
                break;
            case "钻石会员":
                textcolor("#dcdde1");
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_diamond);
                constraintlayout.setBackgroundResource(R.drawable.card_diamond);
                imIcon.setBackgroundResource(R.drawable.icon_card_money_silverfordiamond);
                imageView15.setBackgroundResource(R.drawable.icon_card_arrow_silverfordiamond);
                imageView16.setBackgroundResource(R.drawable.icon_card_arrow_silverfordiamond);
                break;
        }
    }

    private void textcolor(String color) {
        tvValidity.setTextColor(Color.parseColor(color));
        tvPhonenumber.setTextColor(Color.parseColor(color));
        tvNicknema.setTextColor(Color.parseColor(color));
        tvLevel.setTextColor(Color.parseColor(color));
        tvMoney.setTextColor(Color.parseColor(color));
        textView11.setTextColor(Color.parseColor(color));
        tvIntegralNum.setTextColor(Color.parseColor(color));
    }
}
