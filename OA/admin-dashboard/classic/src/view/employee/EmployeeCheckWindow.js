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
			items: [{name: '员工编号',fieldLabel: 'id'},
					// {name: 'profile_pic',fieldLabel: 'User',
						// renderer: function(value) {
							// return "<img src='resources/images/user-profile/" + value + "' alt='Profile Pic' height='40px' width='40px'>";
						// }
					// },
					{name: '员工姓名',fieldLabel: 'name'}, 
					{name: '所属部门',fieldLabel: 'department'}, 
					{name: '职位',fieldLabel: 'position'}, 
					{name: 'email',fieldLabel: 'email'},
					{name: '在职状态',fieldLabel: 'status'},
					{name: '上级领导',fieldLabel: 'leader'},
					{name: 'joinDate',fieldLabel: 'entryTime'},
			],
			
			// buttons: [{
					// text: 'OK',
					// handler: function () {
					// }

				// }, {

					// text: 'Cancel',
					// handler: function () {
						// this.up('window').hide();

					// }

				// }
			// ]

		}
	],
});