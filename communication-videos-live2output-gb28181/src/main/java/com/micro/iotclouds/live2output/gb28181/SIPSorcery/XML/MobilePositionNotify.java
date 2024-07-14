package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 移动设备位置信息通知
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Notify")]
public class MobilePositionNotify extends XmlHelper<MobilePositionNotify>
{
	private static MobilePositionNotify _instance;

	/** 
	 单例模式访问
	 
	*/
	public static MobilePositionNotify getInstance()
	{
		if(_instance == null)
		{
			_instance = new MobilePositionNotify();
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
	 产生通知时间
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Time ")]
	private int privateTime;
	public final int getTime()
	{
		return privateTime;
	}
	public final void setTime(int value)
	{
		privateTime = value;
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

	/** 
	 速度km/h
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Speed")]
	private double privateSpeed;
	public final double getSpeed()
	{
		return privateSpeed;
	}
	public final void setSpeed(double value)
	{
		privateSpeed = value;
	}

	/** 
	 方向
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Direction")]
	private double privateDirection;
	public final double getDirection()
	{
		return privateDirection;
	}
	public final void setDirection(double value)
	{
		privateDirection = value;
	}

	/** 
	 海拔高度
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Altitude")]
	private double privateAltitude;
	public final double getAltitude()
	{
		return privateAltitude;
	}
	public final void setAltitude(double value)
	{
		privateAltitude = value;
	}
}