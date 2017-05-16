package com.bizprout.web.app.repository;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.LoginDTO;
import com.bizprout.web.app.dto.UserDTO;

@Repository
public class LoginRepositoryImpl extends AbstractBaseRepository<LoginDTO> {



}
