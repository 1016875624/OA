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
	
	
	/*增加部门功能*/
	openAddWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('departmentAddWindow')).show();
	},
	/*Add Submit*/	
	submitAddForm:function(btn){
		var win    = btn.up('window');
		var form = win.down('form');
		var record = Ext.create('Admin.model.department.DepartmentModel');
		var values  =form.getValues();//获取form数据
		record.set(values);
		record.save();
		Ext.data.StoreManager.lookup('departmentGridStroe').load();
		win.close();
	},
	
	
	
	
	gridModify:function(grid, rowIndex, colIndex){
		var rec=grid.getStore().getAt(rowIndex);
		var win=Ext.widget('departmentEditWindow');
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
	
	//部门人员管理和交换
	gridChange:function(grid, rowIndex, colIndex){		
		var rec = grid.getStore().getAt(rowIndex);
		var win = Ext.widget('departmentChangeWindow');
		//win.setHtml(rec.data.employeesName);
		win.down("form").getForm().loadRecord(rec);
		win.show();
		console.log(Ext.ClassManager.getName(win.down("form")));
	},
	
	tbarClickDeleteMore:function(btn){
		var grid= btn.up('gridpanel');
		console.log(btn);
		console.log(grid);
		console.log(Ext.ClassManager.getName(grid));
		var selects= grid.getSelection();
		var store=grid.getStore();
		console.log(selects);
		var ids=new Array();
		Ext.Array.each(selects,function(val, index, countriesItSelf){
			console.log(val);
			console.log(val.data);
			console.log(val.data.id);
			ids.push(val.get("id"));
		});
		console.log(ids);
		Ext.Ajax.request({
			url: 'http://localhost:8080/department/deleteMore',
			method:'post',
			params:{id:ids},
			success: function(response, opts) {
				store.load();
			},

			failure: function(response, opts) {
				console.log('server-side failure with status code ' + response.status);
			}
		 });
	},
	
	showWindow:function(grid, rowIndex, colIndex){
		var rec=grid.getStore().getAt(rowIndex);
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
		
		win.show();
	},
	
	
	/*Quick Search*/	
	quickSearch:function(btn){
		var store =	btn.up('gridpanel').getStore();
		Ext.apply(store.proxy.extraParams, {id:"",name:""});
		var searchField = this.lookupReference('searchFieldName').getValue();
		
		var searchValue = this.lookupReference('searchFieldValue').getValue();
		var searchComboValue = this.lookupReference('departmentBox').getValue();
		
		if(searchField==='id'){
			Ext.apply(store.proxy.extraParams, {id:searchValue});
		}
		if(searchField==='name'){
			Ext.apply(store.proxy.extraParams, {id:searchComboValue});
		}
		store.load({params:{start:0, limit:20, page:1}});
	},
	tbarSelectChange:function(box,newValue,oldValue,eOpts){
		console.log("12356");
		var searchValue = this.lookupReference('searchFieldValue');
		var searchComboValue = this.lookupReference('departmentBox');
		if(newValue=="id"){
			searchComboValue.setHidden(true);
			searchValue.setHidden(false);
		}
		else if(newValue=="name"){
			searchComboValue.setHidden(false);
			searchValue.setHidden(true);
		}else{
			searchComboValue.setHidden(true);
			searchValue.setHidden(true);
		}
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
	
	/*查看部门人员*/	
	gridCheck:function(grid, rowIndex, colIndex){
		var rec = grid.getStore().getAt(rowIndex);
		console.log(rec);
		var win = Ext.widget('departmentCheckWindow');
		win.setHtml(rec.data.employeesName);
		win.show();
	},
	
	/*自动加载部门成员信息*/	
	quickSearch1:function(btn){
		var store =	btn.up('gridpanel').getStore();
		Ext.apply(store.proxy.extraParams, {name:""});
		var searchComboValue = this.lookupReference('departmentBox').getValue();
		Ext.apply(store.proxy.extraParams, {id:searchComboValue});
		store.load({params:{start:0, limit:20, page:1}});
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
