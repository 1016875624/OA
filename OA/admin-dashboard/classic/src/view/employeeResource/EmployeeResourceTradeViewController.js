Ext.define('Admin.view.employeeResource.EmployeeResourceTradeViewController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.employeeResourceTradeViewController',
	/*Search More*/
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('employeeResourceSearchWindow')).show();
	},
	openTradeWindow:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		if (record) {
			if(record.data.status=="1"){
				var win = grid.up('container').add(Ext.widget('employeeResourceTradeWindow'));
				win.show();
				win.down('form').getForm().loadRecord(record);
			}else{
				Ext.Msg.alert('提示', "只可以交易'已拥有'状态的信息！");
			}
		}
	},
	/*combobox选中后控制对应输入（文本框和日期框）框显示隐藏*/
	searchComboboxSelectChuang:function(combo,record,index){
		//alert(record.data.name);
		var searchField = this.lookupReference('searchFieldName').getValue();
		if(searchField==='recentChangeTime'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchDataFieldValue').show();
		}else if(searchField==='status'){
			this.lookupReference('searchFieldValue').show();
			this.lookupReference('searchDataFieldValue').hide();
		}
	},
	/********************************************** Submit / Ajax / Rest *****************************************************/
	/*Quick Search*/	
	quickSearch:function(btn){
		var searchField = this.lookupReference('searchFieldName').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		var searchFieldValue = this.lookupReference('searchFieldValue').getValue();
		var store =	btn.up('gridpanel').getStore();
		
		Ext.apply(store.proxy.extraParams, {status:"",recentChangeTime:""});
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
		store.load({params:{start:0, limit:20, page:1}});
	},
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('employeeResourceTradeStore');
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
	submitTradeForm:function(btn){
		var win    = btn.up('window');
		var values  = win.down('form').getValues();//获取form数据
		Ext.Ajax.request({
			url : '/employeeResource/trade', 
			method : 'post', 
			params : {
				id:values.id,
				buyNum:values.buyNum,
				resourceNameForTrade :values.resourceNameForTrade,
				sellNum:values.sellNum
			}, 
			success: function(response, options) {
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.success){
					Ext.Msg.alert('操作成功', json.msg, function() {
					Ext.data.StoreManager.lookup('employeeResourceStore').load();
					win.close();
				});
				}else{
					Ext.Msg.alert('操作失败', json.msg);
				}
			}
		});
	}
});
