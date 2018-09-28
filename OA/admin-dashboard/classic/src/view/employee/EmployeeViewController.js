﻿Ext.define('Admin.view.employee.EmployeeViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.employeeViewController',
    /********************************************** Controller View *****************************************************/
    /*Add*/
	openAddWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('employeeAddWindow')).show();
	},
    /*Edit*/
	openEditWindow:function(grid, rowIndex, colIndex){
         var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		if (record ) {
			var win = grid.up('container').add(Ext.widget('employeeEditWindow'));
			win.show();
			win.down('form').getForm().loadRecord(record);
		}
	},
	/*Search More*/	
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('employeeSearchWindow')).show();
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
	/*Add Submit*/	
	submitAddForm:function(btn){
		var win    = btn.up('window');
		var form = win.down('form');
		var record = Ext.create('Admin.model.employee.EmployeeModel');
		var values  =form.getValues();//获取form数据
		record.set(values);
		record.save();
		Ext.data.StoreManager.lookup('employeeGridStroe').load();
		win.close();
	},
	/*Edit Submit*/	
	submitEditForm:function(btn){
		var win    = btn.up('window');
		var store = Ext.data.StoreManager.lookup('employeeGridStroe');
		var values  = win.down('form').getValues();//获取form数据
		var record = store.getById(values.id);//获取id获取store中的数据
		record.set(values);//rest put 
		//store.load();
		win.close();
	},
		
	/*Quick Search*/	
	quickSearch:function(btn){
		var store =	btn.up('gridpanel').getStore();
		Ext.apply(store.proxy.extraParams, {id:"",departmentid:"",entryTime:""});
		var searchField = this.lookupReference('searchFieldName').getValue();
		
		var searchValue = this.lookupReference('searchFieldValue').getValue();
		var searchComboValue = this.lookupReference('departmentBox').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		
		if(searchField==='id'){
			Ext.apply(store.proxy.extraParams, {id:searchValue});
		}
		if(searchField==='departmentName'){
			Ext.apply(store.proxy.extraParams, {departmentid:searchComboValue});
		}
		if(searchField==='entryTime'){
			Ext.apply(store.proxy.extraParams,{
				entryTime:Ext.util.Format.date(searchDataFieldValue, 'Y/m/d')
			});
		}
		store.load({params:{start:0, limit:20, page:1}});
	},
	tbarSelectChange:function(box,newValue,oldValue,eOpts){
		console.log("12356");
		var searchValue = this.lookupReference('searchFieldValue');
		var searchComboValue = this.lookupReference('departmentBox');
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue');
		//console.log(Ext.ClassManager.getName(searchValue));
		if(newValue=="id"){
			searchComboValue.setHidden(true);
			searchValue.setHidden(false);
			searchDataFieldValue.setHidden(true);
		}
		else if(newValue=="entryTime"){
			searchDataFieldValue.setHidden(false);
			searchComboValue.setHidden(true);
			searchValue.setHidden(true);
			
		}
		else if(newValue=="departmentName"){
			searchComboValue.setHidden(false);
			searchValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
		}else{
			searchComboValue.setHidden(true);
			searchValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
		}
		
	},
	
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('employeeGridStroe');
		var win = btn.up('window');
		var form = win.down('form');
		var values  = form.getValues();
		Ext.apply(store.proxy.extraParams, {id:"",departmentid:"",entryTime:""});
		Ext.apply(store.proxy.extraParams,{
			id:values.id,
			departmentid:values.departmentid,
			entryTime:values.entryTime,
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
					store.remove(record);//DELETE //http://localhost:8081/employee/112
					//store.sync();
				}
        	}
        , this);
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
                        selectIds.push(row.data.id);
                    });
                  	Ext.Ajax.request({ 
						url : 'http://localhost:8080/employee/deletes', 
						method : 'post', 
						params : { 
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

	/*Check*/	
	onCheckButton:function(grid, rowIndex, colIndex){
		var rec = grid.getStore().getAt(rowIndex);
		var win = Ext.widget('employeeCheckWindow');;
		win.show();
		win.down("form").getForm().loadRecord(rec);
		console.log(Ext.ClassManager.getName(win.down("form")));
	}
});