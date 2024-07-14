package com.micro.iotclouds.live2output.gb28181.SIPSorcery.net.SDP;

public class SDPMediaTypes
{
	public static SDPMediaTypesEnum GetSDPMediaType(String mediaType)
	{
//ORIGINAL LINE: return (SDPMediaTypesEnum)Enum.Parse(typeof(SDPMediaTypesEnum), mediaType, true);
//C# TO JAVA CONVERTER WARNING: Java does not have a 'ignoreCase' parameter for the static 'valueOf' method of enum types:
		return SDPMediaTypesEnum.valueOf(mediaType);
	}
}