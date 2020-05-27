package com.nitin.microservices2.data;

import java.util.ArrayList;
import java.util.List;
import java.lang.Throwable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nitin.microservices2.model.SharesPortfolioResponseModel;

import feign.FeignException;

public class PortfolioFallBackService implements PortfolioFeignClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Throwable cause;
	
	public PortfolioFallBackService(Throwable cause) {
		this.cause = cause;
	}
	
	//Returning the dummy fallback method along with exception
	@Override
	public List<SharesPortfolioResponseModel> getPortfolio(String email) {
		
		SharesPortfolioResponseModel dummy = new SharesPortfolioResponseModel();
		dummy.setEmail(email);
		dummy.setPortfolio("Dummy Portfolio");
		dummy.setId("dummy ID");
		List<SharesPortfolioResponseModel> ret = new ArrayList<>();
		ret.add(dummy);
		
		if(cause instanceof FeignException && ((FeignException) cause).status() == 400) {
			logger.error("404 ERROR on email : " +email + "\n With Error MEssage : " + cause.getLocalizedMessage());
		}else {
			logger.error("Other Error took place> " + cause.getLocalizedMessage());
		}
		
		return ret;
	}
}