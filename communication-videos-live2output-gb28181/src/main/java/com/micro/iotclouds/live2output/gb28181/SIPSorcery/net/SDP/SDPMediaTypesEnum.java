package com.micro.iotclouds.live2output.gb28181.SIPSorcery.net.SDP;

public enum SDPMediaTypesEnum
{
	audio(1),
	video(2),
	application(3),
	data(4),
	control(5);

	private int intValue;
	private static java.util.HashMap<Integer, SDPMediaTypesEnum> mappings;
	private synchronized static java.util.HashMap<Integer, SDPMediaTypesEnum> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, SDPMediaTypesEnum>();
		}
		return mappings;
	}

	private SDPMediaTypesEnum(int value)
	{
		intValue = value;
		SDPMediaTypesEnum.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SDPMediaTypesEnum forValue(int value)
	{
		return getMappings().get(value);
	}
}