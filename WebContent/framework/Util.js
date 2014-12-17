
/**
 * 系统工具类
 * @author hegang
 */

Util = {
	/**
	 * 全局常量
	 */
  Constant : {
	
  },
  
  /**
   * 缓存数据用
   * @param {Object} response
   * @return {TypeName} 
   */
  cache : {},

  markStar : '<span style="color : red; title:必填字段">*</span>',
  
  ajaxCache : {
	  readyCount : 1,//缓冲次数
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
   * 把rest返回的包含List<JSONObject>类型数据的response转换成固定格式的List
   * @param {Object} response
   * @return [] 
   */
  readListFromResponse : function (response) {
	  var res = val("(" + response.responseText + ")"); //获取后台返回的数据、
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
    	  console.error('无法读取 response',header , response);
    	  break;
      }//end for
  }//end function
	
}