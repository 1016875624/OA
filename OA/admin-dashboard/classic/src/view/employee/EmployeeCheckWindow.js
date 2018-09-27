Ext.define('Admin.view.employee.EmployeeCheckWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.employeeCheckWindow',
	autoShow: true,
	modal: true,
	layout: 'fit',
	width: 500,
	height: 500,
	title: '查看用户信息',
	items: [{
			xtype: 'form',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			bodyPadding: 20,
			scrollable: true,
			defaults: {
				labelWidth: 100,
				labelSeparator: ''

			},
			defaultType: 'textfield',
			items: [{name: 'id',fieldLabel: 'id'},
					{name: 'name',fieldLabel: 'name'}, 
					{name: 'department',fieldLabel: 'department'}, 
					{name: 'position',fieldLabel: 'position'}, 
					{name: 'email',fieldLabel: 'email'},
					{name: 'status',fieldLabel: 'status'},
					{name: 'leader',fieldLabel: 'leader'},
					{name: 'entryTime',fieldLabel: 'entryTime'},
			],
		}
	],
});