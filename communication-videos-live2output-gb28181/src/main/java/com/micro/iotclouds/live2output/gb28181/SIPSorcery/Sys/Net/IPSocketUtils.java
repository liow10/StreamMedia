package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys.Net;

public final class IPSocketUtils
{
	public static boolean IsIPSocket(String socket)
	{
		if(socket == null || socket.trim().length() == 0)
		{
			return false;
		}
		else
		{

			return Regex.Match(socket, "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}(:\\d{1,5})$", RegexOptions.Compiled).Success;

		}
	}


}