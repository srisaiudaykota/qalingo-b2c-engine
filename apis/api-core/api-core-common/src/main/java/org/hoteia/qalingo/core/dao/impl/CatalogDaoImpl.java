/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.CatalogDao;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.User;

@Transactional
@Repository("productCatalogDao")
public class CatalogDaoImpl extends AbstractGenericDaoImpl implements CatalogDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<CatalogMaster> findAllCatalogMasters() {
        return em.createQuery("SELECT cm FROM CatalogMaster cm").getResultList();
    }

    public CatalogMaster getProductCatalogById(final Long catalogMasterId) {
		return em.find(CatalogMaster.class, catalogMasterId);
	}

	public CatalogVirtual getCatalogVirtual(final Long marketAreaId, final Long retailerId) {
	    
	     initCatalogVirtual(getSession(), marketAreaId, retailerId);

        Criteria criteria = getSession().createCriteria(CatalogVirtual.class);
        criteria.createAlias("marketArea", "ma");
        criteria.add( Restrictions.eq("ma.id", marketAreaId));
        
        criteria.setFetchMode("catalogMaster", FetchMode.JOIN);        
        criteria.setFetchMode("catalogCategories", FetchMode.JOIN);        

        CatalogVirtual catalogVirtual = (CatalogVirtual) criteria.uniqueResult();
        
//        catalogMaster
//        catalogCategories
        
//        catalogCategories : defaultParentCatalogCategory
//        catalogCategories : categoryMaster
//        catalogCategories : catalogCategoryGlobalAttributes
//        catalogCategories : catalogCategoryMarketAreaAttributes
//        catalogCategories : catalogCategories
//        catalogCategories : productMarketings
//        catalogCategories : assetsIsGlobal
//        catalogCategories : assetsByMarketArea
        
//		Session session = (Session) em.getDelegate();
//		initCatalogVirtual(session, marketAreaId, retailerId);
//		String sql = "SELECT cv FROM CatalogVirtual cv, MarketArea ma WHERE cv.id = ma.virtualCatalogId AND ma.id = :marketAreaId";
//		Query query = session.createQuery(sql);
//		query.setLong("marketAreaId", marketAreaId);
//		CatalogVirtual catalogVirtual = (CatalogVirtual) query.uniqueResult();
		
		return catalogVirtual;
	}
	
	public void saveOrUpdateProductCatalog(final CatalogMaster catalogMaster) {
		if(catalogMaster.getDateCreate() == null){
			catalogMaster.setDateCreate(new Date());
		}
		catalogMaster.setDateUpdate(new Date());
		if(catalogMaster.getId() == null){
			em.persist(catalogMaster);
		} else {
			em.merge(catalogMaster);
		}
	}

	public void deleteProductCatalog(final CatalogMaster catalogMaster) {
		em.remove(catalogMaster);
	}

}
