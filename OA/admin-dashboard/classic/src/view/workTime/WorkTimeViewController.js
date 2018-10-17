Ext.define('Admin.view.workTime.WorkTimeViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.workTimeViewController',
    /********************************************** Controller View *****************************************************/
    /*Add*/
    //toolbar, rowIndex, colIndex
	openAddWindow:function(btn){
		btn.up('gridpanel').up('container').add(Ext.widget('workTimeAddWindow')).show();
	},
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
		toolbar.up('panel').up('container').add(Ext.widget('workTimeSearchWindow')).show();
		
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
		//var record = Ext.create('Admin.model.workTime.WorkTimeModel');
		var values  =form.getValues();//获取form数据
		console.log(values);
		if(values.employeeid=="")
		{
			Ext.toast("员工编号不能为空");
			return false;
		}
		if(values.hour=="")
		{
			Ext.toast("工时不能为空");
			return false;
		}
		if(values.StartDate==""){
			Ext.toast("开始时间不能为空");
			return false;
		}
		if(values.EndDate==""){
			Ext.toast("结束时间不能为空");
			return false;
		}
		//record.set(values);
		//record.save();
		//Ext.data.StoreManager.lookup('workTimeGridStroe').load();
		//var container=btn.up("window").up("container");
		//console.log(btn.up('window').up('container'));
		win.close();
		win.destroy();
//		Ext.Ajax.request({ 
//			url : 'http://localhost:8080/workTime/savemore', 
//			method : 'post', 
//			params : {
//				StartDate:values.StartDate,
//				EndDate:values.EndDate,
//				employeeid:values.employeeid,
//				hour:values.hour
//			}, 
//			success: function(response,options) {
//				//console.log(response.responseText);
//				//var arr=Ext.decode(response.responseText);
//				var arr=response.responseText;
//				console.log("1111"+arr);
//				//Ext.data.StoreManager.lookup("holidayGridStroe").setData(arr);
//				
//				Ext.data.StoreManager.lookup("holidayGridStroe").loadData(arr,true);
//				console.log("000"+Ext.data.StoreManager.lookup("holidayGridStroe").getData());
//			}
//		});
		//begin = setInterval("Ext.TaskMgr.start(函数)",10000); //定时器
		//Ext.toast('Data saved');
		Ext.toast("已经过滤掉填报过的工时了", "提示", "t", "fa fa-pencil");
		var store=Ext.data.StoreManager.lookup("holidayGridStroe");
		//Ext.apply(store.proxy.extraParams, {employeeid:"",StartDate:"",EndDate:"",hour:""});
		Ext.apply(store.proxy.extraParams, {
			employeeid:values.employeeid,
			StartDate:values.StartDate,
			EndDate:values.EndDate,
			hour:values.hour
		});
		store.load({params:{start:0, limit:20, page:1}});
		
		console.log(Ext.ClassManager.getName(store.getData()));
		console.log(store.getData());
		//store.setData();
		//Ext.data.StoreManager.lookup("holidayGridStroe").load();
		var gridTest = new Ext.grid.GridPanel({
		    region: 'north',
		    //id:"contactsGrid",
		    plugins: {
		        cellediting: {
		            clicksToEdit: 1
		        }
		    },
		    //bind: '{workTimes}',
		    store:store,
		    border: false,
		    columns: [
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeid',text: '员工编号',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeName',text: '员工姓名',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'departmentName',text: '部门',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'hour',text: '当天的上班时间 (单位：H)',flex: 1,
					editor: {
			            allowBlank: false
			        },
//			        listeners:{
//			        	focusleave:function(gridcolumn, event, eOpts ){
//			        		
//			        	}
//			        }
				},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'ifholiday',text: '工作/节假日',flex: 1,
					
					renderer:function(val){
						if(val=='0'){
							return '<span>工作日</span>';
						}else if(val=='1'){
							return '<span style="color:orange">周六日</span>';
						}else if(val=='2'){
							return '<span style="color:orange">节假日</span>';
						}else if(val=='3'){
							return '<span style="color:red">请假</span>';
						}
					}
				},
				{xtype: 'datecolumn',cls: 'content-column',dataIndex: 'date',text: '日期',formatter: 'date("Y/m/d")',flex:1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'status',text: '状态',flex: 1,
					renderer:function(val){
						if(val=='0'){
							return '<span style="color:blue;">待申请</span>';
						}else if(val=='2'){
							return '<span style="color:orange;">待审批</span>';
						}else if(val=='3'){
							return '<span style="color:red;">审批通过</span>';
						}else if(val=='4'){
							return '<span style="color:red;">审批不通过</span>';
						}
					}
				},
//                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: '操作',tooltip: 'edit ',flex: 1,
//                    items: [
//                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,handler: 'openEditWindow',tooltip: '修改申请'},
//                        {xtype: 'button',iconCls: 'x-fa fa-close'	,handler: 'deleteOneRow',tooltip:'取消申请'},
//                        {xtype: 'button',iconCls: 'x-fa fa-star'	,handler: 'starWorktimeProcess',tooltip: '发起申请'}
//                    ]
//                }
            ],
            tbar:[{
            	xtype:'panel',
            	html:'<span style="color:blue;">如果已经申请过的工时，会过滤掉，只显示未申请的工时</span><br><span style="color:blue;">员工只能修改是工作日的当天上班时间</span><br><span style="color:blue;">点击要修改的当天上班时间单元格进行修改</span><br><span style="color:blue;">修改完后点击确认提交开始申请</span>',
            	
            }],
            listeners:{
            	cellclick:function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts ){
            		var rows=grid.getStore().getAt(rowIndex).get("ifholiday");
            		console.log(rows);
            		if(rows!="0"){
            			//console.log("00");
            			return false;//代表事件不生效
            		}
            		console.log(td);
            		console.log(record);
            		console.log(cellIndex);
            		console.log(record);
            		console.log(tr);
            		console.log(rowIndex);
            		console.log(e);
            		console.log(eOpts);
            	},
            	
            	/*afterenderer:function(grid){
            		console.log("111");
            		var store=grid.getStore();
            		var columns=grid.getColumns();
            		var records=store.getData().getSource();
            		Ext.Array.each(records, function(rec, index, recordsItSelf) {
            		    console.log(rec);
            			//console.log(name);
            		});
            	},
            	/*added:function(grid){
            		/*console.log("111");
            		var store=grid.getStore();
            		var columns=grid.getColumns();
            		var records=store.getData().getSource();
            		var data=store.getData();
            		//var items=data.getItems();
            		console.log(store);
            		console.log(columns);
            		console.log(records);
            		console.log(data);
            		data.each(function(items,index,len){
            			console.log(items);
            		});
            		console.log(data);
            		for(i=0;i<data.length;i++){
            			var temp=data.getAt(i);
            			console.log(temp);
            		}*/
            		/*Ext.Array.each(items, function(rec, index, recordsItSelf) {
            		    console.log(rec);
            		    //console.log(store.getData());
            			//console.log(name);
            		});
            	},
            	afterlayoutanimation:function(grid){
            		console.log("111");
            		var store=grid.getStore();
            		var columns=grid.getColumns();
            		var records=store.getData().getSource();
            		Ext.Array.each(records, function(rec, index, recordsItSelf) {
            		    console.log(rec);
            			//console.log(name);
            		});
            	}*/
            },
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                displayInfo: true,
                
            }],
		    width: 800,
		    height: 500,
		    frame: true
		});
		//store.setListeners(update:function(this, record, operation, modifiedFieldNames, details, eOpts))
		
//		store.setListeners({load:function(store,records){
//			console.log("111");
//			var grid=gridTest;
//    		var store=grid.getStore();
//    		var columns=grid.getColumns();
//    		var records=store.getData().getSource();
//    		var data=store.getData();
//    		//var items=data.getItems();
//    		console.log(store);
//    		console.log(columns);
//    		console.log(records);
//    		console.log(data);
//    		data.each(function(items,index,len){
//    			console.log(items);
//    		});
//    		console.log(data);
//    		for(i=0;i<data.length;i++){
//    			var temp=data.getAt(i);
//    			console.log(temp);
//    		}
//		}});
		
		console.log(gridTest.getStore());
		console.log(gridTest.getStore().getData());
		var winChooseGoods = new Ext.Window({
			xtype:"gridTestWindow",
		    title: '工时:(根据自己实际情况填写工时)',
		    layout: 'border',
		    width: 800,
		    height: 550,
		    modal:true,
		    closeAction: 'hide',
		    plain: true,
		    items: [gridTest],
		    buttons: [{
		        text: '确定',
		        handler: function (btn) {
		        	var listRecord = new Array();
//		        	var conGrid = Ext.getCmp('contactsGrid');
//		        	var conStore=conGrid.getStore();
//		        	
//		        	conGrid.getStore().each(function(record){ 
//		        		var value = record.get('ifholiday'); 
//		        	});
		        	var record=store.getAt(1);
		        	console.log(store.getData());
		        	//var data=record.getData();
		        	console.log(record);
		        	console.log(record.getData());
		        	console.log(Ext.ClassManager.getName(record));
		        	//delete data.id;
		        	var records = []; 
		        	store.each(function(r){ 
		        		var temp=r.copy().getData();
		        		temp.date=Ext.util.Format.date(temp.date,'Y/m/d')
		        		delete temp.id;
		        		//temp.id=null;
		        		records.push(temp); 
		        	}); 
		        	console.log(records);
		        	Ext.Ajax.request({ 
						url : 'http://localhost:8080/workTime/forApproval', 
						method : 'post', 
						/*params : {
							records:records
						}, */
						jsonData:records,
						success: function(response, options) {
							var json = Ext.util.JSON.decode(response.responseText);
							if(json.success){
								Ext.Msg.alert('操作成功', json.msg, function() {
								Ext.data.StoreManager.lookup("workTimeGridStroe").load();
								winChooseGoods.close();
								winChooseGoods.destroy();
							});
							}else{
								Ext.Msg.alert('操作失败', json.msg);
							}
						}
					});
		        	/*for(var i=0;i<store2.getCount();i++){
		        		var record=store2.getAt(i);
		        		delete record.getData().id;
		        		listRecord.push(record);
		        	}*/
		        	
		        	/*for(i=0;i<records.length;i++){
		        		delete records[i].id;
		        	}*/
		        	console.log(records);
		        }
		    }, {
		        text: '取消',
		        tabIndex: 12,
		        handler: function () {
		            winChooseGoods.close();
		            winChooseGoods.destroy();
		        }
		    }]
		}).show();
		//btn.up("window").up("container").add(Ext.widget("gridTestWindow")).show();
		//container.add(E).show();
	},
	/*Edit Submit*/	
	submitEditForm:function(btn){
		var win    = btn.up('window');
		var store = Ext.data.StoreManager.lookup('workTimeGridStroe');
		var values  = win.down('form').getValues();//获取form数据
		var record = store.getById(values.id);//获取id获取store中的数据
		record.set(values);//rest put 
		//store.load();
		win.close();
	},
		
	/*Quick Search*/	
	quickSearch:function(btn){
		var store =	btn.up('gridpanel').getStore();
		Ext.apply(store.proxy.extraParams, {employeeid:"",employeeName:"",departmentid:"",StartDate:"",hour:"",EndDate:"",status:""});
		var searchField = this.lookupReference('searchFieldName').getValue();
		
		var searchValue = this.lookupReference('searchFieldValue').getValue();
		var searchComboValue = this.lookupReference('departmentBox').getValue();
		var searchStatusComboValue = this.lookupReference('statusCombo').getValue();
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue').getValue();
		var searchDataFieldValue2 = this.lookupReference('searchDataFieldValue2').getValue();
		if(searchField==='employeeid'){
			Ext.apply(store.proxy.extraParams, {employeeid:searchValue});
		}
		if(searchField==='departmentName'){
			Ext.apply(store.proxy.extraParams, {departmentid:searchComboValue});
		}
		if(searchField==='status'){
			Ext.apply(store.proxy.extraParams, {status:searchStatusComboValue});
		}
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
		var searchComboValue = this.lookupReference('departmentBox');
		var searchStatusComboValue = this.lookupReference('statusCombo');
		var searchDataFieldValue = this.lookupReference('searchDataFieldValue');
		var searchDataFieldValue2 = this.lookupReference('searchDataFieldValue2');
		//console.log(Ext.ClassManager.getName(searchValue));
		if(newValue=="employeeid"){
			searchComboValue.setHidden(true);
			searchValue.setHidden(false);
			searchStatusComboValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
			searchDataFieldValue2.setHidden(true);
		}
		else if(newValue=="date"){
			searchStatusComboValue.setHidden(true);
			searchDataFieldValue.setHidden(false);
			searchDataFieldValue2.setHidden(false);
			searchComboValue.setHidden(true);
			searchValue.setHidden(true);
			
		}
		else if(newValue=="departmentName"){
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
		}else{
			searchComboValue.setHidden(true);
			searchValue.setHidden(true);
			searchDataFieldValue.setHidden(true);
			searchDataFieldValue2.setHidden(true);
		}
		
	},
	
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('workTimeGridStroe');
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
						url : 'http://localhost:8080/workTime/deletes', 
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
    
    /*Star worktime Process*/	
    starWorktimeProcess:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
		if(record.data.status=="0"){
			Ext.Ajax.request({ 
				url : 'http://localhost:8080/workTime/startApproval', 
				method : 'post', 
				params : {
					id :record.get("id"),
					status:"2"
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
		}else {
			Ext.Msg.alert("提示","已经提交过申请");
		}
		
	},	
	/*Check*/	
	onCheckButton:function(grid, rowIndex, colIndex){
		Ext.Msg.alert("Title","Click Check Button");
	}
});