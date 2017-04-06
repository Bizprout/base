package com.bizprout.web.app.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.CompanyDTO;

@Repository
@Transactional
public class UserMappingRepositoryImpl extends AbstractBaseRepository<CompanyDTO>{

}
