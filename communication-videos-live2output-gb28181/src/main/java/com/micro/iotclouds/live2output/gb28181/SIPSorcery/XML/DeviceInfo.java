package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/**
 * 设备信息查询响应结果
 * 
 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Response")]
public class DeviceInfo extends XmlHelper<DeviceInfo> {
	private static DeviceInfo instance;

	/**
	 * 以单例模式访问
	 * 
	 */
	public static DeviceInfo getInstance() {
		if (instance == null) {
			instance = new DeviceInfo();
		}
		return instance;
	}

	/**
	 * 命令类型：设备信息查询(必选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("CmdType")]
	private CommandType privateCmdType = CommandType.forValue(0);

	public final CommandType getCmdType() {
		return privateCmdType;
	}

	public final void setCmdType(CommandType value) {
		privateCmdType = value;
	}

	/**
	 * 命令序列号(必选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("SN")]
	private int privateSN;

	public final int getSN() {
		return privateSN;
	}

	public final void setSN(int value) {
		privateSN = value;
	}

	/**
	 * 目标设备/区域/系统的编码(必选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("DeviceID")]
	private String privateDeviceID;

	public final String getDeviceID() {
		return privateDeviceID;
	}

	public final void setDeviceID(String value) {
		privateDeviceID = value;
	}

	private String _devName;

	/**
	 * 目标设备/区域/系统的名称(可选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("DeviceName")]
	public final String getDeviceName() {
		return _devName;
	}

	public final void setDeviceName(String value) {
		_devName = value == null ? "" : value;
	}

	/**
	 * 查询结果(必选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("Result")]
	private String privateResult;

	public final String getResult() {
		return privateResult;
	}

	public final void setResult(String value) {
		privateResult = value;
	}

	/**
	 * 设备生产商(可选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("Manufacturer")]
	private String privateManufacturer;

	public final String getManufacturer() {
		return privateManufacturer;
	}

	public final void setManufacturer(String value) {
		privateManufacturer = value;
	}

	/**
	 * 设备型号
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("Model")]
	private String privateModel;

	public final String getModel() {
		return privateModel;
	}

	public final void setModel(String value) {
		privateModel = value;
	}

	/**
	 * 设备固件版本(可选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("Firmware")]
	private String privateFirmware;

	public final String getFirmware() {
		return privateFirmware;
	}

	public final void setFirmware(String value) {
		privateFirmware = value;
	}

	/**
	 * 视频输入通道数(可选)
	 * 
	 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	// [XmlElement("Channel")]
	private int privateChannel;

	public final int getChannel() {
		return privateChannel;
	}

	public final void setChannel(int value) {
		privateChannel = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 方法
	public final void Save() {
		super.Save(this);
	}

	public final void Read() {
		instance = this.Read(this.getClass());

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion
}