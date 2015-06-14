package com.example.ekzogen.remotepcclient;

import com.example.ekzogen.remotepcclient.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.media.Image;
import android.os.Bundle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class DeviceListActivity extends Activity {
    private ListView mListView;
    private DeviceListAdapter mAdapter;
    private ArrayList<BluetoothDevice> mDeviceList;
    private ImageButton refreshButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_devices);

        refreshButton = (ImageButton)findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(501);
                finish();
            }
        });

        mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");

        mListView		= (ListView) findViewById(R.id.lv_paired);

        mAdapter		= new DeviceListAdapter(this);

        mAdapter.setData(mDeviceList);
        mAdapter.setListener(new DeviceListAdapter.OnConnectButtonListener() {
            @Override
            public void OnConnectButtonClick(int position) {
                BluetoothDevice device = mDeviceList.get(position);
                /*Intent newIntent = new Intent(DeviceListActivity.this, MainActivity.class);

                newIntent.putExtra("dev_pos", position);

                startActivity(newIntent);*/

                Intent data = new Intent();
                data.putExtra("dev_pos", position);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        mListView.setAdapter(mAdapter);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}