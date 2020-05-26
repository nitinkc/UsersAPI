package com.nitin.microservices2.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nitin.microservices2.model.SharesPortfolioResponseModel;

//@FeignClient(name="portfolio-ws",url="http://localhost:8070/")
@FeignClient(name="portfolio-ws", fallback = PortfolioFallBack.class)
public interface PortfolioFeignClient {
	
	@GetMapping("/portfolio/{email}")
	public List<SharesPortfolioResponseModel> getPortfolio(@PathVariable String email);

}
