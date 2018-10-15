Ext.define('Admin.model.officeResource.OfficeResourceModel', {
	extend: 'Admin.model.Base',
	requires: [
		'Ext.data.proxy.Rest'
	],
	fields: [
		{type:'int',name:'id'}
		,{type:'string',name:'employeeId'}
		,{type:'string',name:'employeeName'}
		,{type:'string',name:'employeePosition'}
		,{type:'date',name:'startTime',dateFormat:'Y/m/d H:i:s'}
		,{type:'date',name:'endTime',dateFormat:'Y/m/d H:i:s'}
		,{type:'date',name:'applyTime',dateFormat:'Y/m/d H:i:s'}
		,{type:'int',name:'leftCount'}
		,{type:'string',name:'resourceName'}
		,{type:'int',name:'remark'}
		,{type:'int',name:'status'}
	],
	proxy: {
		type: 'rest',
		url: '/officeResource',
	}
});
