package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Notify")]
public class Register extends XmlHelper<Register>
{
	private static Register instance;

	/** 
	 以单例模式访问
	 
	*/
	public static Register getInstance()
	{
		if(instance == null)
		{
			instance = new Register();
		}
		return instance;
	}
	/** 
	 命令类型：设备信息查询(必选)
	 
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

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("SumNum")]
	private int privateSumNum;
	public final int getSumNum()
	{
		return privateSumNum;
	}
	public final void setSumNum(int value)
	{
		privateSumNum = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("DeviceList")]
	private DevList privateDeviceList;
	public final DevList getDeviceList()
	{
		return privateDeviceList;
	}
	public final void setDeviceList(DevList value)
	{
		privateDeviceList = value;
	}


	public static class DevList
	{
		private java.util.ArrayList<Items> _item = new java.util.ArrayList<Items>();
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("Num")]
		private int privateNum;
		public final int getNum()
		{
			return privateNum;
		}
		public final void setNum(int value)
		{
			privateNum = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Item")]
		public final java.util.ArrayList<Items> getItem()
		{
			return _item;
		}

	}

	public static class Items
	{
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

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Event")]
		private String privateEvent;
		public final String getEvent()
		{
			return privateEvent;
		}
		public final void setEvent(String value)
		{
			privateEvent = value;
		}
	}
}