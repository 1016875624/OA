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
				allowBlank : false,
				name:'id'
			},{
				xtype: 'textfield',
				fieldLabel: '申请人姓名',
				allowBlank : false,
				name:'id'
			},{
				xtype: 'textfield',
				fieldLabel: '申请人邮箱',
				allowBlank : false,
				name:'id'
			},{
				xtype: 'textfield',
				fieldLabel: '接收者邮箱',
				allowBlank : false,
				name:'id'
			},{
				xtype: 'textfield',
	            name:'applyType',
	            fieldLabel: '申请类型',
	            emptyText:'调离部门',
	            editable: false
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
				fieldLabel: '调部原因',
				name:'reason'
			}
			],
   buttons: ['->',{
        xtype: 'button',
        text: 'Send',
        handler: 'submitSend'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
    }]
});
