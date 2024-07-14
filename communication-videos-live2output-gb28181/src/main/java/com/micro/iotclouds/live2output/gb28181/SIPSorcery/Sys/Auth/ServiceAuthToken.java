package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys.Auth;

// ============================================================================
// FileName: ServiceAuthToken.cs
//
// Description:
// Represents a security token that is passed to web or WCF service.
//
// Author(s):
// Aaron Clauson
//
// History:
// 09 Jun 2010	Aaron Clauson	Created.
//
// License: 
// BSD 3-Clause "New" or "Revised" License, see included LICENSE.md file.
//


public class ServiceAuthToken
{
	public static final String AUTH_TOKEN_KEY = "authid";
	public static final String API_KEY = "apikey";
	public static final String COOKIES_KEY = "Cookie";

	public static String GetAuthId()
	{
		return GetToken(AUTH_TOKEN_KEY);
	}

	public static String GetAPIKey()
	{
		return GetToken(API_KEY);
	}

	private static String GetToken(String tokenName)
	{
		String token = null;

		//if (OperationContext.Current != null)
		//{
		//    SIPSorcerySecurityHeader securityheader = SIPSorcerySecurityHeader.ParseHeader(OperationContext.Current);
		//    if (securityheader != null)
		//    {
		//        token = (tokenName == AUTH_TOKEN_KEY) ? securityheader.AuthID  : securityheader.APIKey;
		//    }
		//}

		//// HTTP Context is available for ?? binding.
		//if (token.IsNullOrBlank() && HttpContext.Current != null)
		//{
		//    // If running in IIS check for a cookie.
		//    HttpCookie authIdCookie = HttpContext.Current.Request.Cookies[tokenName];
		//    if (authIdCookie != null)
		//    {
		//        //logger.Debug("authid cookie found: " + authIdCookie.Value + ".");
		//        token = authIdCookie.Value;
		//    }
		//    else
		//    {
		//        // Not in the cookie so check the request parameters.
		//        token = HttpContext.Current.Request.Params[tokenName];
		//    }
		//}

		//// No HTTP context available so try and get a cookie value from the operation context.
		//if (token.IsNullOrBlank() && OperationContext.Current != null && OperationContext.Current.IncomingMessageProperties[HttpRequestMessageProperty.Name] != null)
		//{
		//    HttpRequestMessageProperty httpRequest = (HttpRequestMessageProperty)OperationContext.Current.IncomingMessageProperties[HttpRequestMessageProperty.Name];
		//    // Check for the header in a case insensitive way. Allows matches on authid, Authid etc.
		//    if (httpRequest.Headers.AllKeys.Contains(tokenName, StringComparer.InvariantCultureIgnoreCase))
		//    {
		//        string authIDHeader = httpRequest.Headers.AllKeys.First(h => { return String.Equals(h, tokenName, StringComparison.InvariantCultureIgnoreCase); });
		//        token = httpRequest.Headers[authIDHeader];
		//        //logger.Debug("authid HTTP header found: " + authId + ".");
		//    }

		//    if (token == null && httpRequest.Headers.AllKeys.Contains(COOKIES_KEY, StringComparer.InvariantCultureIgnoreCase))
		//    {
		//        Match authIDMatch = Regex.Match(httpRequest.Headers[COOKIES_KEY], tokenName + @"=(?<token>.+)");
		//        if (authIDMatch.Success)
		//        {
		//            token = authIDMatch.Result("${token}");
		//            //logger.Debug("authid HTTP cookie found: " + authId + ".");
		//        }
		//    }

		//    if (token == null && httpRequest.QueryString.NotNullOrBlank())
		//    {
		//        NameValueCollection qscoll = HttpUtility.ParseQueryString(httpRequest.QueryString);
		//        if (qscoll[AUTH_TOKEN_KEY].NotNullOrBlank())
		//        {
		//            token = qscoll[AUTH_TOKEN_KEY];
		//        }
		//        else if (qscoll[API_KEY].NotNullOrBlank())
		//        {
		//            token = qscoll[API_KEY];
		//        }
		//    }
		//}

		return token;
	}

}