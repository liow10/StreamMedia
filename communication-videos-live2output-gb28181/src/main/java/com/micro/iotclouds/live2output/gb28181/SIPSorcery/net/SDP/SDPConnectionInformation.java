package com.micro.iotclouds.live2output.gb28181.SIPSorcery.net.SDP;

public class SDPConnectionInformation
{
	public static final String m_CRLF = "\r\n";

	public String ConnectionNetworkType = "IN"; // Type of network, IN = Internet.
	public String ConnectionAddressType = "IP4"; // Address type, typically IP4 or IP6.
	public String ConnectionAddress; // IP or mulitcast address for the media connection.

	private SDPConnectionInformation()
	{
	}

	public SDPConnectionInformation(String connectionAddress)
	{
		ConnectionAddress = connectionAddress;
	}

	public static SDPConnectionInformation ParseConnectionInformation(String connectionLine)
	{
		SDPConnectionInformation connectionInfo = new SDPConnectionInformation();
		String[] connectionFields = connectionLine.substring(2).trim().split("[ ]", -1);
		connectionInfo.ConnectionNetworkType = connectionFields[0].trim();
		connectionInfo.ConnectionAddressType = connectionFields[1].trim();
		connectionInfo.ConnectionAddress = connectionFields[2].trim();
		return connectionInfo;
	}

	@Override
	public String toString()
	{
		return "c=" + ConnectionNetworkType + " " + ConnectionAddressType + " " + ConnectionAddress + m_CRLF;
	}
}