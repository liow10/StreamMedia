package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 设备状态
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Response")]
public class DeviceStatus extends XmlHelper<DeviceStatus>
{
	private static DeviceStatus _instance;

	/** 
	 以单例模式访问
	 
	*/
	public static DeviceStatus getInstance()
	{
		if(_instance == null)
		{
			_instance = new DeviceStatus();
		}
		return _instance;
	}

	/** 
	 指令类型
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("CmdType")]
	private CommandType privateCmdType = CommandType.forValue(0);
	public final CommandType getCmdType()
	{
		return privateCmdType;
	}
	public final void setCmdType(CommandType value)
	{
		privateCmdType = value;
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
	 查询结果标志
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Result")]
	private String privateResult;
	public final String getResult()
	{
		return privateResult;
	}
	public final void setResult(String value)
	{
		privateResult = value;
	}
	/** 
	 是否在线
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Online")]
	private String privateOnline;
	public final String getOnline()
	{
		return privateOnline;
	}
	public final void setOnline(String value)
	{
		privateOnline = value;
	}
	/** 
	 状态
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Status")]
	private String privateStatus;
	public final String getStatus()
	{
		return privateStatus;
	}
	public final void setStatus(String value)
	{
		privateStatus = value;
	}
	/** 
	 不正常工作原因
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Reason")]
	private String privateReason;
	public final String getReason()
	{
		return privateReason;
	}
	public final void setReason(String value)
	{
		privateReason = value;
	}
}