package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

import SIPSorcery.Sys.*;

//-----------------------------------------------------------------------------
// Filename: UDPListener.cs
//
// Description: Generic listener for UDP based protocols.
// 
// History:
// 27 Feb 2012	Aaron Clauson	Created.
//
// License: 
// BSD 3-Clause "New" or "Revised" License, see included LICENSE.md file.
//


//using GB28181.Logger4Net;


public class UDPListener
{
	private static final String THREAD_NAME = "udplistener-";

	//private static ILog logger = AppState.logger;

	private IPEndPoint m_localEndPoint;
	private Guid m_socketId = Guid.NewGuid();
	private UdpClient m_udpClient;
	private boolean m_closed;

	public Action<UDPListener, IPEndPoint, IPEndPoint, byte[]> PacketReceived;

	public UDPListener(IPEndPoint endPoint)
	{
		m_localEndPoint = endPoint;
		Initialise();
	}

	private void Initialise()
	{
		try
		{
			m_udpClient = new UdpClient(m_localEndPoint);

			Thread listenThread = new Thread(new ThreadStart(Listen));
			listenThread.setName(THREAD_NAME + Crypto.GetRandomString(4));
			listenThread.start();

			//logger.Debug("UDPListener listener created " + m_localEndPoint + ".");
		}
		catch (RuntimeException excp)
		{
			//logger.Error("Exception UDPListener Initialise. " + excp.Message);
			throw excp;
		}
	}

	private void dispose(boolean disposing)
	{
		try
		{
			this.Close();
		}
		catch (RuntimeException excp)
		{
			//logger.Error("Exception Disposing UDPListener. " + excp.Message);
		}
	}

	private void Listen()
	{
		try
		{
			byte[] buffer = null;

			//logger.Debug("UDPListener socket on " + m_localEndPoint + " listening started.");

			while (!m_closed)
			{
				IPEndPoint inEndPoint = new IPEndPoint(IPAddress.Any, 0);

				try
				{
					RefObject<IPEndPoint> tempRef_inEndPoint = new RefObject<IPEndPoint>(inEndPoint);
					buffer = m_udpClient.Receive(tempRef_inEndPoint);
					inEndPoint = tempRef_inEndPoint.argvalue;
				}
				catch (SocketException e)
				{
					// ToDo. Pretty sure these exceptions get thrown when an ICMP message comes back indicating there is no listening
					// socket on the other end. It would be nice to be able to relate that back to the socket that the data was sent to
					// so that we know to stop sending.
					continue;
				}
				catch (RuntimeException listenExcp)
				{
					// There is no point logging this as without processing the ICMP message it's not possible to know which socket the rejection came from.
					//logger.Error("Exception listening on UDPListener. " + listenExcp.Message);

					inEndPoint = new IPEndPoint(IPAddress.Any, 0);
					continue;
				}

				if(buffer == null || buffer.length == 0)
				{
					// No need to care about zero byte packets.
					//string remoteEndPoint = (inEndPoint != null) ? inEndPoint.ToString() : "could not determine";
					//logger.Error("Zero bytes received on SIPUDPChannel " + m_localSIPEndPoint.ToString() + ".");
				}
				else
				{
					if(PacketReceived != null)
					{
						PacketReceived(this, m_localEndPoint, inEndPoint, buffer);
					}
				}
			}

			//logger.Debug("UDPListener socket on " + m_localEndPoint + " listening halted.");
		}
		catch (RuntimeException excp)
		{
			//logger.Error("Exception UDPListener Listen. " + excp.Message);
			//throw excp;
		}
	}

	public final void Send(IPEndPoint destinationEndPoint, String message)
	{
		byte[] messageBuffer = Encoding.UTF8.GetBytes(message);
		Send(destinationEndPoint, messageBuffer);
	}

	public final void Send(IPEndPoint destinationEndPoint, byte[] buffer)
	{
		Send(destinationEndPoint, buffer, buffer.length);
	}

	public final void Send(IPEndPoint destinationEndPoint, byte[] buffer, int length)
	{
		try
		{
			if(destinationEndPoint == null)
			{
				throw new ApplicationException("An empty destination was specified to Send in SIPUDPChannel.");
			}
			else
			{
				if(m_udpClient != null && m_udpClient.Client != null)
				{
					m_udpClient.Send(buffer, length, destinationEndPoint);
				}
			}
		}
		catch (RuntimeException excp)
		{
			//logger.Error("Exception (" + excp.GetType().ToString() + ") UDPListener Send (sendto=>" + IPSocket.GetSocketString(destinationEndPoint) + "). " + excp.Message);
			throw excp;
		}
	}

	public final void Close()
	{
		try
		{
			//logger.Debug("Closing UDPListener " + m_localEndPoint + ".");

			m_closed = true;
			m_udpClient.Close();
		}
		catch (RuntimeException excp)
		{
			//logger.Warn("Exception UDPListener Close. " + excp.Message);
		}
	}
}