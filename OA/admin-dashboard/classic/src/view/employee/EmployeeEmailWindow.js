Ext.define('Admin.view.employee.EmployeeEmailWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeEmailWindow',
    autoShow: true,
    height: 450,
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
				fieldLabel: '员工编号',
				name:'id'
			}, {
				xtype: 'datefield',
				fieldLabel: '入职时间',
				name:'entryTime',
				format: 'Y/m/d'
			}]
		}],
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
  
});
