/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */

	Docs.classData ={"id":"apidocs","iconCls":"icon-docs","text":"系统功能菜单","singleClickExpand":true,"children":[
            {"id":"user","text":"用户管理","iconCls":"icon-pkg","cls":"package", "singleClickExpand":true, children:[
                {"href":"./user/addUser.jsp","text":"用户管理","id":"UserManage.用户管理","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true},
                {"href":"./user/quanxian.jsp","text":"角色配置","id":"UserManage.角色配置","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
			   ]},
			{
			 "id":"jixiao","text":"绩效考核管理","iconCls" : "icon-pkg","cls":"package", "singleClickExpand":true, children:[
					 {"href":"./jixiao/center.jsp","text":"中心考核","id":"JiXiaoManage.中心考核","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true},
					 {"href":"./jixiao/window.jsp","text":"窗口考核","id":"JiXiaoManage.窗口考核","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true},
					 {"href":"./jixiao/employee.jsp","text":"窗口人员考核","id":"JiXiaoManage.窗口人员考核","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
			   ]},
			{
			 "id":"result-show","text":"结果展示","iconCls" : "icon-pkg","cls":"package", "singleClickExpand":true, children:[
					 {"href":"./result/center.jsp","text":"中心结果","id":"ResultManage.中心结果","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true},
					 {"href":"./result/window.jsp","text":"窗口结果","id":"ResultManage.窗口结果","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true},
					 {"href":"./result/employee.jsp","text":"窗口人员结果","id":"ResultManage.窗口人员结果","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
			   ]},
			{
			 "id":"index-manage","text":"指标设计","iconCls" : "icon-pkg","cls":"package", "singleClickExpand":true, children:[
					 {"href":"./index/center.jsp","text":"指标管理","id":"ItemManage.指标管理","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true},
					 {"href":"./index/window.jsp","text":"权重设计","id":"WeightManage.权重设计","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
					 
			   ]},	
			{
			 "id":"center-manage","text":"中心管理","iconCls" : "icon-pkg","cls":"package", "singleClickExpand":true, children:[
					 {"href":"./center/centerManage.jsp","text":"中心管理","id":"centerManage.中心管理","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
					 
			   ]},
			{
			 "id":"sys-manage","text":"系统管理","iconCls" : "icon-pkg","cls":"package", "singleClickExpand":true, children:[
					 {"href":"./center/sysManage.jsp","text":"系统管理","id":"sysManage.系统管理","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
					 
			   ]}
			   
			]};
	
	//图标
    Docs.icons = {
	"系统首页.系统首页" :"zbsj",
	
	"指标设计.中心设计" :"zxsj",
	"指标设计.窗口设计" :"ckkh",
	"指标设计.人员设计" :"ckptrykh",
	
	"指标查看.中心指标" :"zxzb",
	"指标查看.窗口指标" :"ckzb",
	"指标查看.窗口普通人员指标" :"ckptryzb",
	
	"绩效考核.中心考核" :"zxkh",
	"绩效考核.窗口考核" :"ckkh",
	"绩效考核.窗口普通人员考核" : "ckptrykh",
	
	"绩效考核.中心考核列表" :"zxkh",
	"绩效考核.窗口考核列表" :"ckkh",
	"绩效考核.窗口普通人员考核列表" : "ckptrykh",
	
		
	"绩效考核.中心考核->续" :"zxkh",
	"绩效考核.窗口考核->续" :"ckkh",
	"绩效考核.窗口普通人员考核->续" : "ckptrykh",
	
	
	"系统维护.系统公告" : "xtgg",
	"系统维护.服务问答" : "fwwd",
	"系统维护.公告查看" : "ggck",
	"系统维护.问答回复" : "wdhf",
	
	"信息服务.系统公告" : "xtgg",
	"信息服务.公告查看" : "ggck",
	"信息服务.服务问答" : "fwwd",
	
	"系统管理.密码修改" : 'mmxg',
	"窗口管理.窗口设置" : "cksz",
	
	"考核结果展示.中心考核结果" :"khjgzs",
	"考核结果展示.窗口考核结果" :"khjgzs",
	"考核结果展示.窗口普通人员考核结果" :"khjgzs",
	"考核结果展示.个人考核结果" : "khjgzs",
	
	"中心管理.中心注册" : 'zxzc',
	"中心管理.中心维护" : 'zxxg',
	"中心管理.中心审核" : 'zxsh',
	
	
	"用户管理.角色管理" : "jsgl",
	"用户管理.权限分配" : "qxfp",
	"用户管理.添加用户" : "tjyh",
	"用户管理.修改用户" : "xgyh",
	"用户管理.注销用户" : "zxyh",
	"用户管理.审核用户" : "shyh",
	"用户管理.用户审核" : "yhsh",
	"用户管理.用户中心" : "yhzx",
	
	"用户中心.个人信息" : "grxx",
	"角色管理.角色分配" : "jsgl"
};
    