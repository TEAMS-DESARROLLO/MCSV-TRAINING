package com.bussinesdomain.training.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bussinesdomain.training.client.master.MasterClient;
import com.bussinesdomain.training.client.master.dto.CollaboratorResponseDTO;
import com.bussinesdomain.training.client.master.dto.LeaderResponseDTO;
import com.bussinesdomain.training.client.master.dto.RegionResponseDTO;
import com.bussinesdomain.training.commons.Filter;
import com.bussinesdomain.training.commons.IPaginationCommons;
import com.bussinesdomain.training.commons.PaginationModel;
import com.bussinesdomain.training.commons.SortModel;
import com.bussinesdomain.training.config.ConfigToken;
import com.bussinesdomain.training.dto.RegisterResponseDTO;

import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageImpl;
import com.bussinesdomain.training.exception.ServiceException;
import com.bussinesdomain.training.utils.DateUtil;

@Service
@RequiredArgsConstructor
public class RegisterPaginationServiceImpl implements IPaginationCommons<RegisterResponseDTO> {

	private final EntityManager entityManager;
	private final MasterClient masterClient;

	@Override
	public Page<RegisterResponseDTO> pagination(PaginationModel pagination) {
		try {

			String sqlCount = "SELECT count(r) " + getFrom().toString() + getFilters(pagination.getFilters()).toString();
			String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(pagination.getFilters()).toString() + getOrder(pagination.getSorts());

			Query queryCount = entityManager.createQuery(sqlCount);
			Query querySelect = entityManager.createQuery(sqlSelect);

			this.setParams(pagination.getFilters(), queryCount);
			this.setParams(pagination.getFilters(), querySelect);

			Long total = (long) queryCount.getSingleResult();

			querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
			querySelect.setMaxResults(pagination.getRowsPerPage());

			@SuppressWarnings("unchecked")
			List<RegisterResponseDTO> lista = querySelect.getResultList();
			assignCollaboratorInList(lista);
			assignLeaderInList(lista);
			assignRegionInList(lista);

			PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

			return new PageImpl<>(lista, pageable, total);
		} catch (RuntimeException e) {
			throw new ServiceException("error when generating the pagination " + e.getMessage());
		}
	}

	private void assignCollaboratorInList(List<RegisterResponseDTO> lista){
		List<Long> idCollaboratorsLst = lista.stream().map(x -> x.getIdCollaborator()).distinct().collect(Collectors.toList());
		List<CollaboratorResponseDTO> collaborators = this.masterClient.findCollaboratorsByListId(ConfigToken.tokenBack, idCollaboratorsLst).getBody();
		lista.forEach(x ->
		{
			@SuppressWarnings("null")
			Optional<CollaboratorResponseDTO> optCollaborator = collaborators.stream().filter(y->y.getIdCollaborator().equals(x.getIdCollaborator())).findAny();
			if(optCollaborator.isPresent()){
				x.setCollaboratorNames(optCollaborator.get().getNames());
				x.setCollaboratorLastNames(optCollaborator.get().getLastName());
			}
		});
	}

	private void assignLeaderInList(List<RegisterResponseDTO> lista){
		List<Long> idLeadersLst = lista.stream().map(x -> x.getIdLeader()).distinct().collect(Collectors.toList());
		List<LeaderResponseDTO> leaders = this.masterClient.findLeadersByListId(ConfigToken.tokenBack, idLeadersLst).getBody();
		lista.forEach(x ->
		{
			@SuppressWarnings("null")
			Optional<LeaderResponseDTO> optLeader = leaders.stream().filter(y->y.getIdLeader().equals(x.getIdLeader())).findAny();
			if(optLeader.isPresent()){
				x.setLeaderNames(optLeader.get().getNames());
			}
		});
	}

	private void assignRegionInList(List<RegisterResponseDTO> lista){
		List<Long> idRegionsLst = lista.stream().map(x -> x.getIdLeaderRegion()).distinct().collect(Collectors.toList());
		List<RegionResponseDTO> regions = this.masterClient.findRegionsByListId(ConfigToken.tokenBack, idRegionsLst).getBody();
		lista.forEach(x ->
		{
			@SuppressWarnings("null")
			Optional<RegionResponseDTO> optRegion = regions.stream().filter(y->y.getIdRegion().equals(x.getIdLeaderRegion())).findAny();
			if(optRegion.isPresent()){
				x.setLeaderRegionNames(optRegion.get().getDescription());
			}
		});
	}

	@Override
	public StringBuilder getSelect() {
		return new StringBuilder("SELECT new com.bussinesdomain.training.dto.RegisterResponseDTO(r.idRegister,r.dateAdmission,r.idCollaborator,r.idLeader,r.idLeaderRegion"
				+ ",r.createdAt,r.updatedAt,r.registrationStatus,r.idUser,\"\",\"\",\"\",\"\") ");
	}

	@Override
	public StringBuilder getFrom() {
		return new StringBuilder(" FROM Register r  ");
	}

	@Override
	public StringBuilder getFilters(List<Filter> filters) {
		StringBuilder sql = new StringBuilder("where 1=1 ");

        for(Filter filtro:filters){
            if(filtro.getField().equals("idRegister")){
                sql.append(" AND r.idRegister = :idRegister");
            }
            if(filtro.getField().equals("dateAdmission")){
                sql.append(" AND r.dateAdmission = :dateAdmission ");
            }            
            if(filtro.getField().equals("idCollaborator")){
                sql.append(" AND r.idCollaborator = :idCollaborator ");
            }
            if(filtro.getField().equals("idLeader")){
                sql.append(" AND r.idLeader = :idLeader");
            }
            if(filtro.getField().equals("idLeaderRegion")){
            	sql.append(" AND r.idLeaderRegion = :idLeaderRegion");
            }
            if(filtro.getField().equals("createdAt")){
            	sql.append(" AND DATE(r.createdAt) = :createdAt");
            }
            if(filtro.getField().equals("updatedAt")){
            	sql.append(" AND DATE(r.updatedAt) = :updatedAt");
            }
            if(filtro.getField().equals("registrationStatus")){
                sql.append(" AND UPPER(r.registrationStatus) LIKE UPPER(:registrationStatus) ");
            }
            if(filtro.getField().equals("idUser")){
            	sql.append(" AND r.idUser = :idUser");
            }
            
        }

        return sql;
	}

	@Override
	public Query setParams(List<Filter> filters, Query query) {
		 for(Filter filtro:filters){
	            if(filtro.getField().equals("idRegister")){
	                query.setParameter("idRegister",filtro.getValue() );
	            }
	            if(filtro.getField().equals("dateAdmission")){
	                query.setParameter("dateAdmission",filtro.getValue());
	            }
	            if(filtro.getField().equals("idCollaborator")){
	                query.setParameter("idCollaborator",filtro.getValue() );
	            }
	            if(filtro.getField().equals("idLeader")){
	                query.setParameter("idLeader",filtro.getValue() );
	            }
	            if(filtro.getField().equals("idLeaderRegion")){
	            	query.setParameter("idLeaderRegion",filtro.getValue() );
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
	                if(sort.getColName().equals("idRegister")){
	                    if(flagMore)
	                        sql.append(", ");

	                    sql.append( " idRegister " + sort.getSort() );
	                    flagMore = true;
	                }

	                if(sort.getColName().equals("dateAdmission")){
	                    if(flagMore)
	                        sql.append(", ");
	                    sql.append( " dateAdmission " + sort.getSort() );
	                    flagMore = true;
	                }
	                if(sort.getColName().equals("idCollaborator")){
	                    if(flagMore)
	                        sql.append(", ");

	                    sql.append( " idCollaborator " + sort.getSort() );
	                    flagMore = true;
	                }
	                if(sort.getColName().equals("idLeader")){
	                    if(flagMore)
	                        sql.append(", ");

	                    sql.append( " idLeader " + sort.getSort() );
	                    flagMore = true;
	                }
	                if(sort.getColName().equals("idLeaderRegion")){
	                	if(flagMore)
	                		sql.append(", ");
	                	
	                	sql.append( " idLeaderRegion " + sort.getSort() );
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
