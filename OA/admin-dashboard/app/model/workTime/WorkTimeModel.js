Ext.define('Admin.model.workTime.WorkTimeModel', {
    extend: 'Admin.model.Base',
    requires: [
		'Ext.data.proxy.Rest'
	],
    fields: [
    	{type: 'int',name: 'id'},
	    {type: 'string',name: 'employeeid'},
		{type: 'string',name: 'employeeName'},
		{type: 'string',name: 'departmentName'},
		{type: 'int',name: 'hour'},
	    {type: 'date', name: 'date', dateFormat:'Y/m/d'}
	],
	proxy: {
		type: 'rest',
		url: '/workTime',
	}
});
