Ext.define('Admin.store.salarypay.SalarypayGridStroe', {
    extend: 'Ext.data.Store',
    alias: 'store.salarypayGridStroe',
	storeId:'salarypayGridStroe',
	model: 'Admin.model.salarypay.SalarypayModel',
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
	remoteSort: true,
	pageSize: 10,
	sorters: {
		direction: 'DESC',
		property: 'id'
	}
	
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
