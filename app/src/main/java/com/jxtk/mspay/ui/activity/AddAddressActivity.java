package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.ADEntity;
import com.jxtk.mspay.entity.AddressBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.zou.fastlibrary.helper.InputTextHelper;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.addressBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class AddAddressActivity extends MyActivity {
    CityPickerView mPicker = new CityPickerView();
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_phonenumber)
    EditText edPhonenumber;
    @BindView(R.id.ed_address)
    TextView edAddress;
    @BindView(R.id.ed_detail_address)
    EditText edDetailAddress;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    String sProvince = "江西省";
    String sCity = "南昌市";
    String sCounty = "东湖区";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        if (null != addressBean) {
            edName.setText(addressBean.getUser_name());
            edPhonenumber.setText(addressBean.getMobile());
            edAddress.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty());
            edDetailAddress.setText(addressBean.getAddress());
            title.setTitle("修改地址");
        }
        mPicker.init(this);
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#333333")//标题文字颜  色
                .titleBackgroundColor("#ffffff")//标题栏背景色
                .confirTextColor("#333333")//确认按钮文字颜色
                .confirmText("确定")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#999999")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(5)//显示item的数量
                .province("江西省")//默认显示的省份
                .city("南昌市")//默认显示省份下面的城市
                .district("东湖区")//默认显示省市下面的区县数据
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
                .drawShadows(true)//滚轮不显示模糊效果
                .setLineColor("#06a8fb")//中间横线的颜色
                .setLineHeigh(2)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();
//设置自定义的属性配置
        mPicker.setConfig(cityConfig);
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                sProvince = province.getName();
                sCity = city.getName();
                sCounty = district.getName();
                String s = province.getName() + city.getName() + district.getName();
                edAddress.setText(s);
                //省份province
                //城市city
                //地区district
            }

            @Override
            public void onCancel() {

            }
        });
        new InputTextHelper.Builder(this)
                .setMain(btConfirm)
                .addView(edName)
                .addView(edPhonenumber)
                .addView(edDetailAddress)
                .build();
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ed_address, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ed_address:
                mPicker.showCityPicker();
                break;
            case R.id.bt_confirm:
                String name = edName.getText().toString();
                String phone = edPhonenumber.getText().toString();
                String detailaddress = edDetailAddress.getText().toString();
                if (StringUtil.isEmpty(name) || StringUtil.isEmpty(phone) || StringUtil.isEmpty(detailaddress)) {
                    toast("请填写完整再提交");
                    return;
                }
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("token", Constant.TOKEN);
                map.put("user_name", name);
                map.put("province", sProvince);
                map.put("city", sCity);
                map.put("county", sCounty);
                map.put("address", detailaddress);
                map.put("mobile", phone);
                Observable<ResponseBody> observable;
                if (null==addressBean){
                    observable   = HttpManage.getInstance().getHttpApi().addAddress(map);
                }else {
                    map.put("id", addressBean.getId());
                    observable   = HttpManage.getInstance().getHttpApi().updateAddress(map);
                }
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        if (null== addressBean){
                            addressBean=new AddressBean();
                        }
                        addressBean.setUser_name(name);
                        addressBean.setMobile(phone);
                        addressBean.setProvince(sProvince);
                        addressBean.setCity(sCity);
                        addressBean.setCounty(sCounty);
                        addressBean.setAddress(detailaddress);
                        showComplete();
                        finish();
                    }

                    @Override
                    public void onFault(String errorMsg) {
                        showComplete();
                        toast(errorMsg);
                    }
                }));

                break;
        }
    }
}
