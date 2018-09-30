Ext.define('Admin.view.employee.EmployeeEmailWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeEmailWindow',
    autoShow: true,
    height: 500,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Send Email Window',
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
				fieldLabel: '申请人编号',
				name:'id'
			},{
				xtype: 'textfield',
				fieldLabel: '申请人姓名',
				name:'id'
			},{
				xtype: 'textfield',
				fieldLabel: '申请人邮箱',
				name:'id'
			},{
				xtype: 'textfield',
				fieldLabel: '接收者邮箱',
				name:'id'
			},{
				xtype: 'combobox',
	            reference:'searchFieldName',
	            fieldLabel: '申请类型',
	            //hideLabel: true,
	            store:Ext.create("Ext.data.Store", {
				    fields: ["name", "value"],
				    data: [
				      	{ name: '调离部门', value: 'change' },
						{ name: '离职'   , value:'leave'},
				    ]
				}),
	            displayField: 'name',
	            valueField:'value',
	            //value:'申请类型',
	            editable: false,
	            queryMode: 'local',
	            triggerAction: 'all',
	            emptyText: 'Select a Type...',
	            width: 135,
	            listeners:{
					change:'tbarSelectChangeType'
				}
			},{
				xtype: 'textfield',
				reference:'searchFieldValue',
				fieldLabel: '申请离职原因',
				name:'reason',
				hidden:true
			},{
				fieldLabel: '原部门',
				xtype:"departmentcombobox",
		    	reference:'departmentBox',
		    	name:'departmentid'
			},{
				fieldLabel: '调往部门',
				xtype:"departmentcombobox",
		    	reference:'departmentBox',
		    	name:'departmentid'
			},{
				xtype: 'textareafield',
				fieldLabel: '申请原因',
				name:'reason'
			}
			],
   buttons: ['->',{
        xtype: 'button',
        text: 'Save',
        handler: 'submitSave'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
    }]
});
