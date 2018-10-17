﻿Ext.define('Admin.view.officeResource.OfficeResourceViewController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.officeResourceViewController',
	/*Add*/
	openAddWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('officeResourceAddWindow')).show();
	},
	/*Allocate*/
	openAllocateWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('officeResourceAllocateWindow')).show();
	},
	/*Edit*/
	openEditWindow:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		
		if (record) {
			if(record.data.status=="0"||record.data.status=="2"||record.data.status=="4"){
				var win = grid.up('container').add(Ext.widget('officeResourceEditWindow'));
				win.show();
				win.down('form').getForm().loadRecord(record);
			}else{
				Ext.Msg.alert('提示', "只可以修改'待发起'或'已抢完'或'已抽完'状态的信息！");
			}
		}
	},
	/*Search More*/
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('officeResourceSearchWindow')).show();
	},
	/*combobox选中后控制对应输入（文本框和日期框）框显示隐藏*/
	searchComboboxSelectChuang:function(combo,record,index){
		//alert(record.data.name);
		var searchField = this.lookupReference('searchFieldName').getValue();
		if(searchField==='luckyTime'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchFieldValue2').hide();
			this.lookupReference('searchDataFieldValue').show();
			this.lookupReference('searchDataFieldValue2').show();
		}else if(searchField==='status'){
			this.lookupReference('searchFieldValue').show();
			this.lookupReference('searchFieldValue2').hide();
			this.lookupReference('searchDataFieldValue').hide();
			this.lookupReference('searchDataFieldValue2').hide();
		}else if(searchField==='remark'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchFieldValue2').show();
			this.lookupReference('searchDataFieldValue').hide();
			this.lookupReference('searchDataFieldValue2').hide();
		}
	},
	/********************************************** Submit / Ajax / Rest *****************************************************/
	/*Add Submit*/	
	submitAddForm:function(btn){
		var win    = btn.up('window');
		var form = win.down('form');
		var record = Ext.create('Admin.model.officeResource.OfficeResourceModel');
		var values  =form.getValues();//获取form数据
		record.set(values);
		record.save();
		Ext.data.StoreManager.lookup('officeResourceStore').load();
		win.close();
	},
	/*Allocate Submit*/	
	submitAllocateForm:function(btn){
		var win    = btn.up('window');
		var values  = win.down('form').getValues();//获取form数据
		Ext.Ajax.request({
			url : '/officeResource/allocateResources', 
			method : 'post', 
			params : {
				resourceName :values.resourceName,
				allocatedNum :values.allocatedNum
			}, 
			success: function(response, options) {
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.success){
					Ext.Msg.alert('操作成功', json.msg, function() {
					Ext.data.StoreManager.lookup('officeResourceStore').load();
					win.close();
				});
				}else{
					Ext.Msg.alert('操作失败', json.msg);
				}
			}
		});
	},
	/*Edit Submit*/	
	submitEditForm:function(btn){
		var win    = btn.up('window');
		var store = Ext.data.StoreManager.lookup('officeResourceStore');
		var values  = win.down('form').getValues();//获取form数据
		var record = store.getById(values.id);//获取id获取store中的数据
		record.set(values);//rest put 
		//store.load();
		win.close();
	},
	/*Quick Search*/	
	quickSearch:function(btn){
		var searchField = this.lookupReference('searchFieldName').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		var searchDataFieldValue2 = this.lookupReference('searchDataFieldValue2').getValue();
		var searchFieldValue = this.lookupReference('searchFieldValue').getValue();
		var searchFieldValue2 = this.lookupReference('searchFieldValue2').getValue();

		var store =	btn.up('gridpanel').getStore();
		//var store = Ext.getCmp('userGridPanel').getStore();// Ext.getCmp(）需要在LeavePanel设置id属性
		Ext.apply(store.proxy.extraParams, {remark:"",status:"",startTime:"",endTime:""});
		if(searchField==='luckyTime'){
			Ext.apply(store.proxy.extraParams,{
				startTime:Ext.util.Format.date(searchDataFieldValue, 'Y/m/d H:i:s'),
				endTime:Ext.util.Format.date(searchDataFieldValue2, 'Y/m/d H:i:s')
			});
		}
		else if(searchField==='status'){
			Ext.apply(store.proxy.extraParams,{
				status:searchFieldValue
			});
		}
		else if(searchField==='remark'){
			Ext.apply(store.proxy.extraParams,{
				remark:searchFieldValue2
			});
		}
		store.load({params:{start:0, limit:20, page:1}});
	},
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('officeResourceStore');
		var win = btn.up('window');
		var form = win.down('form');
		var values  = form.getValues();
		Ext.apply(store.proxy.extraParams, {remark:"",status:"",startTime:"",endTime:""});
		Ext.apply(store.proxy.extraParams,{
			remark:values.remark,
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
		if(record.data.status=="0"||record.data.status=="2"||record.data.status=="4"){
			Ext.MessageBox.confirm('提示', '确定要进行删除操作吗？',function(btn, text){
				if(btn=='yes'){
					store.remove(record);
				}
			}, this);
		}else{
			Ext.Msg.alert('提示', "只可以删除'待发起'或'已抢完'或'已抽完'状态的信息！");
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
						if(row.data.status=="0"||record.data.status=="2"||record.data.status=="4"){
							selectIds.push(row.data.id);
						}
					});
					Ext.Ajax.request({ 
						url : '/officeResource/deletes', 
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
	/*开始抢*/	
	startGrabResource:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		var startTime = record.get('startTime');
		var curDate = new Date();
		//var nowTime=Ext.Date.format(curDate, 'Y/m/d H:i:s');
		console.log(startTime.getTime());
		console.log(curDate.getTime());
		var farOfTime = startTime.getTime() - curDate.getTime();
		console.log(farOfTime);
		task_RealTimeMointor = {
			run: function(){
				clearInterval(begin);//关闭setInterval();
				Ext.Ajax.request({
					url:'/officeResource/startGrabResource',
					disableCaching: true, 
					method:'post', 
					params:{id:record.get('id')}, 
					success:function(response, options) {
						var json = Ext.util.JSON.decode(response.responseText);
						if (json.success) {
							Ext.Msg.alert('操作成功', json.msg, function() {
								grid.getStore().reload();
								Ext.TaskManager.stop(task_RealTimeMointor);//停止计时器
							});
						} else {
							Ext.Msg.alert('操作失败', json.msg);
						}
					}
				});
			},
			interval: farOfTime //时间间隔2ms
		};
		begin = setInterval('Ext.TaskManager.start(task_RealTimeMointor)',farOfTime);
	},
	/*抢资源*/	
	grabResource:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({
			url : '/officeResource/grabResource', 
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
	/*开始抽奖*/	
	startLuckyDraw:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		var startTime = record.get('startTime');
		var curDate = new Date();
		//var nowTime=Ext.Date.format(curDate, 'Y/m/d H:i:s');
		console.log(startTime.getTime());
		console.log(curDate.getTime());
		var farOfTime = startTime.getTime() - curDate.getTime();
		console.log(farOfTime);
		task_RealTimeMointor2 = {
			run: function(){
				clearInterval(begin);//关闭setInterval();
				Ext.Ajax.request({
					url : '/officeResource/startLuckyDraw', 
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
			interval: farOfTime //时间间隔2ms
		};
		begin = setInterval('Ext.TaskManager.start(task_RealTimeMointor2)',farOfTime);
	},
	/*抽奖*/	
	luckyDraw:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({
			url : '/officeResource/luckyDraw', 
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
