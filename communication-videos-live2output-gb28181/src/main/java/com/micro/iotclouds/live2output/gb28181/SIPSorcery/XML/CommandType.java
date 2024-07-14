package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 命令类型
 
*/
public enum CommandType
{
	/** 
	 未知
	 
	*/
	Unknown(0),
	/** 
	 心跳
	 
	*/
	Keepalive(1),
	/** 
	 设备目录
	 
	*/
	Catalog(2),
	/** 
	 视频直播
	 
	*/
	Play(3),
	/** 
	 录像点播
	 
	*/
	Playback(4),
	/** 
	 设备控制
	 
	*/
	DeviceControl(5),
	/** 
	 警告通知
	 
	*/
	Alarm(6),
	/** 
	 设备信息
	 
	*/
	DeviceInfo(7),
	/** 
	 设备状态
	 
	*/
	DeviceStatus(8),
	/** 
	 文件检索
	 
	*/
	RecordInfo(9),
	/** 
	 文件下载
	 
	*/
	Download(10),
	/** 
	 设备配置
	 
	*/
	ConfigDownload(11),
	/** 
	 语音广播
	 
	*/
	Broadcast(12),
	/** 
	 预置位查询
	 
	*/
	PresetQuery(13),

	MobilePosition(14),
	/** 
	 设备配置
	 
	*/
	DeviceConfig(15),
	/** 
	 媒体结束通知
	 
	*/
	MediaStatus(16);

	private int intValue;
	private static java.util.HashMap<Integer, CommandType> mappings;
	private synchronized static java.util.HashMap<Integer, CommandType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, CommandType>();
		}
		return mappings;
	}

	private CommandType(int value)
	{
		intValue = value;
		CommandType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CommandType forValue(int value)
	{
		return getMappings().get(value);
	}
}