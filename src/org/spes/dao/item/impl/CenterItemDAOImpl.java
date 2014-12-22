package org.spes.dao.item.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.CenterFormula;
import org.spes.bean.CenterItem;
import org.spes.dao.item.CenterItemDAO;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class CenterItemDAOImpl extends HibernateDaoSupport implements CenterItemDAO{
	private static final Logger log = LoggerFactory.getLogger(CenterItemDAOImpl.class);
	
	public List<CenterItem> getAllParaData() {
	    try {
	    	List<CenterItem> paraData = this.getHibernateTemplate().find("from CenterItem");
	    	return paraData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CenterItem> getFirstLevelAndSecondLevelPara(int centerItemId) {
	    try {
	    	List<CenterItem> paraData = this.getHibernateTemplate().find("from CenterItem");
	    	if(null == paraData || paraData.size() == 0 ){
	    		return null;
	    	}
	    	List<CenterItem> result = new ArrayList<CenterItem>();
	    	//查询二级指标信息
	    	for (CenterItem item : paraData) {
				if(item.getParentId() == centerItemId)
					result.add(item);
			}
	    	return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public CenterItem getFirstLevelPara(int centerItemId) {
	    try {
	    	return this.getHibernateTemplate().get(CenterItem.class, centerItemId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean curParaInDatabase(String paraname) {

	   try {
		   List<CenterItem> results = this.getHibernateTemplate().find("from CenterItem u where u.itemName = ? ",paraname);
		   if(null == results || results.size() !=0)return false;
		   if(results.size() == 0 ){
			   return true;
		   }else{
			   return false;
		   }
		   
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname) {
		try {
			CenterItem item = this.getHibernateTemplate().get(CenterItem.class,centerItemId);
			List<CenterItem> paraData = this.getHibernateTemplate().find("from CenterItem");
			if(null == item || null == paraData || paraData.size() ==0){
				return false;
			}
			for (CenterItem centerItem : paraData) {
				if(!centerItem.getItemId().equals(item.getItemId())){
					//查询内容是否有相同的
					if(centerItem.getItemName().equals(paraname)){
						return false;
					}
				}
			}
			//不存在相同内容的指标
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType) {
	    try {
	    	CenterItem item = this.getHibernateTemplate().get(CenterItem.class,centerItemId);
	    	if(null == item){
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
	public int updateCenterItemByConcreteItem(CenterItem item) {

	    try {
	    	List<CenterItem> items = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",item.getParentId());
	    	if(null == items) return -1;
	    	//判断父亲指标下，是否存在此指标内容
	    	for (CenterItem centerItem : items) {
				if(!centerItem.getItemId().equals(item.getItemId())){
					if(centerItem.getItemName().equals(item.getItemName()))
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

	public int storeFirstLevelPara(CenterItem firstPara) {
		if(null == firstPara) return -1;
		try {
			
        	int result = (Integer)this.getHibernateTemplate().save(firstPara);
        	
        	return result;
        	
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}	
	}
	public int storeSecondAndThirdPara(CenterItem item) {
         try {
			List<CenterItem> items= this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",item.getParentId());
			if(null == items)return -1;
			//判断数据库中是否存在相同内容的指标
			for (CenterItem centerItem : items) {
				if(centerItem.getItemName().equals(item.getItemName()))
					return -2;
			}
			return (Integer)this.getHibernateTemplate().save(item);
        	 
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean deleteFirstLevelPara(int itemId) {
         try {
        	  //所有指标信息
    	      final List<CenterItem> itemList = new ArrayList<CenterItem>();
    	      //二级指标下的公式信息
    	      final List<CenterFormula> formulaList = new ArrayList<CenterFormula>();
    	      
    	      CenterItem parentItem = this.getHibernateTemplate().get(CenterItem.class, itemId);
    	      if(null == parentItem) return false;
    	      itemList.add(parentItem);
    	      //获取二级指标内容
    	      List<CenterItem> secondParaData = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",itemId);
    	      if(null != secondParaData && secondParaData.size() != 0){
    	    	  itemList.addAll(secondParaData);
    	    	  //获取二级指标公式
    	    	  for (CenterItem item : secondParaData) {
    	    		  List<CenterFormula> formulas = this.getHibernateTemplate().find("from CenterFormula u where u.itemId = ?",item.getItemId());
    	    		  if(null != formulas && formulas.size() !=0){
    	    			  formulaList.addAll(formulas);
    	    		  }
    	    	  }
    	    	  //获取三级指标内容
    	    	  for (CenterItem item : secondParaData) {
    	    		  List<CenterItem> paraData = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",item.getItemId());
    	    		  //判断三级指标结果是否为空或Null
    	    		  if(null != paraData && paraData.size() !=0){
    	    			  itemList.addAll(paraData);
    	    		  }
				  }//for end
    	      }
    	      //事务回滚控制
    	      boolean result =  this.getHibernateTemplate().execute(new HibernateCallback() {
    	    	  
    	    	  public Object doInHibernate(Session session)
    	    			throws HibernateException, SQLException {
    	    		  Transaction transaction= session.beginTransaction();
    	    		  try {
    	    			for (CenterItem item : itemList) {
						    session.delete(item);	
						}
    	    			for (CenterFormula formula : formulaList) {
							session.delete(formula);
						}
    	    		    transaction.commit();
    	    		    
						} catch (Exception e) {
							e.printStackTrace();
							transaction.rollback();
							return false;
						}finally{
							
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
		  //保存二级&三级指标信息	
  	      final List<CenterItem> itemList = new ArrayList<CenterItem>();
  	      //保存二级指标公式信息
  	      final List<CenterFormula> formulaList = new ArrayList<CenterFormula>();
  	      
  	      CenterItem parentItem = this.getHibernateTemplate().get(CenterItem.class, itemId);
  	      if(null == parentItem) return false;
  	      //二级指标内容
  	      itemList.add(parentItem);
  	      //二级指标公式内容
  	      List<CenterFormula> formulas = this.getHibernateTemplate().find("from CenterFormula u where u.itemId = ?",parentItem.getItemId());
  	      if(null != formulas && formulas.size() != 0){
  	    	  formulaList.addAll(formulas);
  	      }
  	      //获取三级指标内容
  	      List<CenterItem> thirdParaData = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",itemId);
  	      if(null != thirdParaData && thirdParaData.size() != 0){
  	    	  itemList.addAll(thirdParaData);
  	      }
  	      //事务控制删除
  	      boolean result = this.getHibernateTemplate().execute(new HibernateCallback() {
		       public Object doInHibernate(Session session)
		        		throws HibernateException, SQLException {
		    	   Transaction transaction = session.getTransaction();
		    	   try {
		    		   transaction.begin();
		    		   for (CenterItem centerItem : itemList) {
						   session.delete(centerItem); 
		    		   }
		    		   for (CenterFormula formula : formulaList) {
		    			   session.delete(formula);
					   }
		    		   transaction.commit();
				   } catch (Exception e) {
					   e.printStackTrace();
					   transaction.rollback();
					   return false;
				   }finally{
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
			
		  //获取三级指标实例
		  CenterItem curItem = this.getHibernateTemplate().get(CenterItem.class, itemId);
   	      if(null == curItem) return -1;
   	      //查找三级指标的父--二级指标
		  CenterItem secondItem = this.getHibernateTemplate().get(CenterItem.class,curItem.getParentId());
		  if(null == secondItem) return -1;
		  //获取公式
		  List<CenterFormula> formula = this.getHibernateTemplate().find("from CenterFormula u where u.itemId = ?",secondItem.getItemId());
   	      //[1]判断如果当前二级指标没有公式，三级指标可以直接删除
		  if(null == formula || formula.size() ==0){
	   	      this.getHibernateTemplate().delete(curItem);
	   	      return 1;
		  }
		  //[2]返回公式数量不为1，则
		  if(formula.size() != 1)return -1;
   	      //[3]当有公式内容，则需要判断
		  String formulaContent = formula.get(0).getCalculator();
		  String content = formulaContent.replaceAll("[(]",";").replaceAll("[)]", ";").replaceAll("[+]", ";")
		                                  .replaceAll("[-]", ";").replaceAll("[*]", ";").replaceAll("[/]", ";");
		  String [] strArray = content.split(";");
		  for (String str : strArray) {
			  if(str.equals(curItem.getItemName()))
				  return -2;
		  }
		  //通过验证可直接删除
		  this.getHibernateTemplate().delete(curItem);
   	      return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public ArrayList<CenterItem> getCurSecondAndThridParameters(int firstItemId) {
         
		ArrayList<CenterItem> result = new ArrayList<CenterItem>();
		//获取二级指标数据
		List<CenterItem> secondPara = this.getHibernateTemplate().find("from CenterItem u where u.parentId= ?",firstItemId);
		if(null == secondPara ){
			return null;
		}
		//获取三级指标数据
		int index = 0;
		for (CenterItem item : secondPara) {
			//添加二级指标信息
			item.setBackup1(String.valueOf(++index));
			result.add(item);
			List<CenterItem> thirdPara = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",item.getItemId());
			if(null == thirdPara)continue;
			//添加三级指标信息
			for (CenterItem thirdItem : thirdPara) {
				result.add(thirdItem);
			}
		}
		return result;
	    
	}
	
	public List<CenterItem> getCurThirdParas(int itemId) {

		ArrayList<CenterItem> result = new ArrayList<CenterItem>();
		try{
			//获取二级指标数据
			List<CenterItem> secondPara = this.getHibernateTemplate().find("from CenterItem u where u.parentId= ?",itemId);
			return secondPara;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CenterItem> findCenterItemById(Integer centerId) {
		String hql = "from CenterItem where centerId=?";
		Session session = null;
		try {
			session = getSession();
			return session.createQuery(hql).setParameter(0, centerId).list();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally{
			releaseSession(session);
		}
	}

	public List<CenterItem> findByItemNameAndCenterId(String itemName,
			Integer centerId) {
		String hql = "from CenterItem where itemName=? and centerId=?";
		return getHibernateTemplate().find(hql, itemName, centerId);
	}

	public List<CenterItem> findByCenterIdAndItemGrade(Integer centerId,
			Integer itemGrade) {
		String hql = "from CenterItem where centerId=? and itemGrade=?";
		return getHibernateTemplate().find(hql, centerId, itemGrade);
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.StaffScoreDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CenterItem instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CenterItem as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<CenterItem> findByItemNameAndItemIds(String itemName,
			Integer parentId, Integer centerId) {
		String hql = "from CenterItem where itemName = ? and parentId = ? and centerId = ?";
		return getHibernateTemplate().find(hql, itemName, parentId, centerId);
	}

	public List<CenterItem> findByItemIdAndCenterId(Integer itemId,
			Integer centerId) {
		String hql = "from CenterItem where itemId = ? and centerId = ?";
		return getHibernateTemplate().find(hql, itemId, centerId);
	}
	@Override
	public List<CenterItem> findByParentIdAndCenterId(Integer parentId,
			Integer centerId) {
		String hql = "From CenterItem where parentId=? and centerId=?";
		return getHibernateTemplate().find(hql, parentId, centerId);
	}
}