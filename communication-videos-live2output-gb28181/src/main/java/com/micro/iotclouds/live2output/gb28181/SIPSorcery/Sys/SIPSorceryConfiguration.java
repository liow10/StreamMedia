package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

public class SIPSorceryConfiguration
{
	public static final String PERSISTENCE_STORAGETYPE_KEY = "DBStorageType";
	public static final String PERSISTENCE_STORAGECONNSTR_KEY = "DBConnStr";

	private StorageTypes privatePersistenceStorageType = StorageTypes.forValue(0);
	public final StorageTypes getPersistenceStorageType()
	{
		return privatePersistenceStorageType;
	}
	private void setPersistenceStorageType(StorageTypes value)
	{
		privatePersistenceStorageType = value;
	}
	private String privatePersistenceConnStr;
	public final String getPersistenceConnStr()
	{
		return privatePersistenceConnStr;
	}
	private void setPersistenceConnStr(String value)
	{
		privatePersistenceConnStr = value;
	}

	public SIPSorceryConfiguration()
	{
		setPersistenceStorageType((ConfigurationManager.AppSettings[PERSISTENCE_STORAGETYPE_KEY] != null) ? StorageTypesConverter.GetStorageType(ConfigurationManager.AppSettings[PERSISTENCE_STORAGETYPE_KEY]) : StorageTypes.Unknown);
		setPersistenceConnStr(ConfigurationManager.AppSettings[PERSISTENCE_STORAGECONNSTR_KEY]);
	}

	public final String GetAppSetting(String key)
	{
		return ConfigurationManager.AppSettings[key];
	}
}