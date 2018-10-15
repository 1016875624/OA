﻿Ext.define('Admin.view.salary.SalaryViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.salaryViewController',
    /********************************************** Controller View *****************************************************/
    /*Add*/
	openAddWindow:function(grid, rowIndex, colIndex){
		grid.up('container').add(Ext.widget('salaryAddWindow')).show();
	},
    /*Edit*/
	openEditWindow:function(grid, rowIndex, colIndex){
         var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		if (record ) {
			var win = grid.up('container').add(Ext.widget('salaryEditWindow'));
			win.show();
			win.down('form').getForm().loadRecord(record);
		}
	},
	/*Search More*/	
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('grid').up('container').add(Ext.widget('salarySearchWindow')).show();
	},
	/*combobox选中后控制对应输入（文本框和日期框）框显示隐藏*/
	searchComboboxSelectChuang:function(combo,record,index){
		//alert(record.data.name);
		var searchField = this.lookupReference('searchFieldName').getValue();
		if(searchField==='createTime'){
			this.lookupReference('searchFieldValue').hide();
			this.lookupReference('searchDataFieldValue').show();
		}else{
			this.lookupReference('searchFieldValue').show();
			this.lookupReference('searchDataFieldValue').hide();
		}
		
	}
	,
	/********************************************** Submit / Ajax / Rest *****************************************************/
	/*Add Submit*/	
	submitAddForm:function(btn){
		var win    = btn.up('window');
		var form = win.down('form');
		var record = Ext.create('Admin.model.salary.SalaryModel');//创建了一个Model,它就是一行记录，里面是空的，通过表单取到所有的值。
		var values  =form.getValues();//获取form数据
		record.set(values);//然后将值赋值给recored这个空的Model。
		record.save();//赋完值后保存。
		Ext.data.StoreManager.lookup('salaryGridStroe').load();//然后找到orderGridStore后加载，相当于自动更新。
		win.close();
	},
	/*Edit Submit*/	
	submitEditForm:function(btn){
		var win    = btn.up('window');//向上找到window
		var store = Ext.data.StoreManager.lookup('salaryGridStroe');//获取到数据源
		var values  = win.down('form').getValues();//窗口向下找找到表单通过getValues就可以获取form中填的所有数据，value是键值对集合。
		var record = store.getById(values.id);//获取id获取store中的数据
		record.set(values);//覆盖所有记录，当set的时候rest就会用put 
		//store.load();//
		win.close();
	},
		
	/*Quick Search*/	
	quickSearch:function(btn){
		var searchField = this.lookupReference('searchFieldName').getValue();
		var searchValue = this.lookupReference('searchFieldValue').getValue();
		var store =	btn.up('gridpanel').getStore();
		//var store = Ext.getCmp('userGridPanel').getStore();// Ext.getCmp(）需要在OrderPanel设置id属性
		//清空查询条件
		Ext.apply(
			store.proxy.extraParams,
			{employeeid:"",employeeName:"",id:"",preSal:"",
				sufSal:"",preBonus:"",sufBonus:"",
				preWorkMonth:"",sufWorkMonth:"",
				preWorktimeMoney:"",sufWorktimeMoney:"",
				preSubsidy:"",sufSubsidy:""});

        val=JSON.parse('{"'+searchField+'":"'+searchValue+'"}');
        Ext.apply(store.proxy.extraParams,val);
		store.load({params:{start:0, limit:20, page:1}});
	},
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('salaryStroe');
		var win = btn.up('window');
		var form = win.down('form');
		var values  = form.getValues();
		//清空查询条件
        Ext.apply(
            store.proxy.extraParams,
            {employeeid:"",employeeName:"",id:"",preSal:"",
                sufSal:"",preBonus:"",sufBonus:"",
                preWorkMonth:"",sufWorkMonth:"",
                preWorktimeMoney:"",sufWorktimeMoney:"",
                preSubsidy:"",sufSubsidy:""});
		Ext.apply(store.proxy.extraParams,form.getForm().getValues());
		store.load({params:{start:0, limit:20, page:1}});
		win.close();
	},
	/*Delete One Row*/	
	deleteOneRow:function(grid, rowIndex, colIndex){ 
	   Ext.MessageBox.confirm('提示', '确定要进行删除操作吗？数据将无法还原！', //消息确认提示框
  			function(btn, text){
            	if(btn=='yes'){//加了个判断，如果点击yes的时候删除
            		var store = grid.getStore();//grid.getStore取到Store
					var record = store.getAt(rowIndex);//取到store选中的记录，然后在store中remove掉这条记录。romove掉后，它就会自动发get然后把ID绑定到后面。
					store.remove(record);//DELETE //http://localhost:8081/order/112，
					//store.sync();因为是自动同步的，所以这行代码不用写
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
						url : '/salary/deletes', 
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

	/*Disable*/	
	onDisableButton:function(grid, rowIndex, colIndex){
		Ext.Msg.alert("Title","Click Disable Button");
	}
});