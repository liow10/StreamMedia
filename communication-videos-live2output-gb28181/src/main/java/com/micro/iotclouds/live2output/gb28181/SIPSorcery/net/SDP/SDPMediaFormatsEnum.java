package com.micro.iotclouds.live2output.gb28181.SIPSorcery.net.SDP;

public enum SDPMediaFormatsEnum
{
	PCMU(0), // Audio.
	GSM(3), // Audio.
	G723(4), // Audio.
	PCMA(8), // Audio.
	G729(18), // Audio.
	JPEG(26), // Video.
	H263(34), // Video.
	PS(96), // Video.
	//MPEG4 = 97,  // video.
	H264(98); // Video.

	private int intValue;
	private static java.util.HashMap<Integer, SDPMediaFormatsEnum> mappings;
	private synchronized static java.util.HashMap<Integer, SDPMediaFormatsEnum> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, SDPMediaFormatsEnum>();
		}
		return mappings;
	}

	private SDPMediaFormatsEnum(int value)
	{
		intValue = value;
		SDPMediaFormatsEnum.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SDPMediaFormatsEnum forValue(int value)
	{
		return getMappings().get(value);
	}
}