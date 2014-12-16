package org.spes.dao.item.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.StaffFormula;
import org.spes.bean.StaffItem;
import org.spes.dao.item.StaffItemDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * StaffItem entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.StaffItem
 * @author MyEclipse Persistence Tools
 */

public class StaffItemDAOImpl extends HibernateDaoSupport implements StaffItemDAO {
	private static final Logger log = LoggerFactory
			.getLogger(StaffItemDAOImpl.class);
	// property constants
	public static final String ITEM_NAME = "itemName";
	public static final String PARENT_ID = "parentId";
	public static final String ITEM_GRADE = "itemGrade";
	public static final String ITEM_WEIGHT = "itemWeight";
	public static final String ITEM_TYPE = "itemType";
	public static final String ENABLED = "enabled";
	public static final String CENTER_ID = "centerId";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.StaffItemDAO#save(org.spes.bean.StaffItem)
	 */
	public void save(StaffItem transientInstance) {
		log.debug("saving StaffItem instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.StaffItemDAO#delete(org.spes.bean.StaffItem)
	 */
	public void delete(StaffItem persistentInstance) {
		log.debug("deleting StaffItem instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StaffItem findById(java.lang.Integer id) {
		log.debug("getting StaffItem instance with id: " + id);
		try {
			StaffItem instance = (StaffItem) getHibernateTemplate().get(
					"org.spes.bean.StaffItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StaffItem instance) {
		log.debug("finding StaffItem instance by example");
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
	 * @see org.spes.bean.StaffItemDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding StaffItem instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StaffItem as model where model."
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

	public List findByParentId(Object parentId) {
		return findByProperty(PARENT_ID, parentId);
	}

	public List findByItemGrade(Object itemGrade) {
		return findByProperty(ITEM_GRADE, itemGrade);
	}

	public List findByItemWeight(Object itemWeight) {
		return findByProperty(ITEM_WEIGHT, itemWeight);
	}

	public List findByItemType(Object itemType) {
		return findByProperty(ITEM_TYPE, itemType);
	}

	public List findByEnabled(Object enabled) {
		return findByProperty(ENABLED, enabled);
	}

	public List findByCenterId(Object centerId) {
		return findByProperty(CENTER_ID, centerId);
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
	 * @see org.spes.bean.StaffItemDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all StaffItem instances");
		try {
			String queryString = "from StaffItem";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StaffItem merge(StaffItem detachedInstance) {
		log.debug("merging StaffItem instance");
		try {
			StaffItem result = (StaffItem) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StaffItem instance) {
		log.debug("attaching dirty StaffItem instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StaffItem instance) {
		log.debug("attaching clean StaffItem instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StaffItemDAO getFromApplicationContext(ApplicationContext ctx) {
		return (StaffItemDAO) ctx.getBean("StaffItemDAO");
	}

	public List<StaffItem> findStaffItemByProperty(Integer windowId,
			Integer centerId) {
		String hql = "from StaffItem as s where s.windowId=? and centerId=?";
		return getHibernateTemplate().find(hql,windowId,centerId);
	}

	public List<StaffItem> findStaffByItemName(String itemName,
			Integer windowId, Integer centerId) {
		String hql = "from StaffItem as s where s.itemName=? and s.windowId=? and s.centerId=?";
		return getHibernateTemplate().find(hql, itemName, windowId, centerId);
	}
	
	public List<StaffItem> findStaffItemByProperty(Integer windowId,
			Integer centerId, Integer itemGrade) {
		String hql = "from StaffItem where windowId=? and centerId=? and itemGrade=?";
		return getHibernateTemplate().find(hql, windowId, centerId, itemGrade);
	}

	public List<StaffItem> findByItemNameAndIds(String itemName,
			Integer parentId, Integer windowId, Integer centerId) {
		String hql = "from StaffItem where itemName=? and parentId=? and windowId=? and centerId=?";
		return getHibernateTemplate().find(hql, itemName, parentId, windowId, centerId);
	}
	
	
	public List<StaffItem> findStaffItemByIds(Integer itemId, Integer windowId,
			Integer centerId) {
		String hql = "from StaffItem where itemId=? and windowId=? and centerId=?";
		return getHibernateTemplate().find(hql, itemId, windowId, centerId);
	}
	
	
	///////////////////zhoushaojun start////////////////////////////////
	
	public List<StaffItem> getAllParaData(int centerId, int windowId) {
		try {
			List<StaffItem> paraData = this.getHibernateTemplate().find(
					"from StaffItem u where u.centerId = ? and u.windowId = ?",
					new Integer[] { centerId, windowId });
			return paraData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<StaffItem> getFirstLevelAndSecondLevelPara(int centerItemId,
			int centerId, int windowId) {
		try {
			List<StaffItem> paraData = this.getHibernateTemplate().find(
					"from StaffItem u where u.centerId = ? and u.windowId = ?",
					new Integer[] { centerId, windowId });
			if (null == paraData || paraData.size() == 0) {
				return null;
			}
			List<StaffItem> result = new ArrayList<StaffItem>();
			// 查询二级指标信息
			for (StaffItem item : paraData) {
				if (item.getParentId() == centerItemId)
					result.add(item);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public StaffItem getFirstLevelPara(int staffItemId) {
		try {
			return this.getHibernateTemplate()
					.get(StaffItem.class, staffItemId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean curParaInDatabase(String paraname, int centerId, int windowId) {

		try {
			List<StaffItem> results = this.getHibernateTemplate().find("from StaffItem u where u.itemName = ? and u.centerId = ? and u.windowId = ?",
							new Object[] { paraname, centerId, windowId });
			if (null == results || results.size() != 0)
				return false;
			if (results.size() == 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean otherSameNameParaInDatabase(int centerItemId,
			String paraname, int centerId, int windowId) {
		try {
			StaffItem item = this.getHibernateTemplate().get(StaffItem.class,
					centerItemId);
			List<StaffItem> paraData = this.getHibernateTemplate().find(
					"from StaffItem u where u.centerId = ? and u.windowId = ?",
					new Integer[] { centerId, windowId });
			if (null == item || null == paraData || paraData.size() == 0) {
				return false;
			}
			for (StaffItem centerItem : paraData) {
				if (!centerItem.getItemId().equals(item.getItemId())) {
					// 查询内容是否有相同的
					if (centerItem.getItemName().equals(paraname)) {
						return false;
					}
				}
			}
			// 不存在相同内容的指标
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType) {
		try {
			StaffItem item = this.getHibernateTemplate().get(StaffItem.class,
					centerItemId);
			if (null == item) {
				return false;
			}
			item.setItemName(paraname);
			item.setItemWeight(weight);
			item.setEnabled(enable);
			item.setItemType(paraType);
			this.getHibernateTemplate().update(item);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int updateCenterItemByConcreteItem(StaffItem item) {

		try {
			List<StaffItem> items = this.getHibernateTemplate()
					.find("from StaffItem u where u.parentId = ?",
							item.getParentId());
			if (null == items)
				return -1;
			// 判断父亲指标下，是否存在此指标内容
			for (StaffItem centerItem : items) {
				if (!centerItem.getItemId().equals(item.getItemId())) {
					if (centerItem.getItemName().equals(item.getItemName()))
						return -2;
				}
			}
			this.getHibernateTemplate().update(item);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int storeFirstLevelPara(StaffItem firstPara) {
		if (null == firstPara)
			return -1;
		try {

			int result = (Integer) this.getHibernateTemplate().save(firstPara);

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int storeSecondAndThirdPara(StaffItem item) {
		try {
			List<StaffItem> items = this.getHibernateTemplate()
					.find("from StaffItem u where u.parentId = ?",
							item.getParentId());
			if (null == items)
				return -1;
			// 判断数据库中是否存在相同内容的指标
			for (StaffItem centerItem : items) {
				if (centerItem.getItemName().equals(item.getItemName()))
					return -2;
			}
			return (Integer) this.getHibernateTemplate().save(item);

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean deleteFirstLevelPara(int itemId) {
		try {
			// 所有指标信息
			final List<StaffItem> itemList = new ArrayList<StaffItem>();
			// 二级指标下的公式信息
			final List<StaffFormula> formulaList = new ArrayList<StaffFormula>();

			StaffItem parentItem = this.getHibernateTemplate().get(
					StaffItem.class, itemId);
			if (null == parentItem)
				return false;
			itemList.add(parentItem);
			// 获取二级指标内容
			List<StaffItem> secondParaData = this.getHibernateTemplate().find(
					"from StaffItem u where u.parentId = ?", itemId);
			if (null != secondParaData && secondParaData.size() != 0) {
				itemList.addAll(secondParaData);
				// 获取二级指标公式
				for (StaffItem item : secondParaData) {
					List<StaffFormula> formulas = this.getHibernateTemplate()
							.find("from StaffFormula u where u.itemId = ?",
									item.getItemId());
					if (null != formulas && formulas.size() != 0) {
						formulaList.addAll(formulas);
					}
				}
				// 获取三级指标内容
				for (StaffItem item : secondParaData) {
					List<StaffItem> paraData = this.getHibernateTemplate()
							.find("from StaffFormula u where u.parentId = ?",
									item.getItemId());
					// 判断三级指标结果是否为空或Null
					if (null != paraData && paraData.size() != 0) {
						itemList.addAll(paraData);
					}
				}// for end
			}
			// 事务回滚控制
			boolean result = this.getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Transaction transaction = session
									.beginTransaction();
							try {
								for (StaffItem item : itemList) {
									session.delete(item);
								}
								for (StaffFormula formula : formulaList) {
									session.delete(formula);
								}
								transaction.commit();

							} catch (Exception e) {
								e.printStackTrace();
								transaction.rollback();
								return false;
							} finally {

								session.close();
							}
							return true;
						}
					});
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean deleteSecondLevelPara(int itemId) {
		try {
			// 保存二级&三级指标信息
			final List<StaffItem> itemList = new ArrayList<StaffItem>();
			// 保存二级指标公式信息
			final List<StaffFormula> formulaList = new ArrayList<StaffFormula>();

			StaffItem parentItem = this.getHibernateTemplate().get(
					StaffItem.class, itemId);
			if (null == parentItem)
				return false;
			// 二级指标内容
			itemList.add(parentItem);
			// 二级指标公式内容
			List<StaffFormula> formulas = this.getHibernateTemplate().find(
					"from StaffFormula u where u.itemId = ?",
					parentItem.getItemId());
			if (null != formulas && formulas.size() != 0) {
				formulaList.addAll(formulas);
			}
			// 获取三级指标内容
			List<StaffItem> thirdParaData = this.getHibernateTemplate().find(
					"from StaffItem u where u.parentId = ?", itemId);
			if (null != thirdParaData && thirdParaData.size() != 0) {
				itemList.addAll(thirdParaData);
			}
			// 事务控制删除
			boolean result = this.getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Transaction transaction = session.getTransaction();
							try {
								transaction.begin();
								for (StaffItem centerItem : itemList) {
									session.delete(centerItem);
								}
								for (StaffFormula formula : formulaList) {
									session.delete(formula);
								}
								transaction.commit();
							} catch (Exception e) {
								e.printStackTrace();
								transaction.rollback();
								return false;
							} finally {
								session.close();
							}
							return true;
						}// doInHibernate end

					});
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int deleteCurParaInfo(int itemId) {
		try {
			// 获取三级指标实例
			StaffItem curItem = this.getHibernateTemplate().get(
					StaffItem.class, itemId);
			if (null == curItem)
				return -1;
			// 查找三级指标的父--二级指标
			StaffItem secondItem = this.getHibernateTemplate().get(
					StaffItem.class, curItem.getParentId());
			if (null == secondItem)
				return -1;
			// 获取公式
			List<StaffFormula> formula = this.getHibernateTemplate().find(
					"from StaffFormula u where u.itemId = ?",
					secondItem.getItemId());
			// [1]判断如果当前二级指标没有公式，三级指标可以直接删除
			if (null == formula || formula.size() == 0) {
				this.getHibernateTemplate().delete(curItem);
				return 1;
			}
			// [2]返回公式数量不为1，则
			if (formula.size() != 1)
				return -1;
			// [3]当有公式内容，则需要判断
			String formulaContent = formula.get(0).getCaclulator();
			String content = formulaContent.replaceAll("[(]", ";").replaceAll(
					"[)]", ";").replaceAll("[+]", ";").replaceAll("[-]", ";")
					.replaceAll("[*]", ";").replaceAll("[/]", ";");
			String[] strArray = content.split(";");
			for (String str : strArray) {
				if (str.equals(curItem.getItemName()))
					return -2;
			}
			// 通过验证可直接删除
			this.getHibernateTemplate().delete(curItem);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ArrayList<StaffItem> getCurSecondAndThridParameters(int firstItemId) {

		ArrayList<StaffItem> result = new ArrayList<StaffItem>();
		// 获取二级指标数据
		List<StaffItem> secondPara = this.getHibernateTemplate().find(
				"from StaffItem u where u.parentId= ?", firstItemId);
		if (null == secondPara) {
			return null;
		}
		// 获取三级指标数据
		int index = 0;
		for (StaffItem item : secondPara) {
			// 添加二级指标信息
			item.setBackup1(String.valueOf(++index));
			result.add(item);
			List<StaffItem> thirdPara = this.getHibernateTemplate().find(
					"from StaffItem u where u.parentId = ?", item.getItemId());
			if (null == thirdPara)
				continue;
			// 添加三级指标信息
			for (StaffItem thirdItem : thirdPara) {
				result.add(thirdItem);
			}
		}
		return result;

	}

	public List<StaffItem> getCurThirdParas(int itemId) {
		ArrayList<StaffItem> result = new ArrayList<StaffItem>();
		try {
			// 获取二级指标数据
			List<StaffItem> secondPara = this.getHibernateTemplate().find("from StaffItem u where u.parentId= ?", itemId);
			return secondPara;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	///////////////////zhoushaojun end/////////////////////////////////
}