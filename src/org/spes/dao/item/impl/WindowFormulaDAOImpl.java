package org.spes.dao.item.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.WindowFormula;
import org.spes.dao.item.WindowFormulaDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * WindowFormula entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.spes.bean.WindowFormula
 * @author MyEclipse Persistence Tools
 */

public class WindowFormulaDAOImpl extends HibernateDaoSupport implements WindowFormulaDAO {
	private static final Logger log = LoggerFactory
			.getLogger(WindowFormulaDAOImpl.class);
	// property constants
	public static final String ITEM_NAME = "itemName";
	public static final String CALCULATOR = "calculator";
	public static final String ITEM_ID = "itemId";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowFormulaDAO#save(org.spes.bean.WindowFormula)
	 */
	public void save(WindowFormula transientInstance) {
		log.debug("saving WindowFormula instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowFormulaDAO#delete(org.spes.bean.WindowFormula)
	 */
	public void delete(WindowFormula persistentInstance) {
		log.debug("deleting WindowFormula instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WindowFormula findById(java.lang.Integer id) {
		log.debug("getting WindowFormula instance with id: " + id);
		try {
			WindowFormula instance = (WindowFormula) getHibernateTemplate()
					.get("org.spes.bean.WindowFormula", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(WindowFormula instance) {
		log.debug("finding WindowFormula instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowFormulaDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding WindowFormula instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from WindowFormula as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List findByCalculator(Object calculator) {
		return findByProperty(CALCULATOR, calculator);
	}

	public List findByItemId(Object itemId) {
		return findByProperty(ITEM_ID, itemId);
	}

	public List findByBackup1(Object backup1) {
		return findByProperty(BACKUP1, backup1);
	}

	public List findByBackup2(Object backup2) {
		return findByProperty(BACKUP2, backup2);
	}

	public List findByBackup3(Object backup3) {
		return findByProperty(BACKUP3, backup3);
	}

	public List findByBackup4(Object backup4) {
		return findByProperty(BACKUP4, backup4);
	}

	public List findByBackup5(Object backup5) {
		return findByProperty(BACKUP5, backup5);
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowFormulaDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all WindowFormula instances");
		try {
			String queryString = "from WindowFormula";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public WindowFormula merge(WindowFormula detachedInstance) {
		log.debug("merging WindowFormula instance");
		try {
			WindowFormula result = (WindowFormula) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(WindowFormula instance) {
		log.debug("attaching dirty WindowFormula instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WindowFormula instance) {
		log.debug("attaching clean WindowFormula instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static WindowFormulaDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (WindowFormulaDAO) ctx.getBean("WindowFormulaDAO");
	}

	public List<WindowFormula> getFormulaByItemId(Integer itemId) {
		try {
			List<WindowFormula> formulas = this.getHibernateTemplate().find("from WindowFormula f where f.itemId = ?", itemId);
			return formulas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateFormulaByItemId(Integer formulaId, String itemName,
			String formula, Integer itemId) {

		try {
			WindowFormula formulaInner = this.getHibernateTemplate().get(WindowFormula.class, formulaId);
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

		try {
			WindowFormula formula = new WindowFormula();
			formula.setItemName(itemName);
			formula.setItemId(itemId);
			Timestamp timestamp = new Timestamp(new Date().getTime());
			formula.setUseTime(timestamp);
			formula.setCalculator(formulaContent);
			// 保存失败会返回-1，成功则返回其Id
			Integer result = (Integer) this.getHibernateTemplate().save(formula);
			if (result.equals(Integer.valueOf(-1))) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}