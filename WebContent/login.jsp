<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
 <%@taglib prefix="s" uri="/struts-tags" %>
 <%@ page language="java" import="java.net.URLDecoder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="js/login.js"></script>
<title>行政服务中心绩效评价系统</title>
<style type="text/css">
#loginDiv{
	font-size:15px;
	background:#D8F0FC;
}

input{
 height:1.3em;
 width:132px;
}

#useCookie {
	width:20px;
	font-size : 10pt;
}

#submit,#reset{
	width:80px;
	height :26px;
}

#loginDiv{
 background:url(image/loginForm.jpg) no-repeat;
 background-position : center;
 margin-top:10em;
}

#remAutoLoginTxt{
   color:#194D72;
   font-weight:bold;
}

</style>
</head>
<body style="background-color: #F5FAFF">
<div id="loginDiv" style="width:99%;height:340px;text-align:center;vertical-align:middle;">
		<div id="formDiv" style="border:solid 1px #F5FAFF;width:100%; height:100%;text-align:center;" >
			<form id="loginForm"  action="loginAction.action" method="post" style="height:100%;">
			 
			  <table width="300px" cellspacing="0" cellpadding="0" height="120px" style="margin-top:140px;" border="0" align="center">
			    <tr style="height: 30px;text-align:right;padding-right:20px;">
			       <td><font color="red" size="2"><s:property value="tip"/></font>
			       <s:if test="hasFieldErrors()">
			         <s:iterator>
			           <span><font color="red"><s:property value="value[0]"/></font></span>
			         </s:iterator>
			       </s:if>
			       </td>
			    </tr>
			    <tr>
			      <td style="vertical-align: middle;text-align:right">
				      <input id="txtName" type="text" name="userName" />
				  </td>
			    </tr>
			    <tr style="height: 8px;">
			      <td></td>
			    </tr>
			    <tr>
			      <td style="vertical-align: middle;text-align:right">
			       <input id="txtPwd" type="password" name="password" size="20"/>
			      </td>
			    </tr>
			    <!-- 
			    <tr>
			      <td>
			      <div style="float: left;margin-left:158px;">
			        <input id="useCookie" type="checkbox" name="useCookie" value="false" title="自动登录" style="text-align: left;" /><label id="remAutoLoginTxt"  for="check">自动登录</label>
			      </div>
			      </td>
			    </tr>
			     -->
			    <tr style="height:35px;">
			      <td style="vertical-align: middle">
			        <div style="border:none;float:right;margin-right:0px;">
				        <input id="submit" type="submit" value="登 陆"/>&nbsp;
				        <input id="reset" type="button" value="注册" onclick="javascript:doregist();">
			        </div>
			      </td>
			    </tr>
			  </table>
			  </form>
		</div>    
</div>
</body>
</html>