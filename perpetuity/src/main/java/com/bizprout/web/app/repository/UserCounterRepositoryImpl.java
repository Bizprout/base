package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.ScreensDTO;
import com.bizprout.web.app.dto.UserCounterDTO;

@Repository
public class UserCounterRepositoryImpl extends AbstractBaseRepository<UserCounterDTO> {

	@Autowired
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	public UserCounterDTO getlastlogindatetime(int userid) {

		logger.info("Inside getlastlogindatetime method......."+this.getClass());

		UserCounterDTO usercounter = null;
		
		try {
			Session session = factory.getCurrentSession();
			
			Criteria cr=session.createCriteria(UserCounterDTO.class)
					.addOrder(Order.desc("logindatetime"))
					.add(Restrictions.eq("userid", userid))
					.setFirstResult(1)
					.setProjection(Projections.projectionList()
							.add(Projections.property("logindatetime"), "logindatetime"))
							.setResultTransformer(Transformers.aliasToBean(UserCounterDTO.class));
				
			usercounter = (UserCounterDTO) cr.list().get(0);
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return usercounter;
	}

}
