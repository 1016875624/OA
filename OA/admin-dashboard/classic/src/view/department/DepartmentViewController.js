var department=Ext.ComponentQuery.query("department");
Ext.define('Admin.view.department.DepartmentViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.departmentViewController',
    
	/*Add Window*/
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
    
	/*删除一行*/
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
	
	/*Edit*/
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
		win.down('form').loadRecord(rec);
		var store = Ext.data.StoreManager.lookup('departmentGridStroe');
		console.log(Ext.ClassManager.getName(win.down('button')));
		win.down('button[text=save]').setHandler(function(){
			var values  = win.down('form').getValues();//获取form数据
        	var record = store.getById(values.id);//获取id获取store中的数据
        	record.set(values);
			win.close();
			store.load();
			
		});
		console.log(Ext.ClassManager.getName(Ext.get('userWindowSave')));
	},
	
	/*部门人员管理和交换窗口*/	
	gridChange:function(grid, rowIndex, colIndex){	
		var win = grid.up('container').add(Ext.widget('departmentChangeWindow'));//打开窗口
		
		var defaultBox = this.lookupReference('dpartmentBox');//获取对象
		var oDefaultBox = this.lookupReference('oDpartmentBox');

		
		var rec = grid.getStore().getAt(rowIndex);//获取Store记录
		var store=Ext.data.StoreManager.lookup('loadingGridStroe');//获取Store数据
		var departid=rec.get("id");//从记录中获取id
		Ext.apply(store.proxy.extraParams, {departmentid:departid});//与后台DTO里面的字段交互
		
		/*var rec = grid.getStore().getAt(rowIndex);//获取Store记录
		var store=Ext.data.StoreManager.lookup('oLoadingGridStroe');//获取Store数据
		var departid=rec.get("id");//从记录中获取id
		Ext.apply(store.proxy.extraParams, {departmentid:departid});//与后台DTO里面的字段交互
		 */		
		var storeL=defaultBox.getStore();
		var storeR=oDefaultBox.getStore();
		
		store.load();//刷新
		
		console.log(Ext.ClassManager.getName(win.down("form")));
		
		storeL.load();
		storeR.load();
		
		defaultBox.setValue(departid);
		//oDefaultBox.setValue(departid);
		
		console.log(defaultBox.getValue());
		console.log(oDefaultBox.getValue());
	},
	
	/*点击原部门选择监听事件*/
	departmentSelectChange:function(box,newValue,oldValue,eOpts){
		console.log("12356");
		var store=Ext.data.StoreManager.lookup('loadingGridStroe');//获取Store数据
		var departid=newValue;//从newValue中获取id
		Ext.apply(store.proxy.extraParams, {departmentid:departid});//与后台DTO里面的字段交互
		store.load();
	},
	
	/*点击其他部门选择监听事件*/
	oDepartmentSelectChange:function(box,newValue,oldValue,eOpts){
		console.log("12356");
		var store=Ext.data.StoreManager.lookup('oLoadingGridStroe');//获取Store数据
		var departid=newValue;//从newValue中获取id
		Ext.apply(store.proxy.extraParams, {departmentid:departid});//与后台DTO里面的字段交互
		store.load();
	},
	
	/*Right Push*/
	rightPush:function(btn){
		var departmentGird = this.lookupReference('loadingPanel');
		var oDepartmentGird = this.lookupReference('oLoadingPanel');
		var dpartment = this.lookupReference('dpartmentBox');
		var oDpartment = this.lookupReference('oDpartmentBox');
		var lselects=departmentGird.getSelection();
		var rselects=oDepartmentGird.getSelection();
		var lArray=new Array();
		var rArray=new Array();
		for(i=0;i<lselects.length;i++){
			var temp=lselects[i].getData();
			temp.departmentid=oDpartment.getValue();
			lArray.push(temp);
		};
		for(j=0;j<rselects.length;j++){
			var temp=rselects[j].getData();
			temp.departmentid=dpartment.getValue();
			rArray.push(temp);
		};
		for(i=0;i<lArray.length;i++){
			var temp=lArray[i];
			rArray.push(temp);
		};
		
		console.log(lArray);
		console.log(rArray);
		Ext.Ajax.request({		
		url: 'http://localhost:8080/employee/changeDepartment',
		method:'post',
		jsonData:rArray,
		success: function(response, opts) {
			departmentGird.getStore().load();
			oDepartmentGird.getStore().load();
		},
		failure: function(response, opts) {
			console.log('server-side failure with status code ' + response.status);
		}
		});
		
		console.log(lselects);
		console.log(rselects);
		console.log(Ext.ClassManager.getName(lselects));
		console.log(Ext.ClassManager.getName(rselects));
	},
	
	/*Left Pull*/
	leftPull:function(btn){
		var departmentGird = this.lookupReference('loadingPanel');
		var oDepartmentGird = this.lookupReference('oLoadingPanel');
		var dpartment = this.lookupReference('dpartmentBox');
		var oDpartment = this.lookupReference('oDpartmentBox');
		var lselects=departmentGird.getSelection();
		var rselects=oDepartmentGird.getSelection();
		var lArray=new Array();
		var rArray=new Array();
		for(i=0;i<lselects.length;i++){
			var temp=lselects[i].getData();
			temp.departmentid=oDpartment.getValue();
			lArray.push(temp);
		};
		for(j=0;j<rselects.length;j++){
			var temp=rselects[j].getData();
			temp.departmentid=dpartment.getValue();
			rArray.push(temp);
		};
		for(i=0;i<rArray.length;i++){
			var temp=rArray[i];
			lArray.push(temp);
		};
		console.log(lArray);
		console.log(rArray);
		Ext.Ajax.request({		
		url: 'http://localhost:8080/employee/changeDepartment',
		method:'post',
		jsonData:lArray,
		success: function(response, opts) {
			departmentGird.getStore().load();
			oDepartmentGird.getStore().load();
		},
		failure: function(response, opts) {
			console.log('server-side failure with status code ' + response.status);
		}
		});
		console.log(lselects);
		console.log(rselects);
		console.log(Ext.ClassManager.getName(lselects));
		console.log(Ext.ClassManager.getName(rselects));
	},
	
	/*Delete More*/
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
	/*点击监听事件*/
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
	
	/*高级查询*/
	openSearchWindow:function(btn){
		var win=Ext.widget('departmentSearchWindow');
		btn.up('gridpanel').up('container').add(win);		
		win.down('button[text=Go]').setHandler(this.highLevelSearch);
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
	
	init:function(){
	}
});
