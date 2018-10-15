Ext.define('Admin.model.salary.SalaryModel', {
    extend: 'Admin.model.Base',
    requires: [
		'Ext.data.proxy.Rest'
	],
    fields: [
	    {type: 'int',name: 'id'},
	    {type: 'string',name: 'employeeid'},
		{type: 'string',name: 'employeeName'},
	    {type: 'string',name: 'sal'},
	    {type: 'string',name: 'bonus'},
	    {type: 'int',name: 'workMonth'},
	    {type: 'string',name: 'worktimeMoney'},
		{type: 'string',name: 'subsidy'}
	],
	/*proxy: {
		type: 'rest',
		url: 'http:localhost:8080/salary',
	}*/
});