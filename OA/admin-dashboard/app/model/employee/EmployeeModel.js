Ext.define('Admin.model.employee.EmployeeModel', {
    extend: 'Admin.model.Base',
    requires: [
		'Ext.data.proxy.Rest'
	],
    fields: [
	    {type: 'string',name: 'id'},
		{type: 'string',name: 'name'},
		{type: 'string',name: 'departmentName'},
		{type: 'string',name: 'email'},
		{type: 'string',name: 'position'},
		{type: 'int',name: 'state'},
		{type: 'string',name: 'leaderName'},
		{type: 'string',name: 'departmentid'},
	    {type: 'date', name: 'entryTime', dateFormat:'Y/m/d H:i:s'}
	],
	proxy: {
		type: 'rest', //类型为依赖
		url: 'http://localhost:8080/employee',
	}
});
