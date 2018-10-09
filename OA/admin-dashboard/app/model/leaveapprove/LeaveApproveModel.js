Ext.define('Admin.model.leaveapprove.LeaveApproveModel', {
    extend: 'Admin.model.Base',
	requires: [
		'Ext.data.proxy.Rest'
	],
    fields: [	//需要修改
		 {type: 'int' ,name: 'id'}
        ,{type:'string',name:'employeeId'}
		,{type:'string',name:'employeeName'}
		,{type: 'date' 	 ,name: 'startTime'}
		,{type: 'date'	 ,name: 'endTime'}
        ,{type: 'date'   ,name: 'realityStartTime'}
		,{type: 'date'	 ,name: 'realityEndTime'}
        ,{type: 'date'   ,name: 'applyTime'}
        ,{type: 'string' ,name: 'leaveType'}
        ,{type: 'string' ,name: 'status'}
		,{type: 'string' ,name: 'reason'}
    ],
	proxy: {
		type: 'rest',
		url: '/leave/approvalTable',
	}
});