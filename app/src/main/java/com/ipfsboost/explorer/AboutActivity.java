package com.ipfsboost.explorer;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.PeerID)
    TextView PeerID;
    @BindView(R.id.Location)
    TextView Location;
    @BindView(R.id.AgentVersion)
    TextView AgentVersion;
    @BindView(R.id.ProtocolVersion)
    TextView ProtocolVersion;
    @BindView(R.id.PublicKey)
    TextView PublicKey;
    @BindView(R.id.NetworkAddresses)
    TextView NetworkAddresses;
    Map ipfsId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Handler handler = new Handler();
        new Thread() {
            public void run() {
                try {
                    IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
                    List<Multihash> multihashList = ipfs.refs.local();
                    ipfsId = ipfs.id();
                    handler.post(runnableUi);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            PeerID.setText(ipfsId.get("ID").toString());
//            Location.setText(ipfsId.get("Location").toString());
            AgentVersion.setText(ipfsId.get("AgentVersion").toString());
            ProtocolVersion.setText(ipfsId.get("ProtocolVersion").toString());
            PublicKey.setText(ipfsId.get("PublicKey").toString());
            NetworkAddresses.setText(ipfsId.get("Addresses").toString());
        }
    };

    public void onClickAbout(View view) {
    }
}
