package org.shop.service.impl;

import org.shop.dao.LoginDao;
import org.shop.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;

	@Override
	public boolean isLoginSuccessful(String id, String password) {
		return loginDao.isLoginSuccessful(id, password);
	}


}
