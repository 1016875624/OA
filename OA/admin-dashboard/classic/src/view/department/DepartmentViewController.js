var department=Ext.ComponentQuery.query("department");
Ext.define('Admin.view.department.DepartmentViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.departmentViewController',
    
	onEditButton:function(grid, rowIndex, colIndex){
		//获取本列的数据
		var rec = grid.getStore().getAt(rowIndex);
        Ext.Msg.alert('Title', rec.get('fullname'));
	},
	gridDelete:function(grid, rowIndex, colIndex){
		//Ext.Msg.alert("Title","Click Delete Button");
		Ext.MessageBox.confirm('提示', '确定要进行删除操作吗？数据将无法还原！',
  			function(btn, text){
            	if(btn=='yes'){
            		var store = grid.getStore();
					var record = store.getAt(rowIndex);
					store.remove(record);//DELETE//http://localhost:8081/order/112
					//store.sync();
				}
        	}
        , this);
	
	},
	gridDisable:function(grid, rowIndex, colIndex){
		Ext.Msg.alert("Title","Click Disable Button");
	},
	
	/**********头部事件*********/
	openSearchWindow:function(btn){
		var win=Ext.widget('departmentSearchWindow');
		//win.setTitle('高级查询');
		btn.up('gridpanel').up('container').add(win);
		//win.show();
		/*win.down('button[text=save]').setHandler(function(){
			Ext.Msg.alert("search","you click the button named save");
		});*/
		
		win.down('button[text=save]').setHandler(this.highLevelSearch);
	},
	clickAddBtn:function(btn){
		var win=Ext.widget('departmentWindow');
		win.setTitle('添加数据');
		btn.up('gridpanel').up('container').add(win);
		var containner=btn.up('gridpanel').up('container');
		var idField=win.getComponent("department_form_id");
		var ifield=Ext.getCmp("department_form_id");
		console.log(Ext.ClassManager.getName(ifield));
		ifield.setHidden(false);
		//win.down('datefield').format='Y/m/d H:i:s';
		var grid=btn.up('gridpanel');
		var form1=win.down('form').getForm();
		win.down('button[text=save]').setHandler(function(){
			//Ext.Msg.alert("Add","you click the button named save");
			var record = Ext.create('Admin.model.department.DepartmentModel');
			var values  =form1.getValues();//获取form数据
			console.log(values);
           	record.set(values);
			console.log(record);
            record.save();
			win.close();
			grid.getStore().load();
			
		});
	},
	
	
	
	
	
	gridModify:function(grid, rowIndex, colIndex){
		var rec=grid.getStore().getAt(rowIndex);
		var win=Ext.widget('departmentWindow');
		grid.up('container').add(win);
		win.show();
		console.log(Ext.ClassManager.getName(rec));
		console.log(rec);
		console.log(rec.data);
		console.log(rec.data.id);
		console.log(Ext.ClassManager.getName(rec.get('name')));
		//console.log(Ext.ClassManager.getName(win));
		//console.log(Ext.ClassManager.getName(win.down('form')));
		//console.log(Ext.ClassManager.getName(win.down('form')));
		//console.log(Ext.typeOf(win.down('form')));
		//win.down('datefield').format='Y/m/d H:i:s';
		win.down('form').loadRecord(rec);
		//console.log(Ext.ClassManager.getName(Ext.getCmp('userWindowSave')));
		/*Ext.getCmp('userWindowSave').setHandler(function(){
			Ext.Msg.alert("","you click the button named save");
		});*/
		var store = Ext.data.StoreManager.lookup('departmentGridStroe');
		console.log(Ext.ClassManager.getName(win.down('button')));
		win.down('button[text=save]').setHandler(function(){
			//Ext.Msg.alert("tips","you click the button named save");
			var values  = win.down('form').getValues();//获取form数据
        	var record = store.getById(values.id);//获取id获取store中的数据
        	record.set(values);
			//rec.data.orderDate.format='Y/m/d H:i:s';
			
			win.close();
			store.load();
			
		});
		
		console.log(Ext.ClassManager.getName(Ext.get('userWindowSave')));
	},
	userClickDeleteMore:function(btn){
		var grid= btn.up('gridpanel');
		console.log(btn);
		console.log(grid);
		console.log(Ext.ClassManager.getName(grid));
		var selects= grid.getSelection();
		var store=grid.getStore();
		store.remove(selects);
	},
	userClickDeleteMore1:function(btn){
		var grid= btn.up('gridpanel');
		console.log(btn);
		console.log(grid);
		console.log(Ext.ClassManager.getName(grid));
		var selects= grid.getSelection();
		var store=grid.getStore();
		console.log(selects);
		//var index=selects.
		//store.remove(selects);
		var ids=new Array();
		Ext.Array.each(selects,function(val, index, countriesItSelf){
			console.log(val);
			console.log(val.data);
			console.log(val.data.id);
			ids.push(val.get("id"));
		});
		console.log(ids);
		Ext.Ajax.request({
			url: 'http://localhost:8080/order/deleteMore',
			method:'post',
			params:{id:ids},
			//jsonData:{id:ids},
			//jsonData:"id:["+ids+"]",
			//jsonData:"id=["+ids+"]",
			success: function(response, opts) {
				//var obj = Ext.decode(response.responseText);
				//console.dir(obj);
				store.load();
			},

			failure: function(response, opts) {
				console.log('server-side failure with status code ' + response.status);
			}
		 });
	},
	
	showWindow:function(grid, rowIndex, colIndex){
		//Ext.m_data=records[0];
		var rec=grid.getStore().getAt(rowIndex);
		//grid.setActiveItem(grid.getStore().getAt(rowIndex));
		Ext.Msg.alert('Title', grid);
		Ext.Msg.alert('Title', rowIndex);
		Ext.Msg.alert('Title', colIndex);
		console.log(grid);
		console.log(rowIndex);
		console.log(colIndex);
		var win=Ext.create('Ext.window.Window', {
			title: 'Hello',
			height: 600,
			width: 800,
			layout: 'fit',
			items: {  // Let's put an empty grid in just to illustrate fit layout
				xtype: 'panel',
				//title: 'User Form',
				height: 550,
				width: 750,
				//border: false,
				items: [
					{
						xtype:'textfield',
						fieldLabel: 'First Name',
						name: 'fullname',
						value:Ext.m_data.data['fullname']
					},
					{
						xtype:'textfield',
						fieldLabel: 'Last Name',
						name: 'Email',
						value:Ext.m_data.data['email']
					},
					{
						xtype: 'datefield',
						fieldLabel: 'joinDate',
						name: 'joinDate',
						value:rec.get("joinDate")
					},
					{
						xtype:'textfield',
						fieldLabel: 'Subscription',
						name: 'Subscription'
					}
				]
			}
		});
		
		//win.loadRecord(Ext.m_data);
		win.show();
	},
	quickSearch:function(btn){
		var field=this.lookupReference('searchFieldName');
		var value=this.lookupReference('searchFieldValue');
		//var value1=this.lookupReference('searchDateFieldValue');
		var grid=btn.up('gridpanel');
		var store=grid.getStore();
		var model=store.getModel();
		//loadData
		/*Ext.Ajax.request({
			url: 'http://localhost:8080/order?page=3&limit=15',

			success: function(response, opts) {
				//console.log(response);
				var obj = Ext.decode(response.responseText);
				console.dir(obj);
				//store.loadData(obj);
				//store.loadPage(obj);
				//model.loadData(obj);
				store.loadRawData(obj)
			},
			failure: function(response, opts) {
				console.log('server-side failure with status code ' + response.status);
			}
		});*/
		//Ext.apply(store.proxy.extraParams, {orderNumber:"",createTimeStart:"",createTimeEnd:""});
		//Ext.apply(store.proxy.extraParams, {orderNo:"",startDate:"",endDate:""});
		//Ext.apply(store.proxy.extraParams, {});
		Ext.apply(store.proxy.extraParams, {id:"",name:""});
		if(field.getValue()==='id'){
			Ext.apply(store.proxy.extraParams, {id:value.getValue()});
		}
		if(field.getValue()==='name'){
			Ext.apply(store.proxy.extraParams,{name:value.getValue()});
		}
		//store.load({params:{start:0, limit:20, page:1}});
		store.load();
		//Ext.Msg.alert("field",field.getValue());
		//Ext.Msg.alert("field",field.getValue());
		//Ext.Msg.alert("value",value.getValue());
	},
	highLevelSearch:function(btn){
		//win.down('datefield').format='Y/m/d H:i:s';
		var store = Ext.data.StoreManager.lookup('departmentGridStroe');
		Ext.apply(store.proxy.extraParams, {id:"",name:""});
		var win=btn.up('window');
		var myform= win.down('form').getForm();
		//win.down('datefield').format='Y/m/d H:i:s';;
		console.log(myform);
		var val=myform.getValues();
		console.log(myform.getValues());
		console.log(myform.getValues(true));
		Ext.apply(store.proxy.extraParams, val);
		//store.load({params:{start:0, limit:20, page:1}});
		console.log(val);
		store.load();
		win.close();
	},
	tbarSelectChange:function(box, newValue, oldValue, eOpts){
		
		/*if(newValue=="id"){
			this.lookupReference('searchFieldValue').show();
			this.lookupReference('searchDateFieldValue').hide();
		}else{
			
			this.lookupReference('searchFieldValue').hide();
			
			this.lookupReference('searchDateFieldValue').show();
		}*/
		
		console.log(newValue);
	},
	
	test:function(){
		Ext.Msg.alert("test","test");
	},
	init:function(){
		/*this.control({
			'gridpanel button': {
				click:this.test
			}
		});*/
	}
});
