package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

import SIPSorcery.Sys.*;

//-----------------------------------------------------------------------------
// Filename: LocalIPConfig.cs
//
// Description: Provides information about the Internet Protocol configuration of the local machine.
//
// History:
// 25 Mar 2009	Aaron Clauson	Created.
//
// License: 
// BSD 3-Clause "New" or "Revised" License, see included LICENSE.md file.
//


//using GB28181.Logger4Net;

public final class LocalIPConfig
{
	public static final String ALL_LOCAL_IPADDRESSES_KEY = "*";
	public static final String LINK_LOCAL_BLOCK_PREFIX = "169.254"; // Used by hosts attempting to acquire a DHCP address. See RFC 3330.

	//private static ILog logger = AppState.logger;

	public static java.util.ArrayList<IPAddress> GetLocalIPv4Addresses()
	{
		java.util.ArrayList<IPAddress> localAddresses = new java.util.ArrayList<IPAddress>();

		NetworkInterface[] adapters = NetworkInterface.GetAllNetworkInterfaces();
		for (NetworkInterface adapter : adapters)
		{
			IPInterfaceProperties adapterProperties = adapter.GetIPProperties();

			UnicastIPAddressInformationCollection localIPs = adapterProperties.UnicastAddresses;
			for (UnicastIPAddressInformation localIP : localIPs)
			{
				if(localIP.Address.AddressFamily == AddressFamily.InterNetwork && !localIP.Address.toString().startsWith(LINK_LOCAL_BLOCK_PREFIX))
				{
					localAddresses.add(localIP.Address);
				}
			}
		}

		return localAddresses;
	}

	public static IPAddress GetDefaultIPv4Address()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		var adapters = from adapter in NetworkInterface.GetAllNetworkInterfaces() where adapter.OperationalStatus == OperationalStatus.Up && adapter.Supports(NetworkInterfaceComponent.IPv4) && adapter.GetIPProperties().GatewayAddresses.size() > 0 && !adapter.GetIPProperties().GatewayAddresses[0].Address.toString().equals("0.0.0.0") select adapter;

		if(adapters == null || adapters.Count() == 0)
		{
			throw new ApplicationException("The default IPv4 address could not be determined as there are were no interfaces with a gateway.");
		}
		else
		{
			UnicastIPAddressInformationCollection localIPs = adapters.First().GetIPProperties().UnicastAddresses;
			for (UnicastIPAddressInformation localIP : localIPs)
			{
				if(localIP.Address.AddressFamily == AddressFamily.InterNetwork && !localIP.Address.toString().startsWith(LINK_LOCAL_BLOCK_PREFIX) && !IPAddress.IsLoopback(localIP.Address))
				{
					return localIP.Address;
				}
			}
		}

		return null;
	}

	public static java.util.ArrayList<IPEndPoint> GetLocalIPv4EndPoints(int port)
	{
		java.util.ArrayList<IPEndPoint> localEndPoints = new java.util.ArrayList<IPEndPoint>();
		java.util.ArrayList<IPAddress> localAddresses = GetLocalIPv4Addresses();

		for (IPAddress localAddress : localAddresses)
		{
			localEndPoints.add(new IPEndPoint(localAddress, port));
		}

		return localEndPoints;
	}

	public static java.util.ArrayList<IPEndPoint> ParseIPSockets(XmlNode socketNodes)
	{
		java.util.ArrayList<IPEndPoint> endPoints = new java.util.ArrayList<IPEndPoint>();
		java.util.ArrayList<IPAddress> localAddresses = GetLocalIPv4Addresses();

		for (XmlNode socketNode : socketNodes.ChildNodes)
		{
			String socketString = socketNode.InnerText;
			//logger.Debug("Parsing end point from socket string " + socketString + ".");

			int port = IPSocket.ParsePortFromSocket(socketString);
			if(socketString.startsWith(ALL_LOCAL_IPADDRESSES_KEY))
			{
				for (IPAddress ipAddress : localAddresses)
				{
					endPoints.add(new IPEndPoint(ipAddress, port));
				}
			}
			else
			{
				endPoints.add(IPSocket.ParseSocketString(socketString));
			}
		}

		return endPoints;
	}
}