﻿Ext.define('Admin.view.leave.LeaveViewController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.leaveViewController',
	/*Add*/
	openAddWindow:function(toolbar, e){
		toolbar.up('panel').up('container').add(Ext.widget('leaveAddWindow')).show();
	},
	/*Edit*/
	openEditWindow:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		
		if (record ) {
			if(record.data.status=="0"||record.data.status=="4"){
				var win = grid.up('container').add(Ext.widget('leaveEditWindow'));
				win.show();
				win.down('form').getForm().loadRecord(record);
			}else{
				Ext.Msg.alert('提示', "只可以修改'待申请'或'被驳回'状态的信息！");
			}
		}
	},
	/*Search More*/
	openSearchWindow:function(toolbar, e){
		toolbar.up('panel').up('container').add(Ext.widget('leaveSearchWindow')).show();
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
	/********************************************** Submit / Ajax / Rest *****************************************************/
	/*Add Submit*/	
	submitAddForm:function(btn){
		var win    = btn.up('window');
		var form = win.down('form');
		var record = Ext.create('Admin.model.leave.LeaveModel');
		var values  =form.getValues();//获取form数据
		if(values.startTime==''||values.startTime==undefined||values.startTime==null||values.endTime==''||values.endTime==undefined||values.endTime==null){
			alert('请假开始时间和结束时间不能为空');
		}
		else{
			if(values.startTime>values.endTime){
				alert('请假开始时间不能晚于结束时间');
			}
			else{
				if(values.reason==''||values.reason==undefined||values.reason==null){
					alert('请假理由不能为空');
				}
				else{
					record.set(values);
					record.save();
					Ext.data.StoreManager.lookup('leaveStore').load();
					win.close();
				}
			}
		}
	},
	/*Edit Submit*/	
	submitEditForm:function(btn){
		var win    = btn.up('window');
		var store = Ext.data.StoreManager.lookup('leaveStore');
		var values  = win.down('form').getValues();//获取form数据
		var record = store.getById(values.id);//获取id获取store中的数据
		record.set(values);//rest put 
		store.load();
		win.close();
	},
	/*Quick Search*/	
	quickSearch:function(btn){
		var searchField = this.lookupReference('searchFieldName').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		var searchDataFieldValue2 = this.lookupReference('searchDataFieldValue2').getValue();
		var searchFieldValue = this.lookupReference('searchFieldValue').getValue();

		var store =	btn.up('gridpanel').getStore();
		//var store = Ext.getCmp('userGridPanel').getStore();// Ext.getCmp(）需要在LeavePanel设置id属性
		Ext.apply(store.proxy.extraParams, {status:"",startTime:"",endTime:""});
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
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('leaveStore');
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
	},
	/*Delete One Row*/	
	deleteOneRow:function(grid, rowIndex, colIndex){
		var store = grid.getStore();
		var record = store.getAt(rowIndex);
		if(record.data.status=="0"||record.data.status=="3"||record.data.status=="4"){
			Ext.MessageBox.confirm('提示', '确定要进行删除操作吗？',function(btn, text){
				if(btn=='yes'){
					store.remove(record);
				}
			}, this);
		}else{
			Ext.Msg.alert('提示', "只可以删除'待申请'或'已销假'或'被驳回'状态的信息！");
		}
	},
	/*Delete More Rows*/	
	deleteMoreRows:function(btn, rowIndex, colIndex){
		var grid = btn.up('gridpanel');
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
				if (button == "yes") {
					var rows = selModel.getSelection();
					var selectIds = []; //要删除的id
					Ext.each(rows, function (row) {
						if(row.data.status=="0"||row.data.status=="3"||record.data.status=="4"){
							selectIds.push(row.data.id);
						}
					});
					Ext.Ajax.request({ 
						url : '/leave/deletes', 
						method : 'post', 
						params : { 
							//ids[] :selectIds
							ids :selectIds
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
				}
			});
		}else {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
		}
	},
	/*Star Leave Process*/	
	starLeaveProcess:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({ 
			url : '/leave/application', 
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
	}
});
