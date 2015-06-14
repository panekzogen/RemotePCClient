package com.example.ekzogen.remotepcclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity{
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    private ProgressDialog mProgressDlg;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();
    private BluetoothDevice mBluetoothDevice;
    public BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket btSocket = null;

    private static final UUID MY_UUID =
            UUID.fromString("b4906571-36e9-417b-9853-661efcee4196");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();

        mProgressDlg 		= new ProgressDialog(this);

        mProgressDlg.setMessage("Scanning...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                mBluetoothAdapter.cancelDiscovery();
            }
        });

        if(!mBluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            startActivityForResult(intent, 1000);
        }
        else getDevicesList();

    }
    public void getDevicesList(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices == null || pairedDevices.size() == 0) {
            discoverDevices();
        } else {
            mDeviceList = new ArrayList<BluetoothDevice>();


            for(Iterator<BluetoothDevice> i = pairedDevices.iterator(); i.hasNext(); ) {
                BluetoothDevice item = i.next();
                if ( item.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.COMPUTER_DESKTOP ||
                        item.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.COMPUTER_LAPTOP)
                    mDeviceList.add(item);
            }

            Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);

            intent.putParcelableArrayListExtra("device.list", mDeviceList);

            startActivityForResult(intent, 1001);
        }
    }
    public void discoverDevices(){
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);

        mBluetoothAdapter.startDiscovery();
    }

    public void setUpPager(){
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CharSequence title = mSectionsPagerAdapter.getPageTitle(position);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static OutputStream outStream = null;
    private InputStream inStream = null;

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    MediaPlayerFragment mp = new MediaPlayerFragment();
                    mp.setOutStream(outStream);
                    /*mp.setListener(new MediaPlayerFragment.OnButtonClickListener() {
                        @Override
                        public void OnButtonClick() {

                            String message = "pidr";
                            byte[] msgBuffer = message.getBytes();
                            try {
                                outStream.write(msgBuffer);
                            } catch (IOException e) {
                                Log.d("Err", "ny");
                            }
                        }
                    });*/
                    return mp;
                case 1:
                    TpKbFragment tf = new TpKbFragment();
                    tf.setListeners(new TpKbFragment.OnMouseButtonListener() {
                        @Override
                        public void OnMouseButtonClick() {
                            String message = "0109111117115101";
                            byte[] msgBuffer = message.getBytes();
                            try {
                                outStream.write(msgBuffer);
                            } catch (IOException e) {
                                Log.d("Err", "ny");
                            }
                            Intent newIntent = new Intent(MainActivity.this, TouchpadActivity.class);
                            startActivityForResult(newIntent, 1002);
                        }
                    }, new TpKbFragment.OnKeyboardButtonListener() {
                        @Override
                        public void OnKeyboardButtonClick() {
                            String message = "1071011219811197114100";
                            byte[] msgBuffer = message.getBytes();
                            try {
                                outStream.write(msgBuffer);
                            } catch (IOException e) {
                                Log.d("Err", "ny");
                            }
                            Intent newIntent = new Intent(MainActivity.this, KeyboardActivity.class);
                            startActivityForResult(newIntent, 1002);
                        }
                    });

                    return tf;
                case 2:
                    return new SlideShowFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
    /*@Override
    public void onStart(){
    }*/
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause() {
        /*if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }*/
        super.onPause();
        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) {
                Log.d("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
            }
        }
    }

    @Override
    public void onDestroy() {
            //unregisterReceiver(mReceiver);
        if (btSocket != null)
            try     {
                btSocket.close();
            } catch (IOException e) {
                Log.d("Fatal Error", "In onPause() and failed to close socket." + e.getMessage() + ".");
            }
        super.onDestroy();
    }

   /* public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }*/
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode == 1000) {
           if (resultCode == RESULT_OK) {
               getDevicesList();
           }
       } else if (requestCode == 1001) {
           if (resultCode == RESULT_OK) {
               mBluetoothDevice = mDeviceList.get(data.getExtras().getInt("dev_pos"));
               try {
                   btSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
               } catch (IOException e) {
                   Log.d("ERR", "Socket Error");
               }
               mBluetoothAdapter.cancelDiscovery();
               try {
                   btSocket.connect();
               } catch (IOException e) {
                   try {
                       Log.d("Fatal Error", "SOCKET ERROR");  //TODO: UUID CHECK
                       btSocket.close();
                   } catch (IOException e2) {
                       Log.d("Fatal Error", "SOCKET ERROR");
                   }
               }
               try {
                   outStream = btSocket.getOutputStream();
                   inStream = btSocket.getInputStream();
               } catch (IOException e) {
                   Log.d("Fatal Error", "OutStream error");
               }
               String message = mBluetoothAdapter.getName();
               byte[] msgBuffer = message.getBytes();
               try {
                   outStream.write(msgBuffer);
               } catch (IOException e) {
                   Log.d("Err", "ny");
               }
               message = Build.MANUFACTURER;
               msgBuffer = message.getBytes();
               try {
                   outStream.write(msgBuffer);
               } catch (IOException e) {
                   Log.d("Err", "ny");
               }
               message = Build.MODEL;
               msgBuffer = message.getBytes();
               try {
                   outStream.write(msgBuffer);
               } catch (IOException e) {
                   Log.d("Err", "ny");
               }
               message = Build.VERSION.RELEASE;
               msgBuffer = message.getBytes();
               try {
                   outStream.write(msgBuffer);
               } catch (IOException e) {
                   Log.d("Err", "ny");
               }
               setContentView(R.layout.activity_main);
               setUpPager();

               AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
               alertDialog.setTitle("Alert");
               alertDialog.setMessage("Accept connection on pc and click OK");
               alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       });
               alertDialog.show();
               mDeviceList = new ArrayList<BluetoothDevice>();
           } else if ( resultCode == 501){
               discoverDevices();
           } else if (resultCode == RESULT_CANCELED) {
               finish();
               System.exit(0);
           }
       }else if (requestCode == 1002) {
           if (resultCode == RESULT_OK) {
               String message = "0101110100109111117115101";
               byte[] msgBuffer = message.getBytes();
               try {
                   outStream.write(msgBuffer);
               } catch (IOException e) {
                   Log.d("Err", "ny");
               }
           }
       }
   }

    private ListView mListView;
    private DeviceListAdapter mAdapter;
    //private ArrayList<BluetoothDevice> mDeviceList;
    public int pos;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    //showToast("Enabled");

                    //showEnabled();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();

                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();
                if(mBluetoothDevice == null){

                    Intent newIntent = new Intent(MainActivity.this, DeviceListActivity.class);

                    newIntent.putParcelableArrayListExtra("device.list", mDeviceList);

                    startActivityForResult(newIntent, 1001);
                }


                /*setContentView(R.layout.activity_devices);
                //mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");

                mListView		= (ListView) findViewById(R.id.lv_paired);

                mAdapter		= new DeviceListAdapter(MainActivity.this);
                mAdapter.setData(mDeviceList);
                mAdapter.setListener(new DeviceListAdapter.OnConnectButtonListener() {
                    @Override
                    public void OnConnectButtonClick(int position) {
                        BluetoothDevice device = mDeviceList.get(position);
                        //Intent newIntent = new Intent(DeviceListActivity.this, MainActivity.class);

                        //newIntent.putExtra("dev_pos", position);

                        //startActivity(newIntent);
                        mBluetoothDevice = mDeviceList.get(pos);
                        try {
                            btSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                        } catch (IOException e) {
                            Log.d("ERR", "Socket Error");
                        }
                        setContentView(R.layout.activity_main);
                        setUpPager();
                        mBluetoothAdapter.cancelDiscovery();
                        mDeviceList = new ArrayList<BluetoothDevice>();
                    }
                });

                mListView.setAdapter(mAdapter);*/

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if ( device.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.COMPUTER_DESKTOP ||
                        device.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.COMPUTER_LAPTOP)
                    mDeviceList.add(device);

                Log.d("111", "Found device " + device.getName());
            }
        }
    };

}
