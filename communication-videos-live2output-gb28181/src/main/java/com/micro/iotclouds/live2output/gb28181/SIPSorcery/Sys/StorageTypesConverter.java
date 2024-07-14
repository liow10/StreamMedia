package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

public class StorageTypesConverter
{
	//private static ILog logger = AppState.logger;

	public static StorageTypes GetStorageType(String storageType)
	{
		try
		{
//ORIGINAL LINE: return (StorageTypes)Enum.Parse(typeof(StorageTypes), storageType, true);
//C# TO JAVA CONVERTER WARNING: Java does not have a 'ignoreCase' parameter for the static 'valueOf' method of enum types:
			return StorageTypes.valueOf(storageType);
		}
		catch (java.lang.Exception e)
		{
			//logger.Error("StorageTypesConverter " + storageType + " unknown.");
			return StorageTypes.Unknown;
		}
	}
}