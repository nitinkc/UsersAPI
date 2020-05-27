package com.nitin.microservices2.data;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class PortfolioFallBackFactory implements FallbackFactory<PortfolioFeignClient> {

	@Override
	public PortfolioFeignClient create(Throwable cause) {
		return new PortfolioFallBackService(cause);
	}

}
