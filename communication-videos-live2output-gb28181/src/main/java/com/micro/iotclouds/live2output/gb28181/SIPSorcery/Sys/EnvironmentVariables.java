package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

public class EnvironmentVariables
{
	private static final String MICRO_REGISTRY_ADDRESS = "MICRO_REGISTRY_ADDRESS"; //10.78.115.182:8500
	private static String _MICRO_REGISTRY_ADDRESS;
	private static final String GB_SERVICE_LOCAL_ID = "GB_SERVICE_LOCAL_ID"; //42010000002100000002
	private static String _GB_SERVICE_LOCAL_ID;
	private static final String GB_SERVICE_LOCAL_IP = "GB_SERVICE_LOCAL_IP"; //localhost
	private static String _GB_SERVICE_LOCAL_IP;
	private static final String DEVICE_MANAGEMENT_SERVICE_ADDRESS = "DEVICE_MANAGEMENT_SERVICE_ADDRESS"; //devicemanagementservice:8080
	private static String _DEVICE_MANAGEMENT_SERVICE_ADDRESS;
	private static final String SYSTEM_CONFIGURATION_SERVICE_ADDRESS = "SYSTEM_CONFIGURATION_SERVICE_ADDRESS"; //systemconfigurationservice:8080
	private static String _SYSTEM_CONFIGURATION_SERVICE_ADDRESS;
	private static final String GB_NATS_CHANNEL_ADDRESS = "GB_NATS_CHANNEL_ADDRESS"; //nats://10.78.115.182:4222
	private static String _GB_NATS_CHANNEL_ADDRESS;
	public static String getMicroRegistryAddress()
	{
		return (_MICRO_REGISTRY_ADDRESS != null) ? _MICRO_REGISTRY_ADDRESS : Environment.GetEnvironmentVariable(MICRO_REGISTRY_ADDRESS);
	}
	public static void setMicroRegistryAddress(String value)
	{
		_MICRO_REGISTRY_ADDRESS = value;
	}
	public static String getGbServiceLocalId()
	{
		return (_GB_SERVICE_LOCAL_ID != null) ? _GB_SERVICE_LOCAL_ID : Environment.GetEnvironmentVariable(GB_SERVICE_LOCAL_ID);
	}
	public static void setGbServiceLocalId(String value)
	{
		_GB_SERVICE_LOCAL_ID = value;
	}
	public static String getGbServiceLocalIp()
	{
		return (_GB_SERVICE_LOCAL_IP != null) ? _GB_SERVICE_LOCAL_IP : Environment.GetEnvironmentVariable(GB_SERVICE_LOCAL_IP);
	}
	public static void setGbServiceLocalIp(String value)
	{
		_GB_SERVICE_LOCAL_IP = value;
	}
	public static String getDeviceManagementServiceAddress()
	{
		return (_DEVICE_MANAGEMENT_SERVICE_ADDRESS != null) ? _DEVICE_MANAGEMENT_SERVICE_ADDRESS : Environment.GetEnvironmentVariable(DEVICE_MANAGEMENT_SERVICE_ADDRESS);
	}
	public static void setDeviceManagementServiceAddress(String value)
	{
		_DEVICE_MANAGEMENT_SERVICE_ADDRESS = value;
	}
	public static String getSystemConfigurationServiceAddress()
	{
		return (_SYSTEM_CONFIGURATION_SERVICE_ADDRESS != null) ? _SYSTEM_CONFIGURATION_SERVICE_ADDRESS : Environment.GetEnvironmentVariable(SYSTEM_CONFIGURATION_SERVICE_ADDRESS);
	}
	public static void setSystemConfigurationServiceAddress(String value)
	{
		_SYSTEM_CONFIGURATION_SERVICE_ADDRESS = value;
	}
	public static String getGBNatsChannelAddress()
	{
		return (_GB_NATS_CHANNEL_ADDRESS != null) ? _GB_NATS_CHANNEL_ADDRESS : Environment.GetEnvironmentVariable(GB_NATS_CHANNEL_ADDRESS);
	}
	public static void setGBNatsChannelAddress(String value)
	{
		_GB_NATS_CHANNEL_ADDRESS = value;
	}
	public static int getGBServerGrpcPort()
	{
		return 50051;
	}
	public static int getGbServiceLocalPort()
	{
		return 5061;
	}
}