package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 设备配置
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Control")]
public class DeviceConfig extends XmlHelper<DeviceConfig>
{
	private static DeviceConfig _instance;
	/** 
	 单例模式访问
	 
	*/
	public static DeviceConfig getInstance()
	{
		if(_instance == null)
		{
			_instance = new DeviceConfig();
		}
		return _instance;
	}

	/** 
	 命令类型
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("CmdType")]
	private CommandType privateCommandType = CommandType.forValue(0);
	public final CommandType getCommandType()
	{
		return privateCommandType;
	}
	public final void setCommandType(CommandType value)
	{
		privateCommandType = value;
	}

	/** 
	 序号
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("SN")]
	private int privateSN;
	public final int getSN()
	{
		return privateSN;
	}
	public final void setSN(int value)
	{
		privateSN = value;
	}

	/** 
	 设备编码
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("DeviceID")]
	private String privateDeviceID;
	public final String getDeviceID()
	{
		return privateDeviceID;
	}
	public final void setDeviceID(String value)
	{
		privateDeviceID = value;
	}

	/** 
	 设备配置参数类型
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("ConfigType")]
	private String privateConfigType;
	public final String getConfigType()
	{
		return privateConfigType;
	}
	public final void setConfigType(String value)
	{
		privateConfigType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("BasicParam")]
	private DeviceParam privateBasicParam;
	public final DeviceParam getBasicParam()
	{
		return privateBasicParam;
	}
	public final void setBasicParam(DeviceParam value)
	{
		privateBasicParam = value;
	}


}