package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

//using GB28181.Logger4Net;

//SIPSorcery.Sys中有相同的类型但是不支持SQLite，所以扩展
public enum StorageTypes
{
	Unknown,
	MSSQL,
	Postgresql,
	MySQL,
	Oracle,
	XML,
	DBLinqMySQL,
	DBLinqPostgresql,
	SimpleDBLinq,
	SQLLinqMySQL,
	SQLLinqPostgresql,
	SQLLinqMSSQL,
	SQLLinqOracle,
	SQLite;

	public int getValue()
	{
		return this.ordinal();
	}

	public static StorageTypes forValue(int value)
	{
		return values()[value];
	}
}