Ext.define('Admin.store.quit.EmployeeLoadStroe', {
    extend: 'Ext.data.Store',
    alias: 'store.employeeLoadStroe',
	storeId:'employeeLoadStroe',
	//autoLoad: 'true',
	autoSync:'true',
	//id:'departmentLoadStroe',
    id:"employeeNameStore",
	proxy: {
        type: 'ajax',
		//url:"/order",
        url:'/employee',
		//url: '~api/search/users'	//mvc url  xxx.json
	    reader:{
	    	type:'json',
	    	//rootProperty:'content',
			//totalProperty:'totalElements'
	    },
		//simpleSortMode: true
    },
	fields: [
	    {type: 'string',name: 'id'},
	    {type: 'string',name: 'name'}
	],
});
