Ext.define('Admin.view.workTimeApproval.WorkTimeApprovalViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.workTimeApprovalViewController',
    /********************************************** Controller View *****************************************************/
   
    /*Edit*/
	openEditWindow:function(grid, rowIndex, colIndex){
         var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		if (record ) {
			if(record.data.status=='0'){
				var win = grid.up('container').add(Ext.widget('workTimeEditWindow'));
				win.show();
				win.down('form').getForm().loadRecord(record);
			}else{
				Ext.Msg.alert("提示","只能修改还没申请的工时！");
			}
		}
	},
	/*Search More*/	
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('workTimeApprovalSearchWindow')).show();
		
	},
	/*combobox选中后控制对应输入（文本框和日期框）框显示隐藏*/
	searchComboboxSelectChuang:function(combo,record,index){
		//alert(record.data.name);
		var searchField = this.lookupReference('searchFieldName').getValue();
		if(searchField==='entryTime'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchDataFieldValue').show();
		}
		else{
			this.lookupReference('searchFieldValue').show();
			this.lookupReference('searchDataFieldValue').hide();
		}
		
	},
	/********************************************** Submit / Ajax / Rest *****************************************************/
	
	
		
	/*Quick Search*/	
	quickSearch:function(btn){
		var store =	btn.up('gridpanel').getStore();
		Ext.apply(store.proxy.extraParams, {employeeid:"",employeeName:"",departmentid:"",StartDate:"",hour:"",EndDate:"",status:""});
		var searchField = this.lookupReference('searchFieldName').getValue();
		
		var searchValue = this.lookupReference('searchFieldValue').getValue();
		//var searchComboValue = this.lookupReference('departmentBox').getValue();
		//var searchStatusComboValue = this.lookupReference('statusCombo').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		var searchDataFieldValue2 = this.lookupReference('searchDataFieldValue2').getValue();
		if(searchField==='employeeid'){
			Ext.apply(store.proxy.extraParams, {employeeid:searchValue});
		}
		/*if(searchField==='departmentName'){
			Ext.apply(store.proxy.extraParams, {departmentid:searchComboValue});
		}
		if(searchField==='status'){
			Ext.apply(store.proxy.extraParams, {status:searchStatusComboValue});
		}*/
		if(searchField==='date'){
			Ext.apply(store.proxy.extraParams,{
				StartDate:Ext.util.Format.date(searchDataFieldValue, 'Y/m/d'),
				EndDate:Ext.util.Format.date(searchDataFieldValue2, 'Y/m/d')
			});
		}
		store.load({params:{start:0, limit:20, page:1}});
	},
	tbarSelectChange:function(box,newValue,oldValue,eOpts){
		console.log("12356");
		var searchValue = this.lookupReference('searchFieldValue');
		//var searchComboValue = this.lookupReference('departmentBox');
		//var searchStatusComboValue = this.lookupReference('statusCombo');
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue');
		var searchDataFieldValue2 = this.lookupReference('searchDataFieldValue2');
		//console.log(Ext.ClassManager.getName(searchValue));
		if(newValue=="employeeid"){
			//searchComboValue.setHidden(true);
			searchValue.setHidden(false);
			//searchStatusComboValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
			searchDataFieldValue2.setHidden(true);
		}
		else if(newValue=="date"){
		//	searchStatusComboValue.setHidden(true);
			searchDataFieldValue.setHidden(false);
			searchDataFieldValue2.setHidden(false);
		//	searchComboValue.setHidden(true);
			searchValue.setHidden(true);
			
		}
		/*else if(newValue=="departmentName"){
			searchStatusComboValue.setHidden(true);
			searchComboValue.setHidden(false);
			searchValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
			searchDataFieldValue2.setHidden(true);
		}else if(newValue=="status"){
			searchStatusComboValue.setHidden(false);
			searchComboValue.setHidden(true);
			searchValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
			searchDataFieldValue2.setHidden(true);
		}*/else{
			//searchComboValue.setHidden(true);
			searchValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
			searchDataFieldValue2.setHidden(true);
		}
		
	},
	
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('workTimeApprovalStore');
		var win = btn.up('window');
		var form = win.down('form');
		var values  = form.getValues();
		Ext.apply(store.proxy.extraParams, {employeeid:"",employeeName:"",departmentid:"",hour:"",date:""});
		Ext.apply(store.proxy.extraParams,{
			employeeid:values.employeeid,
			employeeName:values.employeeName,
			departmentid:values.departmentid,
			hour:values.hour,
			date:Ext.util.Format.date(values.date, 'Y/m/d')
		});
		store.load({params:{start:0, limit:20, page:1}});
		win.close();
	},
	/*Delete One Row*/	
	deleteOneRow:function(grid, rowIndex, colIndex){
	   Ext.MessageBox.confirm('提示', '确定要进行删除操作吗？数据将无法还原！',
  			function(btn, text){
            	if(btn=='yes'){
            		var store = grid.getStore();
					var record = store.getAt(rowIndex);
					store.remove(record);//DELETE //http://localhost:8081/workTime/112
					//store.sync();
				}
        	}
        , this);
	},
	/*批量审批通过*/
	approvalMoreRows:function(btn, rowIndex, colIndex){
		var grid = btn.up('gridpanel');
		var selModel = grid.getSelectionModel();
        if (selModel.hasSelection()) {
            Ext.Msg.confirm("警告", "确定要同意申请吗？", function (button) {
                if (button == "yes") {
                    var rows = selModel.getSelection();
                    var selectIds = []; //要删除的id
                    Ext.each(rows, function (row) {
                        selectIds.push(row.data.id);
                    });
                  	Ext.Ajax.request({ 
						url : 'http://localhost:8080/workTime/deletesApproval', 
						method : 'post', 
						params : { 
							//ids[] :selectIds
							status:"3",
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
	/**/
	/*Delete More Rows*/	
	deleteMoreRows:function(btn, rowIndex, colIndex){
		var grid = btn.up('gridpanel');
		var selModel = grid.getSelectionModel();
        if (selModel.hasSelection()) {
            Ext.Msg.confirm("警告", "确定要驳回申请吗？", function (button) {
                if (button == "yes") {
                    var rows = selModel.getSelection();
                    var selectIds = []; //要删除的id
                    Ext.each(rows, function (row) {
                        selectIds.push(row.data.id);
                    });
                  	Ext.Ajax.request({ 
						url : 'http://localhost:8080/workTime/deletesApproval', 
						method : 'post', 
						params : { 
							//ids[] :selectIds
							status:"4",
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
    
    /*Star worktime Process*/	
    passApproval:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({ 
			url : 'http://localhost:8080/workTime/startApproval', 
			method : 'post', 
			params : {
				id :record.get("id"),
				status:"3"
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
		backApproval:function(grid, rowIndex, colIndex){
			var record = grid.getStore().getAt(rowIndex);
			Ext.Ajax.request({ 
				url : 'http://localhost:8080/workTime/startApproval', 
				method : 'post', 
				params : {
					id :record.get("id"),
					status:"4"
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
	/*Check*/	
	onCheckButton:function(grid, rowIndex, colIndex){
		Ext.Msg.alert("Title","Click Check Button");
	}
});