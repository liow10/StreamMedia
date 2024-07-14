package com.micro.iotclouds.live2output.gb28181.SIPSorcery.SIPProxy;

import Microsoft.Extensions.Logging.*;
import Serilog.*;
import Serilog.Sinks.SystemConsole.Themes.*;
import SIPSorcery.SIP.*;
import SIPSorcery.SIP.App.*;

public class Program
{
	private static int _listenPort = SIPConstants.DEFAULT_SIP_PORT; // The default UDP SIP port.

	private static Microsoft.Extensions.Logging.ILogger logger = SIPSorcery.Sys.Log.Logger;

	private static SIPTransport _sipTransport;
	private static java.util.HashMap<String, SIPAccountBinding> _sipRegistrations = new java.util.HashMap<String, SIPAccountBinding>(); // [SIP Username, Binding], tracks SIP clients that have registered with the server.

	private static void main()
	{
		try
		{
			System.out.println("SIPSorcery SIP Proxy Demo");

			AddConsoleLogger();

			// Configure the SIP transport layer.
			_sipTransport = new SIPTransport();

			// Use default options to set up a SIP channel.
			SIPUDPChannel sipChannel = new SIPUDPChannel(new IPEndPoint(IPAddress.Any, _listenPort));
			_sipTransport.AddSIPChannel(sipChannel);

			SIPUDPChannel ipv6SipChannel = new SIPUDPChannel(new IPEndPoint(IPAddress.IPv6Any, _listenPort));
			_sipTransport.AddSIPChannel(ipv6SipChannel);

			// Wire up the transport layer so SIP requests and responses have somewhere to go.
			_sipTransport.SIPTransportRequestReceived += SIPTransportRequestReceived;
			_sipTransport.SIPTransportResponseReceived += SIPTransportResponseReceived;

			// If you want to see ALL the nitty gritty SIP traffic wire up the events below.
			//_sipTransport.SIPBadRequestInTraceEvent += SIPBadRequestInTraceEvent;
			//_sipTransport.SIPBadResponseInTraceEvent += SIPBadResponseInTraceEvent;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			_sipTransport.SIPRequestInTraceEvent += SIPRequestInTraceEvent;
			//_sipTransport.SIPRequestOutTraceEvent += SIPRequestOutTraceEvent;
			//_sipTransport.SIPResponseInTraceEvent += SIPResponseInTraceEvent;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			_sipTransport.SIPResponseOutTraceEvent += SIPResponseOutTraceEvent;

			ManualResetEvent mre = new ManualResetEvent(false);
			mre.WaitOne();
		}
		catch (RuntimeException excp)
		{
			System.out.println("Exception Main. " + excp);
		}
	}

	/** 
	 Handler for processing incoming SIP requests.
	 
	 @param localSIPEndPoint The end point the request was received on.
	 @param remoteEndPoint The end point the request came from.
	 @param sipRequest The SIP request received.
	*/
	private static async Task SIPTransportRequestReceived(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, SIPRequest sipRequest)
	{
		try
		{
			if(sipRequest.Method == SIPMethodsEnum.BYE)
			{
				throw new NotImplementedException();
			}
			else if(sipRequest.Method == SIPMethodsEnum.CANCEL)
			{
				throw new NotImplementedException();
			}
			else if(sipRequest.Method == SIPMethodsEnum.INVITE)
			{
				throw new NotImplementedException();
			}
			else if(sipRequest.Method == SIPMethodsEnum.OPTIONS)
			{
				SIPResponse optionsResponse = SIPResponse.GetResponse(sipRequest, SIPResponseStatusCodesEnum.Ok, null);
				await _sipTransport.SendResponseAsync(optionsResponse);
			}
			else if(sipRequest.Method == SIPMethodsEnum.REGISTER)
			{
				SIPResponse tryingResponse = SIPResponse.GetResponse(sipRequest, SIPResponseStatusCodesEnum.Trying, null);
				await _sipTransport.SendResponseAsync(tryingResponse);

				SIPResponseStatusCodesEnum registerResponse = SIPResponseStatusCodesEnum.Ok;

				if(sipRequest.Header.Contact != null && sipRequest.Header.Contact.size() > 0)
				{
					int expiry = sipRequest.Header.Contact[0].Expires > 0 ? sipRequest.Header.Contact[0].Expires : sipRequest.Header.Expires;
					SIPAccount sipAccount = new SIPAccount(null, sipRequest.Header.From.FromURI.Host, sipRequest.Header.From.FromURI.User, null, null);
					SIPAccountBinding binding = new SIPAccountBinding(sipAccount, sipRequest.Header.Contact[0].ContactURI, remoteEndPoint, localSIPEndPoint, expiry);

					if(_sipRegistrations.containsKey(sipAccount.SIPUsername))
					{
						_sipRegistrations.remove(sipAccount.SIPUsername);
					}

					_sipRegistrations.put(sipAccount.SIPUsername, binding.clone());

					logger.LogDebug("Registered contact for " + sipAccount.SIPUsername + " as " + binding.RegisteredContact.toString() + ".");
				}
				else
				{
					registerResponse = SIPResponseStatusCodesEnum.BadRequest;
				}

				SIPNonInviteTransaction registerTransaction = new SIPNonInviteTransaction(_sipTransport, sipRequest, null);
				SIPResponse okResponse = SIPResponse.GetResponse(sipRequest, registerResponse, null);
				registerTransaction.SendResponse(okResponse);
			}
			else
			{
				logger.LogDebug("SIP " + sipRequest.Method + " request received but no processing has been set up for it, rejecting.");
			}
		}
		catch (NotImplementedException e)
		{
			logger.LogDebug(sipRequest.Method + " request processing not implemented for " + sipRequest.URI.ToParameterlessString() + " from " + remoteEndPoint + ".");

			SIPResponse notImplResponse = SIPResponse.GetResponse(sipRequest, SIPResponseStatusCodesEnum.NotImplemented, null);
			await _sipTransport.SendResponseAsync(notImplResponse);
		}
	}

	/** 
	 Handler for processing incoming SIP responses.
	 
	 @param localSIPEndPoint The end point the response was received on.
	 @param remoteEndPoint The end point the response came from.
	 @param sipResponse The SIP response received.
	*/
	private static Task SIPTransportResponseReceived(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, SIPResponse sipResponse)
	{
		logger.LogDebug("Response received from " + remoteEndPoint + " method " + sipResponse.Header.CSeqMethod + " status " + sipResponse.Status + ".");
		return Task.CompletedTask;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Non-functional trace/logging handlers. Main use is troubleshooting.

	/** 
	  Adds a console logger. Can be omitted if internal SIPSorcery debug and warning messages are not required.
	 
	*/
	private static void AddConsoleLogger()
	{
		Microsoft.Extensions.Logging.LoggerFactory loggerFactory = new Microsoft.Extensions.Logging.LoggerFactory();
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter could not resolve the named parameters in the following line:
//ORIGINAL LINE: var loggerConfig = new LoggerConfiguration().WriteTo.Console(theme: AnsiConsoleTheme.Code).Enrich.FromLogContext().MinimumLevel.Is(Serilog.Events.LogEventLevel.Debug).WriteTo.Console().CreateLogger();
		LoggerConfiguration loggerConfig = new LoggerConfiguration().WriteTo.Console(theme: AnsiConsoleTheme.Code).Enrich.FromLogContext().MinimumLevel.Is(Serilog.Events.LogEventLevel.Debug).WriteTo.Console().CreateLogger();
		loggerFactory.AddSerilog(loggerConfig);
		SIPSorcery.Sys.Log.LoggerFactory = loggerFactory;
	}

	private static void SIPRequestInTraceEvent(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, SIPRequest sipRequest)
	{
		logger.LogDebug("REQUEST IN {0}->{1}: {2}", remoteEndPoint.toString(), localSIPEndPoint.toString(), sipRequest.StatusLine);
		//logger.LogDebug(sipRequest.ToString());
	}

	private static void SIPRequestOutTraceEvent(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, SIPRequest sipRequest)
	{
		logger.LogDebug("REQUEST OUT {0}->{1}: {2}", localSIPEndPoint.toString(), remoteEndPoint.toString(), sipRequest.StatusLine);
		//logger.LogDebug(sipRequest.ToString());
	}

	private static void SIPResponseInTraceEvent(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, SIPResponse sipResponse)
	{
		logger.LogDebug("RESPONSE IN {0}->{1}: {2}", remoteEndPoint.toString(), localSIPEndPoint.toString(), sipResponse.ShortDescription);
		//logger.LogDebug(sipResponse.ToString());
	}

	private static void SIPResponseOutTraceEvent(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, SIPResponse sipResponse)
	{
		logger.LogDebug("RESPONSE OUT {0}->{1}: {2}", localSIPEndPoint.toString(), remoteEndPoint.toString(), sipResponse.ShortDescription);
		//logger.LogDebug(sipResponse.ToString());
	}

	private static void SIPBadRequestInTraceEvent(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, String message, SIPValidationFieldsEnum sipErrorField, String rawMessage)
	{
		logger.LogWarning("Bad SIPRequest. Field=" + sipErrorField + ", Message=" + message + ", Remote=" + remoteEndPoint.toString() + ".");
	}

	private static void SIPBadResponseInTraceEvent(SIPEndPoint localSIPEndPoint, SIPEndPoint remoteEndPoint, String message, SIPValidationFieldsEnum sipErrorField, String rawMessage)
	{
		logger.LogWarning("Bad SIPResponse. Field=" + sipErrorField + ", Message=" + message + ", Remote=" + remoteEndPoint.toString() + ".");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}