﻿package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 录像信息
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Response")]
public class RecordInfo extends XmlHelper<RecordInfo>
{
	private static RecordInfo _instance;

	/** 
	 以单例模式访问
	 
	*/
	public static RecordInfo getInstance()
	{
		if(_instance == null)
		{
			_instance = new RecordInfo();
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
	 设备名称
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Name")]
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
	 录像总条数
	 
	*/
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

	/** 
	 录像列表
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("RecordList")]
	private RecordList privateRecordItems;
	public final RecordList getRecordItems()
	{
		return privateRecordItems;
	}
	public final void setRecordItems(RecordList value)
	{
		privateRecordItems = value;
	}

	/** 
	 录像列表信息
	 
	*/
	public static class RecordList
	{
		private java.util.ArrayList<Item> _recordItems = new java.util.ArrayList<Item>();

		/** 
		 设备项
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Item")]
		public final java.util.ArrayList<Item> getItems()
		{
			return _recordItems;
		}
	}

	public static class Item
	{
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
		 录像名称
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Name")]
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
		 文件路径
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("FilePath")]
		private String privateFilePath;
		public final String getFilePath()
		{
			return privateFilePath;
		}
		public final void setFilePath(String value)
		{
			privateFilePath = value;
		}

		/** 
		 录像地址
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Address")]
		private String privateAddress;
		public final String getAddress()
		{
			return privateAddress;
		}
		public final void setAddress(String value)
		{
			privateAddress = value;
		}

		/** 
		 开始时间
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("StartTime")]
		private String privateStartTime;
		public final String getStartTime()
		{
			return privateStartTime;
		}
		public final void setStartTime(String value)
		{
			privateStartTime = value;
		}

		/** 
		 结束时间
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("EndTime")]
		private String privateEndTime;
		public final String getEndTime()
		{
			return privateEndTime;
		}
		public final void setEndTime(String value)
		{
			privateEndTime = value;
		}

		/** 
		 保密属性 0不涉密 1涉密
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Secrecy")]
		private int privateSecrecy;
		public final int getSecrecy()
		{
			return privateSecrecy;
		}
		public final void setSecrecy(int value)
		{
			privateSecrecy = value;
		}

		/** 
		 录像产生类型 time alarm manual all
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Type")]
		private String privateType;
		public final String getType()
		{
			return privateType;
		}
		public final void setType(String value)
		{
			privateType = value;
		}

		/** 
		 录像产生者ID
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("RecorderID")]
		private String privateRecorderID;
		public final String getRecorderID()
		{
			return privateRecorderID;
		}
		public final void setRecorderID(String value)
		{
			privateRecorderID = value;
		}
	}
}