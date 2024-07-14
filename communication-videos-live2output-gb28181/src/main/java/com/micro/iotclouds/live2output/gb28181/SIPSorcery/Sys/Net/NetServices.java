package GB28181.Sys.Net;

public final class NetServices
{
	public static final int UDP_PORT_START = 1025;
	public static final int UDP_PORT_END = 65535;
	private static final int RTP_RECEIVE_BUFFER_SIZE = 100000000;
	private static final int RTP_SEND_BUFFER_SIZE = 100000000;
	private static final int MAXIMUM_RTP_PORT_BIND_ATTEMPTS = 5; // The maximum number of re-attempts that will be made when trying to bind the RTP port.

	//private static ILog logger = AppState.logger;

	public static PlatformEnum Platform = PlatformEnum.Windows;

	private static Mutex _allocatePortsMutex = new Mutex();

	public static UdpClient CreateRandomUDPListener(IPAddress localAddress, RefObject<IPEndPoint> localEndPoint)
	{
		//return SIPSorcery.Sys.NetServices.CreateRandomUDPListener(localAddress, UDP_PORT_START, UDP_PORT_END, null, out localEndPoint);
	  return CreateRandomUDPListener(localAddress, UDP_PORT_START, UDP_PORT_END, null, localEndPoint);
	}

	public static UdpClient CreateRandomUDPListener(IPAddress localAddress, int start, int end, java.util.ArrayList inUsePorts, RefObject<IPEndPoint> localEndPoint)
	{
		try
		{
			UdpClient randomClient = null;
			int attempts = 1;

			localEndPoint.argvalue = null;

			while (attempts < 50)
			{
				int port = SIPSorcery.Sys.Crypto.GetRandomInt(start, end);
				if(inUsePorts == null || !inUsePorts.contains(port))
				{
					try
					{
						localEndPoint.argvalue = new IPEndPoint(localAddress, port);
						randomClient = new UdpClient(localEndPoint.argvalue);
						break;
					}
					catch(RuntimeException excp)
					{
						//logger.Warn("Warning couldn't create UDP end point for " + localAddress + ":" + port + "." + excp.Message);
					}

					attempts++;
				}
			}

		   //logger.Debug("Attempts to create UDP end point for " + localAddress  + " by randam port was " + attempts);

			return randomClient;
		}
		catch (java.lang.Exception e)
		{
			throw new ApplicationException("Unable to create a random UDP listener between " + start + " and " + end);
		}
	}

	public static void CreateRtpSocket(IPAddress localAddress, int startPort, int endPort, boolean createControlSocket, RefObject<Socket> rtpSocket, RefObject<Socket> controlSocket)
	{
		rtpSocket.argvalue = null;
		controlSocket.argvalue = null;

		synchronized (_allocatePortsMutex)
		{
			//if (_nextMediaPort >= MEDIA_PORT_END)
			//{
			//    // The media port range has been used go back to the start. Some connections have most likely been closed.
			//    _nextMediaPort = MEDIA_PORT_START;
			//}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			var inUseUDPPorts = (from p in System.Net.NetworkInformation.IPGlobalProperties.GetIPGlobalProperties().GetActiveUdpListeners() where p.Port >= startPort && p.Port <= endPort && p.Address.toString().equals(localAddress.toString() select) p.Port).OrderBy(x => x).ToList();

			// Make the RTP port start on an even port. Some legacy systems require the RTP port to be an even port number.
			if(startPort % 2 != 0)
			{
				startPort += 1;
			}

			int rtpPort = startPort;
			int controlPort = (createControlSocket == true) ? rtpPort + 1 : 0;

			if(inUseUDPPorts.size() > 0)
			{
				// Find the first two available for the RTP socket.
				for (int index = startPort; index <= endPort; index += 2)
				{
					if(!inUseUDPPorts.Contains(index))
					{
						rtpPort = index;

						if(!createControlSocket)
						{
							break;
						}
						else if(!inUseUDPPorts.Contains(index + 1))
						{
							controlPort = index + 1;
							break;
						}
					}
				}
			}
			else
			{
				rtpPort = startPort;

				if(createControlSocket)
				{
					controlPort = rtpPort + 1;
				}
			}

			if(rtpPort != 0)
			{
				boolean bindSuccess = false;

				for (int bindAttempts = 0; bindAttempts <= MAXIMUM_RTP_PORT_BIND_ATTEMPTS; bindAttempts++)
				{
					try
					{
						// The potential ports have been found now try and use them.
						rtpSocket.argvalue = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
						rtpSocket.argvalue.ReceiveBufferSize = RTP_RECEIVE_BUFFER_SIZE;
						rtpSocket.argvalue.SendBufferSize = RTP_SEND_BUFFER_SIZE;

						rtpSocket.argvalue.Bind(new IPEndPoint(localAddress, rtpPort));

						if(controlPort != 0)
						{
							controlSocket.argvalue = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
							controlSocket.argvalue.Bind(new IPEndPoint(localAddress, controlPort));

							//logger.Debug("Successfully bound RTP socket " + localAddress + ":" + rtpPort + " and control socket " + localAddress + ":" + controlPort + ".");
						}
						else
						{
							//logger.Debug("Successfully bound RTP socket " + localAddress + ":" + rtpPort + ".");
						}

						bindSuccess = true;

						break;
					}
					catch (System.Net.Sockets.SocketException e)
					{
						if(controlPort != 0)
						{
							//logger.Warn("Failed to bind on address " + localAddress + " to RTP port " + rtpPort + " and/or control port of " + controlPort + ", attempt " + bindAttempts + ".");
						}
						else
						{
							//logger.Warn("Failed to bind on address " + localAddress + " to RTP port " + rtpPort + ", attempt " + bindAttempts + ".");
						}

						// Increment the port range in case there is an OS/network issue closing/cleaning up already used ports.
						rtpPort += 2;
						controlPort += (controlPort != 0) ? 2 : 0;
					}
				}

				if(!bindSuccess)
				{
					throw new ApplicationException("An RTP socket could be created due to a failure to bind on address " + localAddress + " to the RTP and/or control ports within the range of " + startPort + " to " + endPort + ".");
				}
			}
			else
			{
				throw new ApplicationException("An RTP socket could be created due to a failure to allocate on address " + localAddress + " and an RTP and/or control ports within the range " + startPort + " to " + endPort + ".");
			}
		}
	}


	/** 
	 Extracts the default gateway from the route print command
	 
	 @return The IP Address of the default gateway.
	*/
	public static IPAddress GetDefaultGateway()
	{
		try
		{
			String routeTable = CallRoute();

			if(routeTable != null)
			{
				if(Platform == PlatformEnum.Windows)
				{
					Match gatewayMatch = Regex.Match(routeTable, "Gateway\\s*:\\s*(?<gateway>(\\d+\\.){3}\\d+)", RegexOptions.IgnoreCase | RegexOptions.Singleline);

					if(gatewayMatch.Success)
					{
						return IPAddress.Parse(gatewayMatch.Result("${gateway}"));
					}
				}
				else
				{
					Match gatewayMatch = Regex.Match(routeTable, "default\\s*(?<gateway>(\\d+\\.){3}\\d+)", RegexOptions.IgnoreCase | RegexOptions.Singleline);

					if(gatewayMatch.Success)
					{
						return IPAddress.Parse(gatewayMatch.Result("${gateway}"));
					}
				}
			}

			return null;
		}
		catch (java.lang.Exception e)
		{
			//logger.Error("Exception GetDefaultGateway. " + excp.Message);
			return null;
		}
	}

	/** 
	 Attempts to get the local IP address that is being used with the default gateway and is therefore the one being used
	 to connect to the internet.
	 
	 @param defaultGateway
	 @return 
	*/
	public static IPAddress GetDefaultIPAddress(IPAddress defaultGateway)
	{
		try
		{
			String[] gatewayOctets = defaultGateway.toString().split("\\.");

			IPHostEntry hostEntry = Dns.GetHostEntry(Dns.GetHostName());

			java.util.ArrayList possibleMatches = new java.util.ArrayList();
			for (IPAddress localAddress : hostEntry.AddressList)
			{
				possibleMatches.add(localAddress);
			}

			for (int octetIndex = 0; octetIndex < 4; octetIndex++)
			{
				IPAddress[] testAddresses = (IPAddress[])possibleMatches.ToArray(IPAddress.class);
				for (IPAddress localAddress : testAddresses)
				{
					String[] localOctets = localAddress.toString().split("\\.");
					if(!gatewayOctets[octetIndex].equals(localOctets[octetIndex]))
					{
						possibleMatches.remove(localAddress);
					}

					if(possibleMatches.size() == 1)
					{
						return (IPAddress)possibleMatches.get(0);
					}
				}
			}

			return null;
		}
		catch (java.lang.Exception e)
		{
			//logger.Error("Exception GetDefaultIPAddress. " + excp.Message);
			return null;
		}
	}

	/** 
	 Calls the operating system command 'route print' to obtain the IP
	 routing information.
	 
	 @return A string holding the output of the command.
	*/
	public static String CallRoute()
	{
		try
		{
			if(Platform == PlatformEnum.Windows)
			{
				return CallShellCommand("route", "print");
			}
			else
			{
				return CallShellCommand("route", "");
			}
		}
		catch (RuntimeException excp)
		{
			//logger.Error("Exception call to 'route print': " + excp.Message);
			throw new ApplicationException("An attempt to call 'route print' failed. " + excp.getMessage());
		}
	}

	/** Creates a new process to execute a specified shell command and returns the output
	 to the caller as a string.
	 
	*/
	public static String CallShellCommand(String command, String commandLine)
	{
		Process osProcess = new Process();
		osProcess.StartInfo.CreateNoWindow = true;
		osProcess.StartInfo.UseShellExecute = false;
		//osProcess.StartInfo.UseShellExecute = true;
		osProcess.StartInfo.RedirectStandardOutput = true;

		osProcess.StartInfo.FileName = command;
		osProcess.StartInfo.Arguments = commandLine;

		osProcess.Start();

		osProcess.WaitForExit();
		return osProcess.StandardOutput.ReadToEnd();
	}
}