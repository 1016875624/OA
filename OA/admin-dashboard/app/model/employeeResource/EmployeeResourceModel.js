Ext.define('Admin.model.employeeResource.EmployeeResourceModel', {
	extend: 'Admin.model.Base',
	requires: [
		'Ext.data.proxy.Rest'
	],
	fields: [
		{type:'int',name:'id'}
		,{type:'string',name:'employeeId'}
		,{type:'string',name:'tradeId'}
		,{type:'string',name:'employeeName'}
		,{type:'string',name:'employeePosition'}
		,{type:'date',name:'employeeResource',dateFormat:'Y/m/d H:i:s'}
		,{type:'int',name:'count'}
		,{type:'string',name:'resourceName'}
		,{type:'int',name:'loseCount'}
		,{type:'string',name:'loseResourceName'}
		,{type:'string',name:'remark'}
		,{type:'int',name:'status'}
	],
	proxy: {
		type: 'rest',
		url: '/employeeResource',
	}
});
