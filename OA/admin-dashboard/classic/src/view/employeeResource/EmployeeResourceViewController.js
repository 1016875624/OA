Ext.define('Admin.view.employeeResource.EmployeeResourceViewController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.employeeResourceViewController',
	/*Search More*/
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('employeeResourceSearchWindow')).show();
	},
	/*combobox选中后控制对应输入（文本框和日期框）框显示隐藏*/
	searchComboboxSelectChuang:function(combo,record,index){
		//alert(record.data.name);
		var searchField = this.lookupReference('searchFieldName').getValue();
		if(searchField==='recentChangeTime'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchFieldValue3').hide();
			this.lookupReference('searchDataFieldValue').show();
		}else if(searchField==='status'){
			this.lookupReference('searchFieldValue').show();
			this.lookupReference('searchFieldValue3').hide();
			this.lookupReference('searchDataFieldValue').hide();
		}else if(searchField==='resourceName'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchFieldValue3').show();
			this.lookupReference('searchDataFieldValue').hide();
		}
	},
	/********************************************** Submit / Ajax / Rest *****************************************************/
	/*Quick Search*/	
	quickSearch:function(btn){
		var searchField = this.lookupReference('searchFieldName').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		var searchFieldValue = this.lookupReference('searchFieldValue').getValue();
		var searchFieldValue3 = this.lookupReference('searchFieldValue3').getValue();
		var store =	btn.up('gridpanel').getStore();
		
		Ext.apply(store.proxy.extraParams, {status:"",recentChangeTime:"",resourceName:""});
		if(searchField==='recentChangeTime'){
			Ext.apply(store.proxy.extraParams,{
				recentChangeTime:Ext.util.Format.date(searchDataFieldValue, 'Y/m/d H:i:s'),
			});
		}
		else if(searchField==='status'){
			Ext.apply(store.proxy.extraParams,{
				status:searchFieldValue
			});
		}
		else if(searchField==='resourceName'){
			Ext.apply(store.proxy.extraParams,{
				resourceName:searchFieldValue3
			});
		}
		store.load({params:{start:0, limit:20, page:1}});
	},
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('employeeResourceStore');
		var win = btn.up('window');
		var form = win.down('form');
		var values  = form.getValues();
		Ext.apply(store.proxy.extraParams, {status:"",recentChangeTime:""});
		Ext.apply(store.proxy.extraParams,{
			status:values.status,
			recentChangeTime:Ext.util.Format.date(values.recentChangeTime, 'Y/m/d H:i:s')
		});
		store.load({params:{start:0, limit:20, page:1}});
		win.close();
	},
	sureTrade:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({ 
			url : '/employeeResource/sureTrade', 
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
	cancelTrade:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({ 
			url : '/employeeResource/cancelTrade', 
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
