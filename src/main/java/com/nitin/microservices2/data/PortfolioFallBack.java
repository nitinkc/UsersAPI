package com.nitin.microservices2.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nitin.microservices2.model.SharesPortfolioResponseModel;

@Component
public class PortfolioFallBack implements PortfolioFeignClient{

	@Override
	public List<SharesPortfolioResponseModel> getPortfolio(String email) {
		SharesPortfolioResponseModel dummy = new SharesPortfolioResponseModel();
		dummy.setEmail(email);
		dummy.setPortfolio("Dummy Portfolio");
		dummy.setId("dummy ID");
		List<SharesPortfolioResponseModel> ret = new ArrayList<>();
		ret.add(dummy);
		return ret;
	}

}
