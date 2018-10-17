Ext.define('Admin.store.workTime.WorkTimeGridStroe', {
	extend: 'Ext.data.Store',
	storeId:'workTimeGridStroe',
	alias: 'store.workTimeGridStroe',
	model:'Admin.model.workTime.WorkTimeModel',
	proxy: {
		type: 'rest',
		url: 'http://localhost:8080/workTime',
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
	autoLoad: true,
	autoSync: true,
	remoteSort: true,//全局(远程)排序
	pageSize: 15,
	sorters: {
		direction: 'DESC',property: 'id'
	}
});
