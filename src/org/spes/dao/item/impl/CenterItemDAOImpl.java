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
	    	//��ѯ����ָ����Ϣ
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
					//��ѯ�����Ƿ�����ͬ��
					if(centerItem.getItemName().equals(paraname)){
						return false;
					}
				}
			}
			//��������ͬ���ݵ�ָ��
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
	    	//�жϸ���ָ���£��Ƿ���ڴ�ָ������
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
			//�ж����ݿ����Ƿ������ͬ���ݵ�ָ��
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
        	  //����ָ����Ϣ
    	      final List<CenterItem> itemList = new ArrayList<CenterItem>();
    	      //����ָ���µĹ�ʽ��Ϣ
    	      final List<CenterFormula> formulaList = new ArrayList<CenterFormula>();
    	      
    	      CenterItem parentItem = this.getHibernateTemplate().get(CenterItem.class, itemId);
    	      if(null == parentItem) return false;
    	      itemList.add(parentItem);
    	      //��ȡ����ָ������
    	      List<CenterItem> secondParaData = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",itemId);
    	      if(null != secondParaData && secondParaData.size() != 0){
    	    	  itemList.addAll(secondParaData);
    	    	  //��ȡ����ָ�깫ʽ
    	    	  for (CenterItem item : secondParaData) {
    	    		  List<CenterFormula> formulas = this.getHibernateTemplate().find("from CenterFormula u where u.itemId = ?",item.getItemId());
    	    		  if(null != formulas && formulas.size() !=0){
    	    			  formulaList.addAll(formulas);
    	    		  }
    	    	  }
    	    	  //��ȡ����ָ������
    	    	  for (CenterItem item : secondParaData) {
    	    		  List<CenterItem> paraData = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",item.getItemId());
    	    		  //�ж�����ָ�����Ƿ�Ϊ�ջ�Null
    	    		  if(null != paraData && paraData.size() !=0){
    	    			  itemList.addAll(paraData);
    	    		  }
				  }//for end
    	      }
    	      //����ع�����
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
		  //�������&����ָ����Ϣ	
  	      final List<CenterItem> itemList = new ArrayList<CenterItem>();
  	      //�������ָ�깫ʽ��Ϣ
  	      final List<CenterFormula> formulaList = new ArrayList<CenterFormula>();
  	      
  	      CenterItem parentItem = this.getHibernateTemplate().get(CenterItem.class, itemId);
  	      if(null == parentItem) return false;
  	      //����ָ������
  	      itemList.add(parentItem);
  	      //����ָ�깫ʽ����
  	      List<CenterFormula> formulas = this.getHibernateTemplate().find("from CenterFormula u where u.itemId = ?",parentItem.getItemId());
  	      if(null != formulas && formulas.size() != 0){
  	    	  formulaList.addAll(formulas);
  	      }
  	      //��ȡ����ָ������
  	      List<CenterItem> thirdParaData = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",itemId);
  	      if(null != thirdParaData && thirdParaData.size() != 0){
  	    	  itemList.addAll(thirdParaData);
  	      }
  	      //�������ɾ��
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
			
		  //��ȡ����ָ��ʵ��
		  CenterItem curItem = this.getHibernateTemplate().get(CenterItem.class, itemId);
   	      if(null == curItem) return -1;
   	      //��������ָ��ĸ�--����ָ��
		  CenterItem secondItem = this.getHibernateTemplate().get(CenterItem.class,curItem.getParentId());
		  if(null == secondItem) return -1;
		  //��ȡ��ʽ
		  List<CenterFormula> formula = this.getHibernateTemplate().find("from CenterFormula u where u.itemId = ?",secondItem.getItemId());
   	      //[1]�ж������ǰ����ָ��û�й�ʽ������ָ�����ֱ��ɾ��
		  if(null == formula || formula.size() ==0){
	   	      this.getHibernateTemplate().delete(curItem);
	   	      return 1;
		  }
		  //[2]���ع�ʽ������Ϊ1����
		  if(formula.size() != 1)return -1;
   	      //[3]���й�ʽ���ݣ�����Ҫ�ж�
		  String formulaContent = formula.get(0).getCalculator();
		  String content = formulaContent.replaceAll("[(]",";").replaceAll("[)]", ";").replaceAll("[+]", ";")
		                                  .replaceAll("[-]", ";").replaceAll("[*]", ";").replaceAll("[/]", ";");
		  String [] strArray = content.split(";");
		  for (String str : strArray) {
			  if(str.equals(curItem.getItemName()))
				  return -2;
		  }
		  //ͨ����֤��ֱ��ɾ��
		  this.getHibernateTemplate().delete(curItem);
   	      return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public ArrayList<CenterItem> getCurSecondAndThridParameters(int firstItemId) {
         
		ArrayList<CenterItem> result = new ArrayList<CenterItem>();
		//��ȡ����ָ������
		List<CenterItem> secondPara = this.getHibernateTemplate().find("from CenterItem u where u.parentId= ?",firstItemId);
		if(null == secondPara ){
			return null;
		}
		//��ȡ����ָ������
		int index = 0;
		for (CenterItem item : secondPara) {
			//��Ӷ���ָ����Ϣ
			item.setBackup1(String.valueOf(++index));
			result.add(item);
			List<CenterItem> thirdPara = this.getHibernateTemplate().find("from CenterItem u where u.parentId = ?",item.getItemId());
			if(null == thirdPara)continue;
			//�������ָ����Ϣ
			for (CenterItem thirdItem : thirdPara) {
				result.add(thirdItem);
			}
		}
		return result;
	    
	}
	
	public List<CenterItem> getCurThirdParas(int itemId) {

		ArrayList<CenterItem> result = new ArrayList<CenterItem>();
		try{
			//��ȡ����ָ������
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
}