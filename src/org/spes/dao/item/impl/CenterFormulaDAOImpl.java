package org.spes.dao.item.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.CenterFormula;
import org.spes.dao.item.CenterFormulaDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class CenterFormulaDAOImpl extends HibernateDaoSupport implements CenterFormulaDAO {

	//日记初始化加载
	private static final Logger log = LoggerFactory.getLogger(CenterFormulaDAOImpl.class);
	
	public List<CenterFormula> getFormulaByItemId(Integer itemId) {
		try {
			List<CenterFormula> formulas = this.getHibernateTemplate().find("from CenterFormula f where f.itemId = ?", itemId);
			return formulas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
     public boolean updateFormulaByItemId(Integer formulaId,String itemName, String formula,
		Integer itemId) {
    	 
    	try {
    		System.out.println("zhou updaate");
    		CenterFormula formulaInner  = this.getHibernateTemplate().get(CenterFormula.class, formulaId);
    		formulaInner.setItemName(itemName);
    		formulaInner.setItemId(itemId);
    		formulaInner.setCalculator(formula);
    		Timestamp timestamp = new Timestamp(new Date().getTime());
        	formulaInner.setUseTime(timestamp);
    		 
        	this.getHibernateTemplate().update(formulaInner);
        	return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	public boolean storeFormulaByItemId(String itemName, String formulaContent,
			Integer itemId) {
		System.out.println("store");
		try {
			CenterFormula formula = new CenterFormula();
			formula.setItemName(itemName);
			formula.setItemId(itemId);
			
			Timestamp timestamp = new Timestamp(new Date().getTime());
        	formula.setUseTime(timestamp);
        	formula.setCalculator(formulaContent);
        	//保存失败会返回-1，成功则返回其Id
        	Integer result = (Integer)this.getHibernateTemplate().save(formula);
        	if(result.equals(Integer.valueOf(-1))){
        		
        		return false;
        	}else {
				return true;
			}
        	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.spes.bean.CenterParamDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CenterFormula instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CenterFormula as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    
}
