package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 录像信息
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Notify")]
public class MediaStatus extends XmlHelper<MediaStatus>
{
	private static MediaStatus _instance;

	/** 
	 以单例模式访问
	 
	*/
	public static MediaStatus getInstance()
	{
		if(_instance == null)
		{
			_instance = new MediaStatus();
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
	 通知事件类型（取值121表示历史媒体文件发送结束）
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("NotifyType")]
	private String privateNotifyType;
	public final String getNotifyType()
	{
		return privateNotifyType;
	}
	public final void setNotifyType(String value)
	{
		privateNotifyType = value;
	}
}