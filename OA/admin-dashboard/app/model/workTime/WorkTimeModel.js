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
	    {type: 'date', name: 'date', dateFormat:'Y/m/d'},
	    {type:'int',name:'status'},
	    {type:'int',name:'ifholiday'}
	],
	proxy: {
		type: 'rest',
		url: 'http://localhost:8080/workTime',
	}
});
