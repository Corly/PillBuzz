package ro.medapp1;

import java.lang.reflect.Method;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BraceletConnectActivity extends Activity {
	private BluetoothAdapter mBluetoothAdapter = null;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private BluetoothService mBluetoothService = null;
	
	private static final int REQUEST_ENABLE_BT = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bracelet_connect);
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		
		setButtonListDevices();
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        
        if (mBluetoothService != null) {
        	mBluetoothService.stop();
        }

        // Unregister broadcast listeners
        try {
        	this.unregisterReceiver(mReceiver);
        } catch (Exception e) {}
    }
	
	private void setButtonListDevices() {
		Button buttonListDevices = (Button) findViewById(R.id.button_list_devices);
		buttonListDevices.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!mBluetoothAdapter.isEnabled()) {
					Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
				}
				else {
					doDiscovery();
					initializeBluetoothService();
					initializeLists();
					v.setVisibility(View.GONE);
				}
			}
		});
	}
	
	private void doDiscovery() {
		// Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);

        // Turn on sub-title for new devices
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
	}
	
	// Set up the bluetooth service
	private void initializeBluetoothService() {
		if (mBluetoothService == null) {
			mBluetoothService = new BluetoothService(this);
		}
	}
	
	private void initializeLists() {
		// Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        
        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
        
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
        
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        
        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
	}
	
	// The on-click listener for all devices in the ListViews
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            mBluetoothAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            
            // Get the BluetoothDevice object
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            
            // TODO workaround
            /*if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            	unpairDevice(device);
            }*/
            //connectManual(device);
            
            //lists dissapear
            findViewById(R.id.linear_view_blt_connect).setVisibility(View.GONE);
            //TextView appears
            findViewById(R.id.text_connected_info).setVisibility(View.VISIBLE);
            // Attempt to connect to the device
            mBluetoothService.connect(device, false, (TextView)findViewById(R.id.text_connected_info));
            
            Button sendData = (Button) findViewById(R.id.button_send_data);
            sendData.setVisibility(View.VISIBLE);
            sendData.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					byte[] out = "Alarmaaa".getBytes();
					mBluetoothService.write(out);
					Toast.makeText(getApplicationContext(), mBluetoothService.getState() + "", Toast.LENGTH_SHORT).show();
				}
			});
        }
    };
	
    // Workaround.. 'til we find out what is the problem with connected devices
    private void unpairDevice(BluetoothDevice device) {
    	try {
    		Method m = device.getClass().getMethod("removeBond", (Class[]) null);
    		m.invoke(device, (Object[]) null);
    	} catch (Exception e) {
    		Log.e("Bluetooth", "Naspa");
    	}
    }
    
    private void connectManual(BluetoothDevice device) {
    	try {
    		Method m = device.getClass().getDeclaredMethod("connect", BluetoothDevice.class);
    		m.invoke(device, (Object[])null);
    	} catch (Exception e) {
    		Log.e("Bluetooth", "Si mai naspa");
    	}
    }
	
	// The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case REQUEST_ENABLE_BT:
    		if (resultCode == Activity.RESULT_OK) {
    			//Bluetooth enabled
    			doDiscovery();
    			initializeBluetoothService();
				initializeLists();
				findViewById(R.id.button_list_devices).setVisibility(View.GONE);
    		}
    	}
    };

}
