Ext.define('Admin.view.employee.EmployeeEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeEditWindow',
    height: 600,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Edit employee Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your name',
        items: [
			{
				xtype: 'textfield',
				fieldLabel: '员工id',
				name:'id'
			}, {
				xtype: 'textfield',
				fieldLabel: '员工姓名',
				name:'name'
			}, {
				
				xtype: 'combobox',
				fieldLabel: '员工部门',
				name:'departmentName',
				store:Ext.create("Ext.data.Store", {
				fields: ["id", "name"],
				proxy: {
						type: 'ajax',
						url:'http://localhost:8080/department/simpleget',
						reader:{
						type:'json',
						},
					},
					autoLoad: 'true',
					autoSync:'true',
				}),
				displayField:'name',
				valueField:'id'
			}, {
				xtype: 'textfield',
				fieldLabel: '员工邮箱',
				name:'email'
			}, {
				xtype: 'textfield',
				fieldLabel: '职位',
				name:'position'
			}, {
				xtype: 'combobox',
				fieldLabel: '在职状态',
				name:'status',
	            store: Ext.create('Ext.data.Store', {
	                fields: ['value', 'name'],
	                data: [
					{"value":"0","name":"正常"},
	                {"value":"1","name":"离职"},
					{"value":"-1","name":"封禁"}
	                ]
	                }),
	                displayField: 'name',
	                valueField: 'value',
			}, {
				xtype: 'textfield',
				fieldLabel: '上级领导',
				name:'leaderName'
			}, {
				xtype: 'datefield',
				fieldLabel: '入职时间',
				name:'entryTime',
				format: 'Y/m/d H:i:s'
			}]
		}],
   buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitEditForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
