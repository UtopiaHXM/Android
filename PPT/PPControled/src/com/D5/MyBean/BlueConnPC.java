package com.D5.MyBean;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class BlueConnPC {

	private StreamConnectionNotifier notifier;
	private String PCBluetoothAddress;
	private String PCBluetoothName;
	private LocalDevice PClocalDevice;
	private Boolean isExitDevice = false;
	private UUID uuid = null;
	private String url = null;
	private StreamConnection connection = null;

	public BlueConnPC() {
		super();
		try {
			PClocalDevice = LocalDevice.getLocalDevice();
			if (PClocalDevice != null) {
				isExitDevice = true;
			}
		} catch (BluetoothStateException e) {
			isExitDevice = false;
		}
	}

	public BlueConnPC(String pCBluetoothAddress, String pCBluetoothName,
			LocalDevice PClocalDevice, Boolean isExitDevice, String hasDevice,
			UUID uuid, String url, StreamConnection connection) {
		super();
		this.PCBluetoothAddress = pCBluetoothAddress;
		this.PCBluetoothName = pCBluetoothName;
		this.PClocalDevice = PClocalDevice;
		this.isExitDevice = isExitDevice;
		this.uuid = uuid;
		this.url = url;
		this.connection = connection;
	}

	public StreamConnectionNotifier getNotifier() {
		return notifier;
	}

	public void setNotifier(StreamConnectionNotifier notifier) {
		this.notifier = notifier;
	}

	public String getPCBluetoothAddress() {
		return PCBluetoothAddress;
	}

	public void setPCBluetoothAddress(String pCBluetoothAddress) {
		PCBluetoothAddress = pCBluetoothAddress;
	}

	public String getPCBluetoothName() {
		return PCBluetoothName;
	}

	public void setPCBluetoothName(String pCBluetoothName) {
		PCBluetoothName = pCBluetoothName;
	}

	public LocalDevice getPClocalDevice() {
		return PClocalDevice;
	}

	public void setPClocalDevice(LocalDevice PClocalDevice) {
		this.PClocalDevice = PClocalDevice;
	}

	public Boolean getIsExitDevice() {
		return isExitDevice;
	}

	public void setIsExitDevice(Boolean isExitDevice) {
		this.isExitDevice = isExitDevice;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public StreamConnection getConnection() {
		return connection;
	}

	public void setConnection(StreamConnection connection) {
		this.connection = connection;
	}

}
