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
			items: [{name: 'id',fieldLabel: '员工id'},
					{name: 'name',fieldLabel: '员工姓名'}, 
					{name: 'departmentName',fieldLabel: '员工部门'}, 
					{name: 'position',fieldLabel: '职位'}, 
					{name: 'email',fieldLabel: 'email'},
					{name: 'status',fieldLabel: '状态'},
					{name: 'leaderName',fieldLabel: '上级'},
					{name: 'entryTime',fieldLabel: '入职时间',format: 'Y/m/d H:i:s'},
			],
		}
	],
});