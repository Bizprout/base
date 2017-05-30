package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

	@SuppressWarnings("unchecked")
	public List<ClientDTO> getClients()
	{
		List<ClientDTO> user=null;
		try {
			logger.info("Inside getClients method......."+this.getClass());

			Session session = getSession();

			user=session.createQuery("from ClientDTO").list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<ClientDTO> getClientNames()
	{
		logger.info("Inside getClientNames method......."+this.getClass());

		List<ClientDTO> user=null;
		try {
			Session session = getSession();

			Criteria cr = session.createCriteria(ClientDTO.class)
					.setProjection(Projections.projectionList()
							.add(Projections.property("clientId"), "clientId")
							.add(Projections.property("clientName"), "clientName"))
							.setResultTransformer(Transformers.aliasToBean(ClientDTO.class));

			user = cr.list();
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public List<ClientDTO> getClientIdName()
	{
		logger.info("Inside getClientIdName method......."+this.getClass());

		List<ClientDTO> user=null;
		try {
			Session session = getSession();

			Criteria cr = session.createCriteria(ClientDTO.class)
					.add(Restrictions.eq("status", "Active"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("clientId"), "clientId")
							.add(Projections.property("clientName"), "clientName"))
							.setResultTransformer(Transformers.aliasToBean(ClientDTO.class));

			user = cr.list();
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return user;
	}

	public ClientDTO getClientData(int clientid) {
		Session session;
		Query qry = null;
		try {
			logger.info("Inside getClientData method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("from ClientDTO where clientId=:clientid");

			qry.setParameter("clientid",clientid);
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return (ClientDTO) qry.uniqueResult();
	}

	public int updateClient(ClientDTO clientdto) {

		int result = 0;
		Session session = null;

		try {
			logger.info("Inside updateClient method......."+this.getClass());

			session = getSession();

			Query qry=session.createQuery("UPDATE ClientDTO set clientName=:editclientname, contactPerson=:contactper, contactEmail=:email, contactTelPhone=:phone, status=:stat WHERE clientId=:clientid");

			qry.setParameter("editclientname", clientdto.getClientName());
			qry.setParameter("contactper",clientdto.getContactPerson());
			qry.setParameter("email", clientdto.getContactEmail());
			qry.setParameter("phone", clientdto.getContactTelPhone());
			qry.setParameter("stat", clientdto.getStatus());
			qry.setParameter("clientid", clientdto.getClientId());

			result= qry.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}
}
