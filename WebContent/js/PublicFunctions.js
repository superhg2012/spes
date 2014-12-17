function showErrorMsg ( msg , title ) {
	Ext.MessageBox.show({
		title: title || '操作失败',
		msg: "<b  style='color: #FF0000'>" + msg + "</b>",
		buttons: Ext.MessageBox.OK,
		icon: Ext.MessageBox.ERROR
	});
}

function showSuccessMsg ( msg, title ) {
	Ext.MessageBox.show({
		title: title || '操作成功',
		msg: "<b  style='color: #0080FF'>" + msg + "</b>",
		buttons: Ext.MessageBox.OK,
		icon: Ext.MessageBox.INFO
	});
}

function showFormFailureMsg( action, title ) {
	if(action.failureType==Ext.form.Action.CONNECT_FAILURE){
		showErrorMsg( '网络连接失败。', title);
	}
	else if(action.failureType==Ext.form.Action.CLIENT_INVALID){
		showErrorMsg( '某些输入项不符合规定，请检查各字段的输入。', title);
	}
	else if(action.failureType==Ext.form.Action.SERVER_INVALID){
		showErrorMsg( action.result.msg, title);
	}
	else if(action.failureType==Ext.form.Action.LOAD_FAILURE){
		showErrorMsg( 'LOAD_FAILURE', title);
	}
	else {
		showErrorMsg( '未知的原因。', title);
	}
}

function showAjaxResponseMsg( response, what, silentOnSuccess) {
	var success = false;
	if(response.status==200){
		var json;
		
		try {
			json = Ext.decode(response.responseText);
		}
		catch(e){
			showErrorMsg( what + "返回的数据格式不对。");
		}
		
		if(json && json.success){
			success = true;
			if(silentOnSuccess == null || silentOnSuccess == false){
				showSuccessMsg( what + "成功。");
			}
			else {
				WSMessageBox.msg("操作成功",what + "成功。");
			}
			return json;
		}
		else {
			if(json && json.msg) {
				showErrorMsg(json.msg);
			}
			else {
				showErrorMsg( what + "也许不成功：服务器没有返回出错原因信息。");
			}
			
			if(json && json.url){
				window.location.href = json.url;
			}
		}
	}
	else {
		showErrorMsg( what + "失败：网络连接有问题或者服务器错误。")
	}
	return success;
}

WSMessageBox = function() {
	var msgCt;
    function createBox(t, s){
		return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    }
	return {
		msg : function(title, format){
			if(!msgCt){
 				msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
 			}
			var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
			var m = Ext.DomHelper.append(msgCt, createBox(title, s), true);
			m.hide();
			m.slideIn('t').ghost('t', { delay: 2000, remove: true});
		}
	};
}();