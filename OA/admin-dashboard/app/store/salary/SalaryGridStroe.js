Ext.define('Admin.store.salary.SalaryGridStroe', {
	extend: 'Ext.data.Store',
	storeId:'salaryGridStroe',//为了方便写事件可以找到OrderGridStore组件
	alias: 'store.salaryGridStroe',
	model:'Admin.model.salary.SalaryModel',
	/*proxy: {
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
	},*/
	autoLoad: true,
	autoSync: true,
	remoteSort: true,//全局(远程)排序
	pageSize: 20,
	sorters: {
		direction: 'DESC',property: 'id'
	}
});
