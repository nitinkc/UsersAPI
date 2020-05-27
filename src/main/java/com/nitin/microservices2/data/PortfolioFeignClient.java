package com.nitin.microservices2.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nitin.microservices2.model.SharesPortfolioResponseModel;

// To Call a Microservice that is not registered with eureka
//@FeignClient(name="portfolio-ws",url="http://localhost:8070/")
// To Call with Fall back mechanism
//@FeignClient(name="portfolio-ws", fallback = PortfolioFallBack.class)
@FeignClient(name="portfolio-ws", fallbackFactory = PortfolioFallBackFactory.class)
public interface PortfolioFeignClient {
	
	@GetMapping("/portfolio/{email}")
	public List<SharesPortfolioResponseModel> getPortfolio(@PathVariable String email);

}
