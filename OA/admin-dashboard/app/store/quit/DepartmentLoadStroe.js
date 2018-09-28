Ext.define('Admin.store.quit.DepartmentLoadStroe', {
    extend: 'Ext.data.Store',
    alias: 'store.departmentLoadStroe',
	storeId:'departmentLoadStroe',
	//autoLoad: 'true',
	autoSync:'true',
	//id:'departmentLoadStroe',
    id:"departmentNameStore",
	proxy: {
        type: 'ajax',
		//url:"/order",
        url:'http://localhost:8080/department/simpleget1',
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
