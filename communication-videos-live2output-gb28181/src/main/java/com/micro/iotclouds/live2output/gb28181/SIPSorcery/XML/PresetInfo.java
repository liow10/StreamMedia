package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 设备预置位查询结果
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Response")]
public class PresetInfo extends XmlHelper<PresetInfo>
{
	private static PresetInfo _instance;

	/** 
	 以单例模式访问
	 
	*/
	public static PresetInfo getInstance()
	{
		if(_instance == null)
		{
			_instance = new PresetInfo();
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
	 设备预置位列表，用于平台间或平台与设备间的预置位查询
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("PresetList")]
	private java.util.ArrayList<PresetValue> privatePresetList;
	public final java.util.ArrayList<PresetValue> getPresetList()
	{
		return privatePresetList;
	}
	public final void setPresetList(java.util.ArrayList<PresetValue> value)
	{
		privatePresetList = value;
	}

	public static class PresetValue
	{
		/** 
		 列表项个数，当未配置预置位时取值为0
		 
		*/
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

		private java.util.ArrayList<Item> _presetItem = new java.util.ArrayList<Item>();

		/** 
		 当前配置的预置位记录，当未配置预置位时不填写
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Item")]
		public final java.util.ArrayList<Item> getItems()
		{
			return _presetItem;
		}
	}

	/** 
	 预置位信息
	 
	*/
	public static class Item
	{
		/** 
		 预置位编码
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("PresetID")]
		private String privatePresetID;
		public final String getPresetID()
		{
			return privatePresetID;
		}
		public final void setPresetID(String value)
		{
			privatePresetID = value;
		}

		private String _presetName;
		/** 
		 预置位名称
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("PresetName")]
		public final String getPresetName()
		{
			return _presetName;
		}
		public final void setPresetName(String value)
		{
			_presetName = value == null ? "" : value.replace();
		}
	}
}