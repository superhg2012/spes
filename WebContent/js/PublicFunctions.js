function showErrorMsg ( msg , title ) {
	Ext.MessageBox.show({
		title: title || '����ʧ��',
		msg: "<b  style='color: #FF0000'>" + msg + "</b>",
		buttons: Ext.MessageBox.OK,
		icon: Ext.MessageBox.ERROR
	});
}

function showSuccessMsg ( msg, title ) {
	Ext.MessageBox.show({
		title: title || '�����ɹ�',
		msg: "<b  style='color: #0080FF'>" + msg + "</b>",
		buttons: Ext.MessageBox.OK,
		icon: Ext.MessageBox.INFO
	});
}

function showFormFailureMsg( action, title ) {
	if(action.failureType==Ext.form.Action.CONNECT_FAILURE){
		showErrorMsg( '��������ʧ�ܡ�', title);
	}
	else if(action.failureType==Ext.form.Action.CLIENT_INVALID){
		showErrorMsg( 'ĳЩ��������Ϲ涨��������ֶε����롣', title);
	}
	else if(action.failureType==Ext.form.Action.SERVER_INVALID){
		showErrorMsg( action.result.msg, title);
	}
	else if(action.failureType==Ext.form.Action.LOAD_FAILURE){
		showErrorMsg( 'LOAD_FAILURE', title);
	}
	else {
		showErrorMsg( 'δ֪��ԭ��', title);
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
			showErrorMsg( what + "���ص����ݸ�ʽ���ԡ�");
		}
		
		if(json && json.success){
			success = true;
			if(silentOnSuccess == null || silentOnSuccess == false){
				showSuccessMsg( what + "�ɹ���");
			}
			else {
				WSMessageBox.msg("�����ɹ�",what + "�ɹ���");
			}
			return json;
		}
		else {
			if(json && json.msg) {
				showErrorMsg(json.msg);
			}
			else {
				showErrorMsg( what + "Ҳ���ɹ���������û�з��س���ԭ����Ϣ��");
			}
			
			if(json && json.url){
				window.location.href = json.url;
			}
		}
	}
	else {
		showErrorMsg( what + "ʧ�ܣ�����������������߷���������")
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