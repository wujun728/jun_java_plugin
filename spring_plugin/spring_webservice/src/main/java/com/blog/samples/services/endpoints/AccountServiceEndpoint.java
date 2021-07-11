package com.blog.samples.services.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.blog.samples.services.AccountService;
import com.blog.samples.webservices.Account;
import com.blog.samples.webservices.accountservice.AccountDetailsRequest;
import com.blog.samples.webservices.accountservice.AccountDetailsResponse;

/**
 * The Class AccountService.
 */
@Endpoint
public class AccountServiceEndpoint
{
	private static final String TARGET_NAMESPACE = "http://com/blog/samples/webservices/accountservice";

	@Autowired
	private AccountService accountService_i;

	/**
	 * Gets the account details.
	 *
	 * @param accountNumber the account number
	 * @return the account details
	 */
	@PayloadRoot(localPart = "AccountDetailsRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload AccountDetailsResponse getAccountDetails(@RequestPayload AccountDetailsRequest request)
	{
		AccountDetailsResponse response = new AccountDetailsResponse();

		/* call Spring injected service implementation to retrieve account data */
		Account account = accountService_i.getAccountDetails(request.getAccountNumber());
		response.setAccountDetails(account);
		return response;
	}

	public void setAccountService(AccountService accountService_p)
	{
		this.accountService_i = accountService_p;
	}
}