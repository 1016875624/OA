Ext.define('Admin.view.employee.EmployeeSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeSearchWindow',
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
				fieldLabel: 'id',
				name:'id'
			}, {
				xtype: 'textfield',
				fieldLabel: 'name',
				name:'name'
			}, {
				xtype: 'textfield',
				fieldLabel: 'department',
				name:'departmentName'
			}, {
				xtype: 'datefield',
				format: 'Y/m/d H:i:s',
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