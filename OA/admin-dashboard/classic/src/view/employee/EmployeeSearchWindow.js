Ext.define('Admin.view.employee.EmployeeSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeSearchWindow',
    autoShow: true,
    height: 300,
    minHeight: 300,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Search More Window',
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
        items: [{
				xtype: 'textfield',
				fieldLabel: '员工编号',
				name:'id'
			}, {
	            fieldLabel: '所属部门',
	            xtype:"departmentcombobox",
	            reference:'departmentBox',
	            name:'departmentid',
	            //hidden:true
			}, {
				xtype: 'datefield',
				fieldLabel: '入职时间',
				format: 'Y/m/d',
				name: 'entryTime'
				
         }]
    }],
	buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitSearchForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
});