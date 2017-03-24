package com.bizprout.web.app.repository;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.LoginDTO;

@Repository
public class LoginRepositoryImpl extends AbstractBaseRepository<LoginDTO> {

	@Autowired
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());	

	public LoginDTO getLoginUser(String username,String password) {


		logger.info("Inside getLoginUser method.......");

		Session session = factory.getCurrentSession();

		Query qry=session.createQuery("from LoginDTO where UserName=:username and Password=:pwd and ActiveStatus=0");

		qry.setParameter("username",username);
		qry.setParameter("pwd",password);
		return (LoginDTO) qry.uniqueResult();


	}

}
