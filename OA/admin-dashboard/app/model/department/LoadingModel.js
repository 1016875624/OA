Ext.define('Admin.model.department.LoadingModel', {
    extend: 'Admin.model.Base',
    requires: [
		'Ext.data.proxy.Rest'
	],
    fields: [
	    {type: 'string',name: 'id'},
		{type: 'string',name: 'name'}
	],
	proxy: {
		type: 'rest',
		url: 'http://localhost:8080/employee',
		reader:{
			type:'json',
			rootProperty:'content',//对应后台返回的结果集名称
			totalProperty: 'totalElements'//分页需要知道总记录数
		},
		writer: {
			type: 'json'
		},
		simpleSortMode: true	//简单排序模式
	}
});