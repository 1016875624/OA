Ext.define('Admin.store.department.OLoadingGridStroe', {
    extend: 'Ext.data.Store',
    alias: 'store.oLoadingGridStroe',
	storeId:'oLoadingGridStroe',
	//model: 'Admin.model.department.DepartmentModel',
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
		simpleSortMode: true	//简单排序模式
	},
    autoLoad: 'true',
	autoSync:'true',
	remoteSort: true,
	pageSize: 10,
	sorters: {
		direction: 'ASE',
		property: 'id'
	}
});
