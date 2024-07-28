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
import com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailResponseDTO;
import com.bussinesdomain.training.exception.ServiceException;
import com.bussinesdomain.training.utils.DateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignedCourseRegistrationDetailPaginationServiceImpl implements IPaginationCommons<AssignedCourseRegistrationDetailResponseDTO> {

	private final EntityManager entityManager;

	@Override
	public Page<AssignedCourseRegistrationDetailResponseDTO> pagination(PaginationModel pagination) {
		try {

			String sqlCount = "SELECT count(a) " + getFrom().toString()
					+ getFilters(pagination.getFilters()).toString();
			String sqlSelect = getSelect().toString() + getFrom().toString()
					+ getFilters(pagination.getFilters()).toString() + getOrder(pagination.getSorts());

			Query queryCount = entityManager.createQuery(sqlCount);
			Query querySelect = entityManager.createQuery(sqlSelect);

			this.setParams(pagination.getFilters(), queryCount);
			this.setParams(pagination.getFilters(), querySelect);

			Long total = (long) queryCount.getSingleResult();

			querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
			querySelect.setMaxResults(pagination.getRowsPerPage());

			@SuppressWarnings("unchecked")
			List<AssignedCourseRegistrationDetailResponseDTO> lista = querySelect.getResultList();

			PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

			return new PageImpl<>(lista,pageable, total);
		} catch (RuntimeException e) {
			throw new ServiceException("error when generating the pagination " + e.getMessage());
		}
	}

	@Override
	public StringBuilder getSelect() {
		return new StringBuilder("SELECT new com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailResponseDTO(a.idAssignedCourseRegistrationDetail,a.reportDate,a.themeDescription,a.themeDuration,a.themeOfDay, a.idAssignedCourseRegistration.idAssignedCourseRegistration,"
				+ "a.createdAt,a.updatedAt,a.registrationStatus,a.idUser) ");
	}

	@Override
	public StringBuilder getFrom() {
		return new StringBuilder(" FROM AssignedCourseRegistrationDetail a ");
	}

	@Override
	public StringBuilder getFilters(List<Filter> filters) {
		 StringBuilder sql = new StringBuilder("where 1=1 ");

	        for(Filter filtro:filters){
	            if(filtro.getField().equals("idAssignedCourseRegistrationDetail")){
	                sql.append(" AND a.idAssignedCourseRegistrationDetail = :idAssignedCourseRegistrationDetail");
	            }
	            if(filtro.getField().equals("reportDate")){
	            	sql.append(" AND DATE(a.reportDate) = :reportDate ");
	            }
	            if(filtro.getField().equals("themeDescription")){
	                sql.append(" AND UPPER(a.themeDescription) LIKE UPPER(:themeDescription) ");
	            }
	            if(filtro.getField().equals("themeDuration")){
	                sql.append(" AND a.themeDuration = :themeDuration");
	            }
	            if(filtro.getField().equals("themeOfDay")){
	                sql.append(" AND UPPER(a.themeOfDay) LIKE UPPER(:themeOfDay) ");
	            }
	            if(filtro.getField().equals("idAssignedCourseRegistration")){
	                sql.append(" AND a.idAssignedCourseRegistration.idAssignedCourseRegistration = :idAssignedCourseRegistration");
	            }
	            if(filtro.getField().equals("createdAt")){
	            	sql.append(" AND DATE(a.createdAt) = :createdAt");
	            }
	            if(filtro.getField().equals("updatedAt")){
	            	sql.append(" AND DATE(a.updatedAt) = :updatedAt");
	            }
	            if(filtro.getField().equals("registrationStatus")){
	                sql.append(" AND UPPER(a.registrationStatus) LIKE UPPER(:registrationStatus) ");
	            }
	            if(filtro.getField().equals("idUser")){
	                sql.append(" AND a.idUser = :idUser");
	            }
	        }

	        return sql;
	}

	@Override
	public Query setParams(List<Filter> filters, Query query) {
		 for(Filter filtro:filters){
	            if(filtro.getField().equals("idAssignedCourseRegistrationDetail")){
	                query.setParameter("idAssignedCourseRegistrationDetail",filtro.getValue() );
	            }
	            if(filtro.getField().equals("reportDate")){
	            	LocalDate dateAdmission = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
	            	query.setParameter("reportDate", dateAdmission);
	            }
	            if(filtro.getField().equals("themeDescription")){
	                query.setParameter("themeDescription","%"+filtro.getValue()+"%");
	            }
	            if(filtro.getField().equals("themeDuration")){
	                query.setParameter("themeDuration",filtro.getValue() );
	            }
	            if(filtro.getField().equals("themeOfDay")){
	                query.setParameter("themeOfDay","%"+filtro.getValue()+"%");
	            }
	            if(filtro.getField().equals("idAssignedCourseRegistration")){
	                query.setParameter("idAssignedCourseRegistration",filtro.getValue() );
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
                if(sort.getColName().equals("idAssignedCourseRegistrationDetail")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " idAssignedCourseRegistrationDetail " + sort.getSort() );
                    flagMore = true;
                }
                
                if(sort.getColName().equals("reportDate")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " reportDate " + sort.getSort() );
                    flagMore = true;
                }
                
                if(sort.getColName().equals("themeDescription")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " themeDescription " + sort.getSort() );
                    flagMore = true;
                }
                
                if(sort.getColName().equals("themeDuration")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " themeDuration " + sort.getSort() );
                    flagMore = true;
                }
                
                if(sort.getColName().equals("themeOfDay")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " themeOfDay " + sort.getSort() );
                    flagMore = true;
                }
                
                if(sort.getColName().equals("idAssignedCourseRegistration")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " idAssignedCourseRegistration " + sort.getSort() );
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
