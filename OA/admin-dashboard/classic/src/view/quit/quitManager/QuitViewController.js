var quit=Ext.ComponentQuery.query("quit");
Ext.define('Admin.view.quit.quitManager.QuitViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.quitViewController',
    
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
		var win=Ext.widget('quitSearchWindow');
		//win.setTitle('高级查询');
		btn.up('gridpanel').up('container').add(win);
		//win.show();
		/*win.down('button[text=save]').setHandler(function(){
			Ext.Msg.alert("search","you click the button named save");
		});*/
		
		win.down('button[text=save]').setHandler(this.highLevelSearch);
	},
	tbarClickAddBtn:function(btn){
		var win=Ext.widget('quitWindow');
		win.setTitle('添加数据');
		btn.up('gridpanel').up('container').add(win);
		var containner=btn.up('gridpanel').up('container');
		var grid=btn.up('gridpanel');
		var form1=win.down('form').getForm();
		win.down('button[text=save]').setHandler(function(){
			//Ext.Msg.alert("Add","you click the button named save");
			var record = Ext.create('Admin.model.quit.QuitModel');
			var values  =form1.getValues();//获取form数据
			console.log(values);
           	record.set(values);
			console.log(record);
            record.save();
			win.close();
			grid.getStore().load();
			
		});
	},
    tbarClickApplyQuitBtn:function(btn){
        var win=Ext.widget('quitWindow');
        win.setTitle('申请离职');
        btn.up('gridpanel').up('container').add(win);
        var containner=btn.up('gridpanel').up('container');
        var grid=btn.up('gridpanel');
        var form1=win.down('form').getForm();
        form1.findField("employeeid").setHidden(true);
        form1.findField("status").setHidden(true);
        form1.findField("quitDate").setHidden(true);
        //form1.findField("id").setHidden(true);
        win.down('button[text=save]').setHandler(function(){
            Ext.Ajax.request({
                url: 'http://localhost:8080/quit/applyQuit',

                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    console.dir(obj);
                    grid.getStore().load();
                },

                failure: function(response, opts) {
                    console.log('server-side failure with status code ' + response.status);
                },
                params:form1.getValues(),
				method:'post',
            });
            win.close();

        });
	},
	
	
	
	
	
	gridModify:function(grid, rowIndex, colIndex){
		//grid.setStyle('background-color', 'red');
		//grid.setStyle({"background-color":"red"});
        //grid.setStyle({"width":"300px"});
        console.log(grid.getStyle());
        var rec=grid.getStore().getAt(rowIndex);
		var win=Ext.widget('quitWindow');
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
		var store = Ext.data.StoreManager.lookup('quitGridStroe');
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
    gridApproval:function(grid, rowIndex, colIndex){
		var rec=grid.getStore().getAt(rowIndex);
		var win=Ext.widget('quitApprovalWindow');
		grid.up('container').add(win);
		win.show();
		console.log(Ext.ClassManager.getName(rec));
		console.log(rec);
		console.log(rec.data);
		console.log(rec.data.id);
		console.log(Ext.ClassManager.getName(rec.get('name')));

		win.down('form').loadRecord(rec);
		var store = Ext.data.StoreManager.lookup('quitGridStroe');
		console.log(Ext.ClassManager.getName(win.down('button')));
		win.down('button[text=通过]').setHandler(function(){
			var values  = win.down('form').getValues();//获取form数据
			values.status=1;
            Ext.Ajax.request({
                url: 'http://localhost:8080/quit/approval',
				method:"post",
				params:values,
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    console.dir(obj);
                    store.load();
                },

                failure: function(response, opts) {
                    console.log('server-side failure with status code ' + response.status);
                }
            });
            win.close();
		});

        win.down('button[text=驳回]').setHandler(function(){
            var values  = win.down('form').getValues();//获取form数据
            values.status=2;
            Ext.Ajax.request({
                url: 'http://localhost:8080/quit/approval',
                method:"post",
                params:values,
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    console.dir(obj);
                    store.load();
                },

                failure: function(response, opts) {
                    console.log('server-side failure with status code ' + response.status);
                }
            });
            win.close();
        });

	},

	/*userClickDeleteMore:function(btn){
		var grid= btn.up('gridpanel');
		console.log(btn);
		console.log(grid);
		console.log(Ext.ClassManager.getName(grid));
		var selects= grid.getSelection();
		var store=grid.getStore();
		store.remove(selects);
	},*/
	tbarClickDeleteMore:function(btn){
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
			url: 'http://localhost:8080/quit/deleteMore',
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
		var text=this.lookupReference('searchTextField');
		var m_date=this.lookupReference("searchDateField");
		var fieldName=this.lookupReference("searchFieldName");
		var combox=this.lookupReference("searchComboboxField");

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
		Ext.apply(store.proxy.extraParams, 
			{id:"",name:"",employeeid:"",employeeName:"",reason:"",departmentid:"",departmentName:"",preApplyDate:"",preQuitDate:""}
		);
		/*if(field.getValue()==='id'){
			Ext.apply(store.proxy.extraParams, {id:value.getValue()});
		}
		if(field.getValue()==='name'){
			Ext.apply(store.proxy.extraParams,{name:value.getValue()});
		}*/
		var nameval=fieldName.getValue();
		if(fieldName.getValue()=="preApplyDate"||fieldName.getValue()=="preQuitDate"){
		    console.log("{'"+nameval+"':'"+m_date.getValue()+"'}");
		    //val=JSON.parse("'{"+nameval+"':'"+m_date.getValue()+"'}");
            val=JSON.parse('{"'+nameval+'":"'+Ext.util.Format.date(m_date.getValue(),"Y/m/d H:i:s")+'"}');
		    console.log(val);
			Ext.apply(store.proxy.extraParams,val);
		}else if (fieldName.getValue()=="departmentName"||fieldName.getValue()=="departmentid"){
            //val=JSON.parse(nameval+":"+m_date.getValue());
			Ext.apply(store.proxy.extraParams,{departmentid:combox.getValue()});
		}else {
            console.log("{"+nameval+":"+text.getValue()+"}");
            val=JSON.parse('{"'+nameval+'":"'+text.getValue()+'"}');
            Ext.apply(store.proxy.extraParams,val);
		}
		
		//store.load({params:{start:0, limit:20, page:1}});
		store.load();
		//Ext.Msg.alert("field",field.getValue());
		//Ext.Msg.alert("field",field.getValue());
		//Ext.Msg.alert("value",value.getValue());
	},
	highLevelSearch:function(btn){
		//win.down('datefield').format='Y/m/d H:i:s';
		var store = Ext.data.StoreManager.lookup('quitGridStroe');
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
		var text=this.lookupReference('searchTextField');
		var date=this.lookupReference('searchDateField');
		var combox=this.lookupReference("searchComboboxField");
		text.setValue("");
		date.setValue("");
		combox.setValue("");
		if(newValue=="preApplyDate"||newValue=="preQuitDate"){
			text.hide();
			date.show();
			combox.hide();
		}
		else if (newValue=="departmentName"||newValue=="departmentid"){
            text.hide();
            date.hide();
            combox.show();
        }

		else{
            text.show();
            date.hide();
            combox.hide();
		}
		//box.getStore().load();
		console.log(newValue);
	},


    gridPass:function(grid, rowIndex, colIndex){
        var rec=grid.getStore().getAt(rowIndex);
        var store = Ext.data.StoreManager.lookup('quitGridStroe');
        console.log(rec);
        console.log(rec.data);
        values=rec.data;
        values.status=1;
        values.quitDate=Ext.util.Format.date(values.quitDate,"Y/m/d H:i:s");
        delete values.quitDate;
        delete values.applyDate;
        Ext.Ajax.request({
            url: 'http://localhost:8080/quit/approvalPass',
            //url: 'http://localhost:8080/quit',
            method:"post",
            params:values,
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                console.dir(obj);
                var store1 = Ext.data.StoreManager.lookup('quitGridStroe');
                store1.load();
			},

            failure: function(response, opts) {
                console.log('server-side failure with status code ' + response.status);
            }
        });
    },
    gridNoPass:function(grid, rowIndex, colIndex){
        var rec=grid.getStore().getAt(rowIndex);
        var store = Ext.data.StoreManager.lookup('quitGridStroe');
        console.log(rec);
        console.log(rec.data);
        values=rec.data;
        values.status=2;
        values.quitDate=Ext.util.Format.date(values.quitDate,"Y/m/d H:i:s");
        delete values.quitDate;
        delete values.applyDate;
        Ext.Ajax.request({
            url: 'http://localhost:8080/quit/approvalNoPass',
            //url: 'http://localhost:8080/quit',
            method:"post",
            params:values,
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                console.dir(obj);
                var store1 = Ext.data.StoreManager.lookup('quitGridStroe');
                store1.load();
            },

            failure: function(response, opts) {
                console.log('server-side failure with status code ' + response.status);
            }
        });
    },
    tbarClickPassBtn:function(btn){
        var grid= btn.up('gridpanel');

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
            url: 'http://localhost:8080/quit/approvalPassMore',
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

    tbarClickNoPassBtn:function(btn){
        var grid= btn.up('gridpanel');

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
            url: 'http://localhost:8080/quit/approvalNoPassMore',
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
