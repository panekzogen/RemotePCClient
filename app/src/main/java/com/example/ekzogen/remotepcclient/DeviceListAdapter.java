package com.example.ekzogen.remotepcclient;

import java.util.List;

import com.example.ekzogen.remotepcclient.R;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DeviceListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<BluetoothDevice> mData;
    private OnConnectButtonListener mListener;

    public DeviceListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<BluetoothDevice> data) { mData = data; }

    public void setListener(OnConnectButtonListener listener) {
        mListener = listener;
    }

    public int getCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView			=  mInflater.inflate(R.layout.btdevice_list_item, null);

            holder 				= new ViewHolder();

            holder.nameTv		= (TextView) convertView.findViewById(R.id.tv_name);
            holder.addressTv 	= (TextView) convertView.findViewById(R.id.tv_address);
            holder.connectBtn		= (Button) convertView.findViewById(R.id.btn_connect);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BluetoothDevice device	= mData.get(position);

        holder.nameTv.setText(device.getName());
        holder.addressTv.setText(device.getAddress());
        holder.connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnConnectButtonClick(position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView nameTv;
        TextView addressTv;
        TextView connectBtn;
    }

    public interface OnConnectButtonListener {
        public abstract void OnConnectButtonClick(int position);
    }
}