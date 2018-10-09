Ext.define('Admin.store.department.DepartmentGridStroe', {
    extend: 'Ext.data.Store',
    alias: 'store.departmentGridStroe',
	storeId:'departmentGridStroe',
	model: 'Admin.model.department.DepartmentModel',
	
    proxy: {
        type: 'rest',
        url:'http://localhost:8080/department',
	    reader:{
	    	type:'json',
	    	rootProperty:'content',
			totalProperty:'totalElements'
	    },
		simpleSortMode: true
    },
	fields: [
	    {type: 'string',name: 'id'},
	    {type: 'string',name: 'name'}
	],
    autoLoad: 'true',
	autoSync:'true',
	remoteSort: true,
	pageSize: 15,
	sorters: {
		direction: 'DESC',
		property: 'id'
	},
});
