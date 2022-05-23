package com.test.record.action;

import android.content.Intent;
import android.os.Bundle;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.test.record.R;
import com.test.record.action.login.LoginActivity;

public class ShakeActivity extends AppCompatActivity {

    /**
     * 闪屏页
     */
    private RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_shake);

        rlSplash = (RelativeLayout) findViewById(R.id.rl_splash);

        startAnim();
    }

    /**
     * 启动动画
     */
    private void startAnim() {
        // 渐变动画,从完全透明到完全不透明
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        // 持续时间
        alpha.setDuration(3000);
        // 动画结束后，保持动画状态
        alpha.setFillAfter(true);

        // 设置动画监听器
        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画结束时回调此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                // 跳转到下一个页面
                jumpNextPage();
            }
        });

        // 启动动画
        rlSplash.startAnimation(alpha);
    }

    /**
     * 跳转到下一个页面
     */
    private void jumpNextPage() {
        startActivity(new Intent(ShakeActivity.this, LoginActivity.class));
        finish();
    }
}
