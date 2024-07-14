package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 状态改变事件类型
 
*/
public enum EventType 
{
	/** 
	 上线
	 
	*/
	ON(0),
	/** 
	 离线
	 
	*/
	OFF(1),
	/** 
	 视频丢失
	 
	*/
	VLOST(2),
	/** 
	 故障
	 
	*/
	DEFECT(3),
	/** 
	 增加
	 
	*/
	ADD(4),
	/** 
	 删除
	 
	*/
	DEL(5),
	/** 
	 更新
	 
	*/
	UPDATE(6);

	private int intValue;
	private static java.util.HashMap<Integer, EventType> mappings;
	private synchronized static java.util.HashMap<Integer, EventType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, EventType>();
		}
		return mappings;
	}

	private EventType(int value)
	{
		intValue = value;
		EventType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static EventType forValue(int value)
	{
		return getMappings().get(value);
	}
}