package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 报警通知
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Notify")]
public class Alarm extends XmlHelper<Alarm>
{
	private static Alarm _instance;

	/** 
	 单例模式访问
	 
	*/
	public static Alarm getInstance()
	{
		if(_instance == null)
		{
			_instance = new Alarm();
		}
		return _instance;
	}
	/** 
	 命令类型
	 com.micro.iotclouds.live2output.rtsp
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
	 报警级别
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("AlarmPriority")]
	private String privateAlarmPriority;
	public final String getAlarmPriority()
	{
		return privateAlarmPriority;
	}
	public final void setAlarmPriority(String value)
	{
		privateAlarmPriority = value;
	}

	/** 
	 报警方式
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("AlarmMethod")]
	private String privateAlarmMethod;
	public final String getAlarmMethod()
	{
		return privateAlarmMethod;
	}
	public final void setAlarmMethod(String value)
	{
		privateAlarmMethod = value;
	}

	/** 
	 报警时间
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("AlarmTime")]
	private String privateAlarmTime;
	public final String getAlarmTime()
	{
		return privateAlarmTime;
	}
	public final void setAlarmTime(String value)
	{
		privateAlarmTime = value;
	}

	/** 
	 报警内容描述
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("AlarmDescription")]
	private String privateAlarmDescription;
	public final String getAlarmDescription()
	{
		return privateAlarmDescription;
	}
	public final void setAlarmDescription(String value)
	{
		privateAlarmDescription = value;
	}

	/** 
	 经度
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Longitude")]
	private double privateLongitude;
	public final double getLongitude()
	{
		return privateLongitude;
	}
	public final void setLongitude(double value)
	{
		privateLongitude = value;
	}

	/** 
	 纬度
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Latitude")]
	private double privateLatitude;
	public final double getLatitude()
	{
		return privateLatitude;
	}
	public final void setLatitude(double value)
	{
		privateLatitude = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Info")]
	private Info privateInfoEx;
	public final Info getInfoEx()
	{
		return privateInfoEx;
	}
	public final void setInfoEx(Info value)
	{
		privateInfoEx = value;
	}

	public static class Info
	{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("AlarmType")]
		private String privateAlarmType;
		public final String getAlarmType()
		{
			return privateAlarmType;
		}
		public final void setAlarmType(String value)
		{
			privateAlarmType = value;
		}
	}
}