package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.UserDTO;

@Repository
@Transactional
public class UserRepositoryImpl extends AbstractBaseRepository<UserDTO> {

	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<UserDTO> getusers() {
		logger.info("Inside getusers method.......");

		Session session = factory.getCurrentSession();

		List<UserDTO> user = session.createQuery("from UserDTO").list();

		return user;
	}

}
