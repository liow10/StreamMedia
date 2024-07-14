package com.micro.iotclouds.live2output.gb28181.SIPSorcery.SIPProxy;

import Microsoft.Extensions.Logging.*;
import Serilog.*;
import Serilog.Sinks.SystemConsole.Themes.*;
import SIPSorcery.SIP.*;
import SIPSorcery.SIP.App.*;

//-----------------------------------------------------------------------------
// Filename: Program.cs
//
// Description: An example of a simple SIP Proxy Server. 
//
// Author(s):
// Aaron Clauson (aaron@sipsorcery.com)
// 
// History: 
// 15 Nov 2016	Aaron Clauson	Created, Hobart, Australia.
// 13 Oct 2019  Aaron Clauson   Updated to use the SIPSorcery nuget package.
// 25 Jan 2020  Aaron Clauson   Converted from net452 to netcoreapp3.0.
//
// License: 
// BSD 3-Clause "New" or "Revised" License, see included LICENSE.md file.
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Notes:
//
// A convenient tool to test SIP applications is [SIPp] (https://github.com/SIPp/sipp). The OPTIONS request handling can 
// be tested from Ubuntu or [WSL] (https://docs.microsoft.com/en-us/windows/wsl/install-win10) using the steps below.
//
// $ sudo apt install sip-tester
// $ wget https://raw.githubusercontent.com/saghul/sipp-scenarios/master/sipp_uac_options.xml
// $ sipp -sf sipp_uac_options.xml -max_socket 1 -r 1 -p 5062 -rp 1000 -trace_err 127.0.0.1
//
// To test registrations (note SIPp returns an error due to no 401 response, if this demo app registers the contact then
// it worked correctly):
//
// $ wget http://tomeko.net/other/sipp/scenarios/REGISTER_client.xml
// $ wget http://tomeko.net/other/sipp/scenarios/REGISTER_SUBSCRIBE_client.csv
// $ sipp 127.0.0.1 -sf REGISTER_client.xml -inf REGISTER_SUBSCRIBE_client.csv -m 1 -trace_msg -trace_err 
//-----------------------------------------------------------------------------


//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: struct SIPAccountBinding
public final class SIPAccountBinding
{
	public SIPAccount SIPAccount;
	public SIPURI RegisteredContact;
	public SIPEndPoint RemoteEndPoint;
	public SIPEndPoint LocalEndPoint;
	public int Expiry;

	public SIPAccountBinding(SIPAccount sipAccount, SIPURI contact, SIPEndPoint remote, SIPEndPoint local, int expiry)
	{
		SIPAccount = sipAccount;
		RegisteredContact = contact;
		RemoteEndPoint = remote;
		LocalEndPoint = local;
		Expiry = expiry;
	}

	public SIPAccountBinding clone()
	{
		SIPAccountBinding varCopy = new SIPAccountBinding();

		varCopy.SIPAccount = this.SIPAccount;
		varCopy.RegisteredContact = this.RegisteredContact;
		varCopy.RemoteEndPoint = this.RemoteEndPoint;
		varCopy.LocalEndPoint = this.LocalEndPoint;
		varCopy.Expiry = this.Expiry;

		return varCopy;
	}
}