package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

import SIPSorcery.Sys.*;

//-----------------------------------------------------------------------------
// Filename: Email.cs
//
// Description: Sends an Email.
//
// History:
// 11 Jun 2005	Aaron Clauson	Created.
//
// License: 
// Public Domain

//using GB28181.Logger4Net;

public class SIPSorcerySMTP
{
	//private static readonly ILog logger = AppState.logger;
	// 以下，如果用到须要对应的配置
	private static final String m_smtpServer = AppState.GetConfigSetting("SMTPServer");
	private static final String m_smtpServerPort = AppState.GetConfigSetting("SMTPServerPort");
	private static final String m_smtpServerUseSSL = AppState.GetConfigSetting("SMTPServerUseSSL");
	private static final String m_smtpSendUsername = AppState.GetConfigSetting("SMTPServerUsername");
	private static final String m_smtpSendPassword = AppState.GetConfigSetting("SMTPServerPassword");

	public static void SendEmail(String toAddress, String fromAddress, String subject, String messageBody)
	{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		ThreadPool.QueueUserWorkItem(delegate
		{
			SendEmailAsync(toAddress, fromAddress, null, null, subject, messageBody);
		}
	   );
	}

	public static void SendEmail(String toAddress, String fromAddress, String ccAddress, String bccAddress, String subject, String messageBody)
	{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		ThreadPool.QueueUserWorkItem(delegate
		{
			SendEmailAsync(toAddress, fromAddress, ccAddress, bccAddress, subject, messageBody);
		}
	   );
	}

	private static void SendEmailAsync(String toAddress, String fromAddress, String ccAddress, String bccAddress, String subject, String messageBody)
	{
		if(toAddress.IsNullOrBlank())
		{
			throw new ApplicationException("An email cannot be sent with an empty To address.");
		}
		else
		{

			try
			{
				// Send an email.
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//				using var email = new MailMessage(fromAddress, toAddress, subject, messageBody)
				try
				{
					BodyEncoding = Encoding.UTF8
				}
				finally
				{
					fromAddress.dispose();
					toAddress.dispose();
					subject.dispose();
					messageBody.dispose();
				}

				// Get around bare line feed issue with IIS and qmail.
				if(messageBody != null)
				{
					messageBody = Regex.Replace(messageBody, "(?<!\r)\n", "\r\n");
				}

				if(!ccAddress.IsNullOrBlank())
				{
					email.CC.Add(new MailAddress(ccAddress));
				}

				if(!bccAddress.IsNullOrBlank())
				{
					email.Bcc.Add(new MailAddress(bccAddress));
				}

				if(!m_smtpServer.IsNullOrBlank())
				{
					RelayMail(email);
				}
				else
				{
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//					using var smtpClient = new SmtpClient { DeliveryMethod = SmtpDeliveryMethod.PickupDirectoryFromIis };
					smtpClient.Send(email);
					//logger.Debug("Email sent to " + toAddress);
				}
			}
			catch (RuntimeException excp)
			{
				//logger.Error("Exception SendEmailAsync (To=" + toAddress + "). " + excp.Message);
			}
		}
	}

	private static void RelayMail(MailMessage email)
	{
		try
		{
			int smtpPort = (m_smtpServerPort.IsNullOrBlank()) ? 25 : Integer.parseInt(m_smtpServerPort);

			//logger.Debug("RelayMail attempting to send " + email.Subject + " via " + m_smtpServer + ":" + smtpPort + " to " + email.To);

//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//			using SmtpClient smtpClient = new SmtpClient(m_smtpServer, smtpPort)
			try
			{
				;
			}
			finally
			{
				m_smtpServer.dispose();
				smtpPort.dispose();
			}
			smtpClient.UseDefaultCredentials = false;
			smtpClient.DeliveryMethod = SmtpDeliveryMethod.Network;

			if(!m_smtpServerUseSSL.IsNullOrBlank())
			{
				smtpClient.EnableSsl = Boolean.parseBoolean(m_smtpServerUseSSL);
			}

			if(!m_smtpSendUsername.IsNullOrBlank())
			{
				smtpClient.Credentials = new NetworkCredential(m_smtpSendUsername, m_smtpSendPassword, "");
			}

			smtpClient.Send(email);
			//logger.Debug("RelayMail " + email.Subject + " relayed via " + m_smtpServer + " to " + email.To);
		}
		catch (RuntimeException ex)
		{
			//logger.Error("Exception RelayMail. " + ex.Message);
			throw ex;
		}
	}
}