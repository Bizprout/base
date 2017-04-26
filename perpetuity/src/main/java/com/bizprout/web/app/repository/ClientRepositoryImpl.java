package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.ClientDTO;


@Repository
public class ClientRepositoryImpl extends AbstractBaseRepository<ClientDTO> {

	@Autowired  
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	public List<ClientDTO> getClients()
	{
		logger.info("Inside getClients method.......");

		Session session = factory.getCurrentSession();

		List<ClientDTO> user=session.createQuery("from ClientDTO").list();

		return user;
	}
	
	public List<ClientDTO> getClientNames()
	{
		logger.info("Inside getClientNames method.......");

		Session session = factory.getCurrentSession();

		//Query qry=session.createQuery("select clientId, clientName from ClientDTO");

		Criteria cr = session.createCriteria(ClientDTO.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("clientId"), "clientId")
						.add(Projections.property("clientName"), "clientName"))
						.setResultTransformer(Transformers.aliasToBean(ClientDTO.class));

		List<ClientDTO> user=cr.list();

		return user;
	}

	public List<ClientDTO> getClientIdName()
	{
		logger.info("Inside getClientIdName method.......");

		Session session = factory.getCurrentSession();

		//Query qry=session.createQuery("select clientId, clientName from ClientDTO");

		Criteria cr = session.createCriteria(ClientDTO.class)
				.add(Restrictions.eq("status", "Active"))
				.setProjection(Projections.projectionList()
						.add(Projections.property("clientId"), "clientId")
						.add(Projections.property("clientName"), "clientName"))
						.setResultTransformer(Transformers.aliasToBean(ClientDTO.class));

		List<ClientDTO> user=cr.list();

		return user;
	}

	public ClientDTO getClientData(int clientid) {
		Session session;
		Query qry = null;
		try {
			logger.info("Inside getClientData method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("from ClientDTO where clientId=:clientid");

			qry.setParameter("clientid",clientid);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (ClientDTO) qry.uniqueResult();
	}

	public int updateClient(ClientDTO clientdto) {

		int result = 0;
		Session session = null;
		Transaction tx = null;

		try {
			logger.info("Inside updateClient method.......");

			session = factory.getCurrentSession();

			tx=session.beginTransaction();

			Query qry=session.createQuery("UPDATE ClientDTO set contactPerson=:contactper, contactEmail=:email, contactTelPhone=:phone, status=:stat WHERE clientId=:clientid");

			qry.setParameter("contactper",clientdto.getContactPerson());
			qry.setParameter("email", clientdto.getContactEmail());
			qry.setParameter("phone", clientdto.getContactTelPhone());
			qry.setParameter("stat", clientdto.getStatus());
			qry.setParameter("clientid", clientdto.getClientId());

			result= qry.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			session.close();
		}
		return result;
	}
}
