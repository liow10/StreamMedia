package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 设备配置信息查询应答
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Response")]
public class DeviceConfigDownload extends XmlHelper<DeviceConfigDownload>
{
	private static DeviceConfigDownload _instance;
	/** 
	 单例模式
	 
	*/
	public static DeviceConfigDownload getInstance()
	{
		if(_instance == null)
		{
			_instance = new DeviceConfigDownload();
		}
		return _instance;
	}

	/** 
	 命令类型：设备配置获取(必选)
	 
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
	 命令序列号(必选)
	 
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
	 设备/区域编码(必选)
	 
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
	 查询结果标志(必选)
	 
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
	 基本配置参数
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("BasicParam")]
	private BasicParamAttr privateBasicParam;
	public final BasicParamAttr getBasicParam()
	{
		return privateBasicParam;
	}
	public final void setBasicParam(BasicParamAttr value)
	{
		privateBasicParam = value;
	}

	/** 
	 基本配置参数
	 
	*/
	public static class BasicParamAttr
	{
		/** 
		 设备名称(必选)
		 
		*/
		private String privateName;
		public final String getName()
		{
			return privateName;
		}
		public final void setName(String value)
		{
			privateName = value;
		}

		/** 
		 注册过期时间(必选)
		 
		*/
		private String privateExpiration;
		public final String getExpiration()
		{
			return privateExpiration;
		}
		public final void setExpiration(String value)
		{
			privateExpiration = value;
		}

		/** 
		 心跳间隔时间(必选)
		 
		*/
		private int privateHeartBeatInterval;
		public final int getHeartBeatInterval()
		{
			return privateHeartBeatInterval;
		}
		public final void setHeartBeatInterval(int value)
		{
			privateHeartBeatInterval = value;
		}

		/** 
		 心跳超时次数(必选)
		 
		*/
		private int privateHeartBeatCount;
		public final int getHeartBeatCount()
		{
			return privateHeartBeatCount;
		}
		public final void setHeartBeatCount(int value)
		{
			privateHeartBeatCount = value;
		}

		/** 
		 定位功能支持情况(可选，默认值取0)
		 0，不支持
		 1，支持GPS定位
		 2，支持北斗定位
		 
		*/
		private int privatePositionCapability;
		public final int getPositionCapability()
		{
			return privatePositionCapability;
		}
		public final void setPositionCapability(int value)
		{
			privatePositionCapability = value;
		}

		/** 
		 经度(可选)
		 
		*/
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
		 纬度(可选)
		 
		*/
		private double privateLatitude;
		public final double getLatitude()
		{
			return privateLatitude;
		}
		public final void setLatitude(double value)
		{
			privateLatitude = value;
		}
	}
}