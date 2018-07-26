package com.ipfsboost.explorer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ipfsboost.explorer.Main4Activity;
import com.ipfsboost.explorer.R;
import com.ipfsboost.explorer.event.CmdIntentServiceDaemonEvent;
import com.ipfsboost.explorer.event.ExecLog;
import com.ipfsboost.explorer.services.CmdIntentService;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexActivity extends BaseActivity {
    @BindView(R.id.tvLog)
    TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        CmdIntentService.startActionDaemon(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEvent(CmdIntentServiceDaemonEvent event) {
        MobclickAgent.onEvent(this,"IndexActivity_successDaemon");
        startActivity(new Intent(this, Main4Activity.class));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEvent(ExecLog event) {
        if (event != null) {
            tvLog.append(event.log + "\n");
            if (event.log.contains("shutdown")) {
                CmdIntentService.startActionDaemon(this);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
