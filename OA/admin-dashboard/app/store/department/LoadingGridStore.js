Ext.define('Admin.store.department.LoadingGridStroe', {
	extend: 'Ext.data.Store',
	storeId:'loadingGridStroe',
	alias: 'store.loadingGridStroe',
	model:'Admin.model.department.LoadingModel',
	autoLoad: true,
	autoSync: true,
	remoteSort: true,//全局(远程)排序
	pageSize: 10,
	sorters: {
		direction: 'ASE',property: 'id'
	}
});