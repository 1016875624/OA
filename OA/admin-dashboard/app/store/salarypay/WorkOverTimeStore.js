Ext.define('Admin.store.salarypay.WorkOverTimeStore', {
    extend: 'Ext.data.Store',
    alias: 'store.workOverTimeStore',
	storeId:'workOverTimeStore',
	/*data: {
		'lists':[{
	        "id": 1,
	        "orderNo": "Archie Young",
	        "orderDate": "2018/09/09 17:51:20"
	    },{
	        "id": 2,
	        "orderNo": "May Williams",
	        "orderDate": "2018/09/09 17:50:20"
    	}]
    },*/
	autoLoad: 'true',
	autoSync:'true',
	remoteSort: false,
	pageSize: 10,
	sorters: {
		direction: 'DESC',
		property: 'id'
	},


    fields: [
        {type: 'string',name: 'employeeid'},
        {type: 'string',name: 'employeeName'},
        {type: 'string',name: 'departmentName'},
        {type: 'string',name: 'departmentid'},
        {type: 'number',name: 'overHours'},
    ],
    proxy: {
        type: 'rest',
        //url:"/order",
        url:'http://localhost:8080/salarypay/workovertime',
        extraParams:{start:"",end:""},
        //url: '~api/search/users'	//mvc url  xxx.json
       /* reader:{
            /!*type:'json',
            rootProperty:'content',
            totalProperty:'totalElements'*!/
        },*/
        simpleSortMode: true
    },

    /*proxy: {
        type: 'rest',
		//url:"/order",
        url:'http://localhost:8080/order',
		//url: '~api/search/users'	//mvc url  xxx.json
	    reader:{
	    	type:'json',
	    	rootProperty:'content',
			totalProperty:'totalElements'
	    },
		simpleSortMode: true
    },
	fields: [
	    {type: 'int',name: 'id'},
	    {type: 'date',name: 'orderDate',dateFormat:'Y/m/d H:i:s'},
	    {type: 'string',name: 'orderNo'}
	],
    autoLoad: 'true',
	autoSync:'true',
	remoteSort: true,
	pageSize: 15,
	sorters: {
		direction: 'DESC',
		property: 'id'
	},*/
});
