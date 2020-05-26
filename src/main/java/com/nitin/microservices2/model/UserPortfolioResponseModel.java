package com.nitin.microservices2.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPortfolioResponseModel {
	private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<SharesPortfolioResponseModel> portfolios;
}
