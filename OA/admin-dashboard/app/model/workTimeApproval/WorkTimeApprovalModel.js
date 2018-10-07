Ext.define('Admin.model.workTimeApproval.WorkTimeApprovalModel', {
    extend: 'Admin.model.Base',
//    requires: [
//		'Ext.data.proxy.Rest'
//	],
    fields: [
    	{type: 'int',name: 'id'},
	    {type: 'string',name: 'employeeid'},
		{type: 'string',name: 'employeeName'},
		{type: 'string',name: 'departmentName'},
		{type: 'int',name: 'hour'},
	    {type: 'date', name: 'date', dateFormat:'Y/m/d'},
	    {type:'int',name:'status'}
	],
	proxy: {
		type: 'ajax',
		url: 'http://localhost:8080/workTime/approval',
		reader:{
	    	type:'json',
	    	rootProperty:'content',
			totalProperty:'totalElements'
	    },
		simpleSortMode: true
	}
});
