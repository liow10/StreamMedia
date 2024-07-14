package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 心跳请求
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Notify")]
public class KeepAlive extends XmlHelper<KeepAlive>
{
	private static KeepAlive _instance;

	/** 
	 单例模式访问
	 
	*/
	public static KeepAlive getInstance()
	{
		if(_instance == null)
		{
			_instance = new KeepAlive();
		}
		return _instance;
	}

	/** 
	 命令类型
	 
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
}