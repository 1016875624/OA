Ext.define('Admin.model.employee.EmployeeModel', {
    extend: 'Admin.model.Base',
    requires: [
		'Ext.data.proxy.Rest'
	],
    fields: [
	    {type: 'string',name: 'id'},
		{type: 'string',name: 'name'},
		//{type: 'department',name: 'department'},
		{type: 'string',name: 'email'},
		{type: 'string',name: 'position'},
		{type: 'integer',name: 'state'},
		{type: 'string',name: 'leader'},
	    {type: 'date', name: 'entryTime', dateFormat:'Y/m/d H:i:s'}
	],
	proxy: {
		type: 'rest',
		url: '/employee',
	}
});
