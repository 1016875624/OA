Ext.define('Admin.view.leaveapprove.LeaveApproveViewController', {
    extend: Ext.app.ViewController,
    alias: 'controller.leaveApproveViewController',
    onClickLeaveApproveCompleteWindowButton: function(grid, rowIndex, colIndex) {
    	var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		
		if (record ) {
			if(record.data.status=="1"){
				var win = grid.up('container').add(Ext.widget('leaveApproveWindow'));
				win.show();
				win.down('form').getForm().loadRecord(record);
			}else{
				Ext.Msg.alert('提示', "只可以审批'待审批'状态的信息！");
			}
		}
    },
	/*submitApproveForm*/	
	submitApproveForm:function(btn){
		var win    = btn.up('window');
		var values  = win.down('form').getValues();//获取form数据
		Ext.Ajax.request({ 
			url : '/leave/approval', 
			method : 'get', 
			params : {
				id :values.id
			}, 
			success: function(response, options) {
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.success){
					Ext.Msg.alert('操作成功', json.msg, function() {
						Ext.data.StoreManager.lookup('leaveApproveStore').load();
						win.close();
				});
				}else{
					Ext.Msg.alert('操作失败', json.msg);
				}
			}
		});
	},
	 onClickRejectWindowButton: function(grid, rowIndex, colIndex) {
    	var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		
		if (record) {
			if(record.data.status=="1"){
				var win = grid.up('container').add(Ext.widget('leaveRejectWindow'));
				win.show();
				win.down('form').getForm().loadRecord(record);
			}else{
				Ext.Msg.alert('提示', "只可以驳回'待审批'状态的信息！");
			}
		}
    },
	submitRejectForm:function(btn){
		var win    = btn.up('window');
		var values  = win.down('form').getValues();//获取form数据
		Ext.Ajax.request({ 
			url : '/leave/reject', 
			method : 'post',
			params : {
				id :values.id,
				reason:values.reason
			}, 
			success: function(response, options) {
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.success){
					Ext.Msg.alert('操作成功', json.msg, function() {
						Ext.data.StoreManager.lookup('leaveApproveStore').load();
						win.close();
				});
				}else{
					Ext.Msg.alert('操作失败', json.msg);
				}
			}
		});
	},
	/*End Leave Process*/	
	endLeaveProcess:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({
			url : '/leave/endleave',
			method : 'post', 
			params : {
				id :record.get("id")
			}, 
			success: function(response, options) {
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.success){
					Ext.Msg.alert('操作成功', json.msg, function() {
					grid.getStore().reload();
				});
				}else{
					Ext.Msg.alert('操作失败', json.msg);
				}
			}
		});
	},
	/*combobox选中后控制对应输入（文本框和日期框）框显示隐藏*/
	searchComboboxSelectChuang:function(combo,record,index){
		//alert(record.data.name);
		var searchField = this.lookupReference('searchFieldName').getValue();
		if(searchField==='leaveTime'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchDataFieldValue').show();
			this.lookupReference('searchDataFieldValue2').show();
		}else{
			this.lookupReference('searchFieldValue').show();
			this.lookupReference('searchDataFieldValue').hide();
			this.lookupReference('searchDataFieldValue2').hide();
		}
	},
	/*Quick Search*/	
	quickSearch:function(btn){
		var searchField = this.lookupReference('searchFieldName').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		var searchDataFieldValue2 = this.lookupReference('searchDataFieldValue2').getValue();
		var searchFieldValue = this.lookupReference('searchFieldValue').getValue();

		var store =	btn.up('gridpanel').getStore();
		//var store = Ext.getCmp('userGridPanel').getStore();// Ext.getCmp(）需要在LeavePanel设置id属性
		Ext.apply(store.proxy.extraParams, {startTime:"",endTime:""});
		if(searchField==='leaveTime'){
			Ext.apply(store.proxy.extraParams,{
				startTime:Ext.util.Format.date(searchDataFieldValue, 'Y/m/d H:i:s'),
				endTime:Ext.util.Format.date(searchDataFieldValue2, 'Y/m/d H:i:s')
			});
		}
		else if(searchField==='status'){
			Ext.apply(store.proxy.extraParams,{
				status:searchFieldValue,
			});
		}
		store.load({params:{start:0, limit:20, page:1}});
	},
	/*Search More*/
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('leaveSearchWindow')).show();
	},
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('leaveApproveStore');
		var win = btn.up('window');
		var form = win.down('form');
		var values  = form.getValues();
		Ext.apply(store.proxy.extraParams, {status:"",startTime:"",endTime:""});
		Ext.apply(store.proxy.extraParams,{
			status:values.status,
			startTime:Ext.util.Format.date(values.startTime, 'Y/m/d H:i:s'),
			endTime:Ext.util.Format.date(values.endTime, 'Y/m/d H:i:s')
		});
		store.load({params:{start:0, limit:20, page:1}});
		win.close();
	}
});