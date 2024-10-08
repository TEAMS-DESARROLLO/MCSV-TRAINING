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
import com.bussinesdomain.training.dto.AssignedCourseRegistrationResponseDTO;
import com.bussinesdomain.training.exception.ServiceException;
import com.bussinesdomain.training.utils.DateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignedCourseRegistrationPaginationServiceImpl implements IPaginationCommons<AssignedCourseRegistrationResponseDTO> {

	private final EntityManager entityManager;

	@Override
	public Page<AssignedCourseRegistrationResponseDTO> pagination(PaginationModel pagination) {
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
			List<AssignedCourseRegistrationResponseDTO> lista = querySelect.getResultList();

			PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

			return new PageImpl<>(lista, pageable,total);
		} catch (RuntimeException e) {
			throw new ServiceException("error when generating the pagination " + e.getMessage() , e.getCause() );
		}
	}

	@Override
	public StringBuilder getSelect() {
		return new StringBuilder("SELECT new com.bussinesdomain.training.dto.AssignedCourseRegistrationResponseDTO(a.idAssignedCourseRegistration,a.advancePercentage,a.durationCourse,a.idUnitMeasure,a.nameCourse,a.observation,a.startDate,a.urlCourse,a.idRegisterFollow.idRegisterFollow"
				+ ",a.createdAt,a.updatedAt,a.registrationStatus,a.idUser ) ");
	}

	@Override
	public StringBuilder getFrom() {
		return new StringBuilder(" FROM AssignedCourseRegistration a ");
	}

	@Override
	public StringBuilder getFilters(List<Filter> filters) {
		StringBuilder sql = new StringBuilder("where 1=1 ");

        for(Filter filtro:filters){
            if(filtro.getField().equals("idAssignedCourseRegistration")){
                sql.append(" AND a.idAssignedCourseRegistration = :idAssignedCourseRegistration");
            }
            if(filtro.getField().equals("advancePercentage")){
                sql.append(" AND a.advancePercentage = :advancePercentage");
            }
            if(filtro.getField().equals("durationCourse")){
                sql.append(" AND a.durationCourse = :durationCourse");
            }
            if(filtro.getField().equals("idUnitMeasure")){
            	sql.append(" AND a.idUnitMeasure = :idUnitMeasure");
            }
            if(filtro.getField().equals("nameCourse")){
                sql.append(" AND UPPER(a.nameCourse) LIKE UPPER(:nameCourse) ");
            }
            if(filtro.getField().equals("observation")){
            	sql.append(" AND UPPER(a.observation) LIKE UPPER(:observation) ");
            }
            if(filtro.getField().equals("startDate")){
            	sql.append(" AND DATE(a.startDate) = :startDate ");
            }
            if(filtro.getField().equals("urlCourse")){
            	sql.append(" AND UPPER(a.urlCourse) LIKE UPPER(:urlCourse) ");
            }
            if(filtro.getField().equals("idRegisterFollow")){
            	sql.append(" AND a.idRegisterFollow.idRegisterFollow = :idRegisterFollow");
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
	            if(filtro.getField().equals("idAssignedCourseRegistration")){
	                query.setParameter("idAssignedCourseRegistration",filtro.getValue() );
	            }
	            if(filtro.getField().equals("advancePercentage")){
	                query.setParameter("advancePercentage",filtro.getValue() );
	            }
	            if(filtro.getField().equals("durationCourse")){
	            	query.setParameter("durationCourse",filtro.getValue() );
	            }
	            if(filtro.getField().equals("idUnitMeasure")){
	            	query.setParameter("idUnitMeasure",filtro.getValue() );
	            }
	            if(filtro.getField().equals("nameCourse")){
	                query.setParameter("nameCourse","%"+filtro.getValue()+"%");
	            }
	            if(filtro.getField().equals("observation")){
	            	query.setParameter("observation","%"+filtro.getValue()+"%");
	            }
	            if(filtro.getField().equals("startDate")){
	            	LocalDate dateAdmission = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
	            	query.setParameter("startDate", dateAdmission);
	            }
	            if(filtro.getField().equals("urlCourse")){
	            	query.setParameter("urlCourse","%"+filtro.getValue()+"%");
	            }
	            if(filtro.getField().equals("idRegisterFollow")){
	            	query.setParameter("idRegisterFollow",filtro.getValue() );
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
                if(sort.getColName().equals("idAssignedCourseRegistration")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " idAssignedCourseRegistration " + sort.getSort() );
                    flagMore = true;
                }
                if(sort.getColName().equals("advancePercentage")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " advancePercentage " + sort.getSort() );
                    flagMore = true;
                }
                if(sort.getColName().equals("durationCourse")){
                	if(flagMore)
                		sql.append(", ");
                	
                	sql.append( " durationCourse " + sort.getSort() );
                	flagMore = true;
                }
                if(sort.getColName().equals("idUnitMeasure")){
                	if(flagMore)
                		sql.append(", ");
                	
                	sql.append( " idUnitMeasure " + sort.getSort() );
                	flagMore = true;
                }
                
                if(sort.getColName().equals("nameCourse")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " nameCourse " + sort.getSort() );
                    flagMore = true;
                }
                if(sort.getColName().equals("observation")){
                	if(flagMore)
                		sql.append(", ");
                	
                	sql.append( " observation " + sort.getSort() );
                	flagMore = true;
                }
                if(sort.getColName().equals("startDate")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " startDate " + sort.getSort() );
                    flagMore = true;
                }
                if(sort.getColName().equals("urlCourse")){
                	if(flagMore)
                		sql.append(", ");
                	
                	sql.append( " urlCourse " + sort.getSort() );
                	flagMore = true;
                }
                if(sort.getColName().equals("idRegisterFollow")){
                	if(flagMore)
                		sql.append(", ");
                	
                	sql.append( " idRegisterFollow " + sort.getSort() );
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
