package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

import SIPSorcery.Sys.*;

/**----------------------------------------------------------------------------
 File Name: AppState.cs
 
 Description: 
 The AppState class holds static application configuration settings for 
 objects requiring configuration information. AppState provides a one stop
 shop for settings rather then have configuration functions in separate 
 classes.
 
 History:
 04 Nov 2004	Aaron Clauson	Created.
 30 May 2020	Edward Chen     Updated.
 License:
 Public Domain.
----------------------------------------------------------------------------
*/

//using GB28181.Logger4Net;

public class AppState implements IConfigurationSectionHandler
{
	public static final String CRLF = "\r\n";
	public static final String DEFAULT_ERRRORLOG_FILE = "c:\\temp\\appstate.error.log";
	public static final String ENCRYPTED_SETTING_PREFIX = "$#";
	private static final String ENCRYPTED_SETTINGS_CERTIFICATE_NAME = "EncryptedSettingsCertificateName";
	private static final String APP_LOGGING_ID = "GB28181.Logger4Net"; // Name of GB28181.Logger4Net identifier.

	// From http://fightingforalostcause.net/misc/2006/compare-email-regex.php.
	public static final String EMAIL_VALIDATION_REGEX = "^([\\w\\!\\#$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`{\\|\\}\\~]+\\.)*[\\w\\!\\#$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`{\\|\\}\\~]+@((((([a-zA-Z0-9]{1}[a-zA-Z0-9\\-]{0,62}[a-zA-Z0-9]{1})|[a-zA-Z])\\.)+[a-zA-Z]{2,6})|(\\d{1,3}\\.){3}\\d{1,3}(\\:\\d{1,5})?)$";

	//public static ILog logger;                        // Used to provide logging functionality for the application.

	private static StringDictionary m_appConfigSettings; // Contains application configuration key, value pairs.
	private static X509Certificate2 m_encryptedSettingsCertificate;
	public static final String NewLine = Environment.NewLine;
	public static String CurrentDirectory;

	static
	{
		try
		{
			try
			{
				// Initialise logging functionality from an XML node in the app.config file.
				System.out.println("Starting logging initialisation.");
				// GB28181.Logger4Net.Config.XmlConfigurator.Configure();
			}
			catch (java.lang.Exception e))
			{
				// Unable to load the GB28181.Logger4Net configuration node (probably invalid XML in the config file).
				System.out.println("Unable to load logging configuration check that the app.config file exists and is well formed.");

				try
				{
					//EventLog.WriteEntry(APP_LOGGING_ID, "Unable to load logging configuration check that the app.config file exists and is well formed.", EventLogEntryType.Error, 0);
				}
				catch (RuntimeException evtLogExcp)
				{
					System.out.println("Exception writing logging configuration error to event log. " + evtLogExcp.getMessage());
				}

				// Configure a basic console appender so if there is anyone watching they can still see log messages and to
				// ensure that any classes using the logger won't get null references.
				ConfigureConsoleLogger();
			}
			finally
			{
				try
				{
					//logger = LogManager.GetLogger(APP_LOGGING_ID);
					//logger.Debug("Target framework: .Net Core 3.1");
					//logger.Debug("EnvironmentVariables.MICRO_REGISTRY_ADDRESS: " + EnvironmentVariables.MicroRegistryAddress);
					//logger.Debug("EnvironmentVariables.GB_NATS_CHANNEL_ADDRESS: " + EnvironmentVariables.GBNatsChannelAddress);
					//logger.Debug("EnvironmentVariables.DEVICE_MANAGEMENT_SERVICE_ADDRESS: " + EnvironmentVariables.DeviceManagementServiceAddress);
					//logger.Debug("EnvironmentVariables.SYSTEM_CONFIGURATION_SERVICE_ADDRESS: " + EnvironmentVariables.SystemConfigurationServiceAddress);
					//logger.Debug("EnvironmentVariables.GB_SERVICE_LOCAL_IP: " + EnvironmentVariables.GbServiceLocalIp);
					////logger.Debug("EnvironmentVariables.GbServiceLocalPort: " + EnvironmentVariables.GbServiceLocalPort);
					//logger.Debug("EnvironmentVariables.GB_SERVICE_LOCAL_ID: " + EnvironmentVariables.GbServiceLocalId);
					//logger.Debug("EnvironmentVariables.GBServerGrpcPort: " + EnvironmentVariables.GBServerGrpcPort);
					//logger.Debug("Notes: if EnvironmentVariables have no value, it gets from xml config.");
				}
				catch (RuntimeException excp)
				{
					StreamWriter errorLog = new StreamWriter(DEFAULT_ERRRORLOG_FILE, true);
//C# TO JAVA CONVERTER TODO TASK: The 'm' format specifier is not converted to Java:
//ORIGINAL LINE: errorLog.WriteLine(DateTime.Now.ToString("dd MMM yyyy HH:mm:ss") + " Exception Initialising AppState Logging. " + excp.Message);
//C# TO JAVA CONVERTER TODO TASK: The 's' format specifier is not converted to Java:
					errorLog.WriteLine(new String.format("%d", java.util.Date()) + " Exception Initialising AppState Logging. " + excp.getMessage());
					errorLog.Close();
				}
			}

			// Initialise the string dictionary to hold the application settings.
			m_appConfigSettings = new StringDictionary();

			CurrentDirectory = Regex.Replace(Path.GetDirectoryName(Assembly.GetExecutingAssembly().CodeBase), "^file:\\\\", ""); // There's undoubtedly a better way!
		}
		catch (RuntimeException excp)
		{
			StreamWriter errorLog = new StreamWriter(DEFAULT_ERRRORLOG_FILE, true);
//C# TO JAVA CONVERTER TODO TASK: The 'm' format specifier is not converted to Java:
//ORIGINAL LINE: errorLog.WriteLine(DateTime.Now.ToString("dd MMM yyyy HH:mm:ss") + " Exception Initialising AppState. " + excp.Message);
//C# TO JAVA CONVERTER TODO TASK: The 's' format specifier is not converted to Java:
			errorLog.WriteLine(new String.format("%d", java.util.Date()) + " Exception Initialising AppState. " + excp.getMessage());
			errorLog.Close();
		}
	}

	//public static ILog GetLogger(string logName)
	//{
	//    return LogManager.GetLogger(logName);
	//}

	/** 
	 Configures the logging object to use a console logger. This would normally be used
	 as a fallback when either the application does not have any logging configuration
	 or there is an error in it.
	 
	*/
	public static void ConfigureConsoleLogger()
	{
		//var appender = new Appender.ConsoleAppender();
		//var fallbackLayout = new Layout.PatternLayout("%m%n");
		//appender.Layout = fallbackLayout;

		// GB28181.Logger4Net.Config.BasicConfigurator.Configure(appender);
	}

	/** 
	 Wrapper around the object holding the application configuration settings extracted
	 from the App.Config file.
	 
	 @param key The name of the configuration setting wanted.
	 @return The value of the configuration setting.
	*/
	public static String GetConfigSetting(String key)
	{
		try
		{
			if(m_appConfigSettings != null && m_appConfigSettings.ContainsKey(key))
			{
				return m_appConfigSettings[key];
			}
			else
			{
				String setting = ConfigurationManager.AppSettings[key];

				if(!setting.IsNullOrBlank())
				{
					if(setting.startsWith(ENCRYPTED_SETTING_PREFIX))
					{
						//logger.Debug("Decrypting appSetting " + key + ".");

						X509Certificate2 encryptedSettingsCertificate = GetEncryptedSettingsCertificate();
						if(encryptedSettingsCertificate != null)
						{
							if(encryptedSettingsCertificate.HasPrivateKey)
							{
								//logger.Debug("Private key on encrypted settings certificate is available.");

								setting = setting.substring(2);
								byte[] encryptedBytes = Convert.FromBase64String(setting);
								RSACryptoServiceProvider rsa = (RSACryptoServiceProvider)encryptedSettingsCertificate.PrivateKey;
								byte[] plainTextBytes = rsa.Decrypt(encryptedBytes, false);
								setting = Encoding.ASCII.GetString(plainTextBytes);

								//logger.Debug("Successfully decrypted appSetting " + key + ".");
							}
							else
							{
								throw new ApplicationException("Could not access private key on encrypted settings certificate.");
							}
						}
						else
						{
							throw new ApplicationException("Could not load the encrypted settings certificate to decrypt setting " + key + ".");
						}
					}

					m_appConfigSettings[key] = setting;
					return setting;
				}
				else
				{
					return null;
				}
			}
		}
		catch (RuntimeException excp)
		{
			//logger.Error("Exception AppState.GetSetting. " + excp.Message);
			throw excp;
		}
	}

	public static boolean GetConfigSettingAsBool(String key)
	{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
		Boolean.TryParse(GetConfigSetting(key), out boolean boolVal);
		return boolVal;
	}

	public static String GetConfigNodeValue(XmlNode configNode, String nodeName)
	{
		XmlNode valueNode = configNode.SelectSingleNode(nodeName);
		if(valueNode != null)
		{
			if(valueNode.Attributes.GetNamedItem("value") != null)
			{
				return valueNode.Attributes.GetNamedItem("value").getValue();
			}
		}

		return null;
	}

	public static Object GetSection(String sectionName)
	{
		return ConfigurationManager.GetSection(sectionName);
	}

	/** 
	 Attempts to load an X509 certificate from a Windows OS certificate store.
	 
	 @param storeLocation The certificate store to load from, can be CurrentUser or LocalMachine.
	 @param certificateSubject The subject name of the certificate to attempt to load.
	 @param checkValidity Checks if the certificate is current and has a verifiable certificate issuer list. Should be
	 set to false for self issued certificates.
	 @return A certificate object if the load is successful otherwise null.
	*/
	public static X509Certificate2 LoadCertificate(StoreLocation storeLocation, String certificateSubject, boolean checkValidity)
	{
		X509Store store = new X509Store(storeLocation);
		//logger.Debug("Certificate store " + store.Location + " opened");
		store.Open(OpenFlags.OpenExistingOnly);
		X509Certificate2Collection collection = store.Certificates.Find(X509FindType.FindBySubjectName, certificateSubject, checkValidity);
		if(collection != null && collection.size() > 0)
		{
			X509Certificate2 serverCertificate = collection[0];
			boolean verifyCert = serverCertificate.Verify();
			//logger.Debug("X509 certificate loaded from current user store, subject=" + serverCertificate.Subject + ", valid=" + verifyCert + ".");
			return serverCertificate;
		}
		else
		{
			//logger.Warn("X509 certificate with subject name=" + certificateSubject + ", not found in " + store.Location + " store.");
			return null;
		}
	}

	private static X509Certificate2 GetEncryptedSettingsCertificate()
	{
		try
		{
			if(m_encryptedSettingsCertificate == null)
			{
				String encryptedSettingsCertName = ConfigurationManager.AppSettings[ENCRYPTED_SETTINGS_CERTIFICATE_NAME];
				if(!encryptedSettingsCertName.IsNullOrBlank())
				{
					X509Certificate2 encryptedSettingsCertificate = LoadCertificate(StoreLocation.LocalMachine, encryptedSettingsCertName, false);
					if(encryptedSettingsCertificate != null)
					{
						//logger.Debug("Encrypted settings certificate successfully loaded for " + encryptedSettingsCertName + ".");
						m_encryptedSettingsCertificate = encryptedSettingsCertificate;
					}
					else
					{
						//logger.Error("Could not load the encrypted settings certificate for " + encryptedSettingsCertName + ".");
					}
				}
				else
				{
					//logger.Error("Could not load the encrypted settings certificate, no " + ENCRYPTED_SETTINGS_CERTIFICATE_NAME + " setting found.");
				}
			}

			return m_encryptedSettingsCertificate;
		}
		catch (RuntimeException excp)
		{
			//logger.Error("Exception AppState.GetEncryptedSettingsCertificate. " + excp.Message);
			return null;
		}
	}

	/** 
	 Checks whether a file path represents a relative or absolute path and if it's relative converts it to
	 an absolute one by prefixing it with the application's base directory.
	 
	 @param filePath The file path to check.
	 @return An absolute file path.
	*/
	public static String ToAbsoluteFilePath(String filePath)
	{
		if(filePath.IsNullOrBlank())
		{
			return null;
		}

		if(!filePath.contains(":"))
		{
			// Relative path.
			filePath = AppDomain.CurrentDomain.BaseDirectory + filePath;
		}

		return filePath;
	}

	/** 
	 Checks whether a directory path represents a relative or absolute path and if it's relative converts it to
	 an absolute one by prefixing it with the application's base directory.
	 
	 @param directoryPath The directory path to check.
	 @return An absolute directory path.
	*/
	public static String ToAbsoluteDirectoryPath(String directoryPath)
	{
		if(directoryPath.IsNullOrBlank())
		{
			return null;
		}

		if(!directoryPath.contains(":"))
		{
			// Relative path.
			directoryPath = AppDomain.CurrentDomain.BaseDirectory + directoryPath;
		}

		if(!directoryPath.endsWith("\\"))
		{
			directoryPath += "\\";
		}

		return directoryPath;
	}

	/** 
	 Handler for processing the App.Config file and retrieving a custom XML node.
	 
	*/
	public final Object Create(Object parent, Object context, XmlNode configSection)
	{
		return configSection;
	}
}