package com.nitin.microservices2.shared;

import java.util.List;

import com.nitin.microservices2.model.SharesPortfolioResponseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPortfolioDTO {
	private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<SharesPortfolioResponseModel> portfolios;
}
