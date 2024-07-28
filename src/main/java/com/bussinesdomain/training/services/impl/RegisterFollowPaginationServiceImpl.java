package com.bussinesdomain.training.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bussinesdomain.training.commons.Filter;
import com.bussinesdomain.training.commons.IPaginationCommons;
import com.bussinesdomain.training.commons.PaginationModel;
import com.bussinesdomain.training.commons.SortModel;
import com.bussinesdomain.training.dto.RegisterFollowResponseDTO;
import com.bussinesdomain.training.exception.ServiceException;
import com.bussinesdomain.training.utils.DateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterFollowPaginationServiceImpl implements IPaginationCommons<RegisterFollowResponseDTO>{

	private final EntityManager entityManager;
	
	@Override
	public Page<RegisterFollowResponseDTO> pagination(PaginationModel pagination) {
		try {

			String sqlCount = "SELECT count(f) " + getFrom().toString() + getFilters(pagination.getFilters()).toString();
			String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(pagination.getFilters()).toString() + getOrder(pagination.getSorts());

			Query queryCount = entityManager.createQuery(sqlCount);
			Query querySelect = entityManager.createQuery(sqlSelect);

			this.setParams(pagination.getFilters(), queryCount);
			this.setParams(pagination.getFilters(), querySelect);

			Long total = (long) queryCount.getSingleResult();

			querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
			querySelect.setMaxResults(pagination.getRowsPerPage());

			@SuppressWarnings("unchecked")
			List<RegisterFollowResponseDTO> lista = querySelect.getResultList();

			PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

			return new PageImpl<>(lista, pageable, total);
		} catch (RuntimeException e) {
			throw new ServiceException("error when generating the pagination :" + e.getMessage());
		}
	}

	@Override
	public StringBuilder getSelect() {
		return new StringBuilder("SELECT new com.bussinesdomain.training.dto.RegisterFollowResponseDTO(f.idRegisterFollow,f.dateStartFollow,f.observation,f.idRegister.idRegister"
				+ ",f.createdAt,f.updatedAt,f.registrationStatus,f.idUser) ");
	}

	@Override
	public StringBuilder getFrom() {
		return new StringBuilder(" FROM RegisterFollow f  ");
	}

	@Override
	public StringBuilder getFilters(List<Filter> filters) {
		StringBuilder sql = new StringBuilder("where 1=1 ");

        for(Filter filtro:filters){
            if(filtro.getField().equals("idRegisterFollow")){
                sql.append(" AND f.idRegisterFollow = :idRegisterFollow");
            }
            if(filtro.getField().equals("dateStartFollow")){
                sql.append(" AND DATE(f.dateStartFollow) = :dateStartFollow ");
            }            
            if(filtro.getField().equals("observation")){
                sql.append(" AND UPPER(f.observation) LIKE UPPER(:observation) ");
            }
            if(filtro.getField().equals("idRegister")){
                sql.append(" AND f.idRegister.idRegister = :idRegister");
            }
            if(filtro.getField().equals("createdAt")){
            	sql.append(" AND DATE(f.createdAt) = :createdAt");
            }
            if(filtro.getField().equals("updatedAt")){
            	sql.append(" AND DATE(f.updatedAt) = :updatedAt");
            }
            if(filtro.getField().equals("registrationStatus")){
                sql.append(" AND UPPER(f.registrationStatus) LIKE UPPER(:registrationStatus) ");
            }
            if(filtro.getField().equals("idUser")){
                sql.append(" AND f.idUser = :idUser");
            }
        }

        return sql;
	}

	@Override
	public Query setParams(List<Filter> filters, Query query) {
		for(Filter filtro:filters){
            if(filtro.getField().equals("idRegisterFollow")){
                query.setParameter("idRegisterFollow",filtro.getValue() );
            }
            if(filtro.getField().equals("dateStartFollow")){
            	LocalDate dateAdmission = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
            	query.setParameter("dateStartFollow", dateAdmission);
            }
            if(filtro.getField().equals("observation")){
            	query.setParameter("observation","%"+filtro.getValue()+"%");
            }
            if(filtro.getField().equals("idRegister")){
                query.setParameter("idRegister",filtro.getValue() );
            }
            if(filtro.getField().equals("createdAt")){
            	LocalDate createdAt = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
            	query.setParameter("createdAt", createdAt);
            }
            if(filtro.getField().equals("updatedAt")){
            	LocalDate updatedAt = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
            	query.setParameter("updatedAt", updatedAt);
            }
            if(filtro.getField().equals("registrationStatus")){
            	query.setParameter("registrationStatus","%"+filtro.getValue()+"%");
            }
            if(filtro.getField().equals("idUser")){
                query.setParameter("idUser",filtro.getValue() );
            }
        }
        return query;
	}

	@Override
	public StringBuilder getOrder(List<SortModel> sorts) {
		 boolean flagMore = false;
	        StringBuilder sql = new StringBuilder("");
	        if(!sorts.isEmpty()){
	            sql.append(" ORDER BY ");

	            for(SortModel sort:sorts){
	                if(sort.getColName().equals("idRegisterFollow")){
	                    if(flagMore)
	                        sql.append(", ");

	                    sql.append( " idRegisterFollow " + sort.getSort() );
	                    flagMore = true;
	                }

	                if(sort.getColName().equals("dateStartFollow")){
	                    if(flagMore)
	                        sql.append(", ");
	                    sql.append( " dateStartFollow " + sort.getSort() );
	                    flagMore = true;
	                }
	                if(sort.getColName().equals("observation")){
	                    if(flagMore)
	                        sql.append(", ");

	                    sql.append( " observation " + sort.getSort() );
	                    flagMore = true;
	                }
	                if(sort.getColName().equals("idRegister")){
	                	if(flagMore)
	                		sql.append(", ");
	                	
	                	sql.append( " idRegister " + sort.getSort() );
	                	flagMore = true;
	                }
	                if(sort.getColName().equals("createdAt")){
	                    if(flagMore)
	                        sql.append(", ");
	                    sql.append( " createdAt " + sort.getSort() );
	                    flagMore = true;
	                }
	                if(sort.getColName().equals("updatedAt")){
	                	if(flagMore)
	                		sql.append(", ");
	                	sql.append( " updatedAt " + sort.getSort() );
	                	flagMore = true;
	                }
	                if(sort.getColName().equals("registrationStatus")){
	                    if(flagMore)
	                        sql.append(", ");

	                    sql.append( " registrationStatus " + sort.getSort() );
	                    flagMore = true;
	                }
	                if(sort.getColName().equals("idUser")){
	                    if(flagMore)
	                        sql.append(", ");

	                    sql.append( " idUser " + sort.getSort() );
	                    flagMore = true;
	                }
	           }
	        }
	         return sql;
	}

	
	
}
