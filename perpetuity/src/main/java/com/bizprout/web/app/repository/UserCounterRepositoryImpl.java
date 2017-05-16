package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.UserCounterDTO;

@Repository
@Transactional
public class UserCounterRepositoryImpl extends AbstractBaseRepository<UserCounterDTO> {

	@Autowired
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	public List<UserCounterDTO> getlastlogindatetime(int userid) {

		logger.info("Inside getlastlogindatetime method.......");

		Session session = factory.getCurrentSession();

		Criteria cr = session.createCriteria(UserCounterDTO.class)
				.add(Restrictions.eq("userid", userid))
				.addOrder(Order.desc("logindatetime"))
				.setFirstResult(1)
				.setMaxResults(1)
				.setProjection(Projections.projectionList()
						.add(Projections.property("logindatetime"), "logindatetime"))
						.setResultTransformer(Transformers.aliasToBean(UserCounterDTO.class));

		List<UserCounterDTO> usercounter=cr.list();

		return usercounter;
	}

}
