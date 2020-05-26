package com.nitin.microservices2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch(response.status()) {
		case 400:
			//Do Something
			break;
		case 504:
			return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
		case 404:
		{
			return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
		}
		default:
			return new Exception(response.reason());
		}
		return null;
	}
}
