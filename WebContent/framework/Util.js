
/**
 * ϵͳ������
 * @author hegang
 */

Util = {
	/**
	 * ȫ�ֳ���
	 */
  Constant : {
	
  },
  
  /**
   * ����������
   * @param {Object} response
   * @return {TypeName} 
   */
  cache : {},

  markStar : '<span style="color : red; title:�����ֶ�">*</span>',
  
  ajaxCache : {
	  readyCount : 1,//�������
	  setCount : function(){
	    if(this.readyCount == 1) {
	    	this.callBack();
	    } else {
	    	this.countDown();
	    	return this.readyCount--;
	    }
	  },
	  callBack : Ext.emptyFn,
	  countDown : Ext.emptyFn
  },
  
  
  
  /**
   * ��rest���صİ���List<JSONObject>�������ݵ�responseת���ɹ̶���ʽ��List
   * @param {Object} response
   * @return [] 
   */
  readListFromResponse : function (response) {
	  var res = val("(" + response.responseText + ")"); //��ȡ��̨���ص����ݡ�
      if(res == null) {
    	  return [];
      }
      for(var header in res) {
    	  var body = res[header];
    	  if(body[0] == unddefined){
    		  return [body];
    	  } else {
    		  var arr = [];
    		  for(var j=0;j<body.length;j++){
    			  arr.push(body[j]);
    		  }
    		  return arr;
    	  }
    	  console.error('�޷���ȡ response',header , response);
    	  break;
      }//end for
  }//end function
	
}