package com.test.record.action.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.record.R;
import com.test.record.action.MainActivity;
import com.test.record.db.DBManager;
import com.test.record.db.DBOpenHelper;

/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_forgetActivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_forgetActivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_forgetActivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_forgetActivity_body);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_forgetActivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_forgetActivity_password2);
        mEtRegisteractivityPhonecodes = findViewById(R.id.et_forgetActivity_phoneCodes);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_forgetActivity_showCode);
//        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);

        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mIvRegisteractivityBack.setOnClickListener(this);
        mIvRegisteractivityShowcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_forgetActivity_back: //返回登录页面
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_forgetActivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_forgetActivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
//                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                //注册验证
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode) ) {
                    if (phoneCode.equals(realCode)) {
                        //将用户名和密码加入到数据库中
                        DBManager.updata(password);
                        Intent intent2 = new Intent(this, LoginActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(this,  "验证通过，修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "验证码错误,修改失败", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "未完善信息，修改失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

