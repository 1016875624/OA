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
    proxy: {
        type: 'rest',
        url: 'http://localhost:8080/salary',
        reader:{
            type:'json',
            rootProperty:'content',//对应后台返回的结果集名称
            totalProperty: 'totalElements'//分页需要知道总记录数
        },
        writer: {
            type: 'json'
        },
        simpleSortMode: true	//简单排序模式
    },
});