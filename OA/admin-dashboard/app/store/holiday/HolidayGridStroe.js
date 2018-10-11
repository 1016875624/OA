Ext.define('Admin.store.holiday.HolidayGridStroe', {
	extend: 'Ext.data.Store',
	storeId:'holidayGridStroe',
	alias: 'store.holidayGridStroe',
	fields: [
	    {type: 'string',name: 'employeeid'},
		{type: 'string',name: 'employeeName'},
		{type: 'string',name: 'departmentName'},
		{type: 'int',name: 'hour'},
	    {type: 'date', name: 'date', dateFormat:'Y/m/d'},
	    //{type:'date'name:'StartDate'},
	    //{type:'date',name:'EndDate'},
	    {type:'int',name:'status'},
	    {type:'int',name:'ifholiday'}
	],
	/*data:{
		'lists':[]
	},*/
	proxy: {
		type: 'ajax',
		method : 'post', 
		url : 'http://localhost:8080/workTime/savemore', 
		reader:{
			type:'json',
			//rootProperty:'lists',//对应当前date返回的结果集名称
			//totalProperty: 'totalElements'//分页需要知道总记录数
		},
		writer: {
			type: 'json'
		},
		simpleSortMode: true	//简单排序模式
	},
	asynchronousLoad:false,
	autoLoad:false,
	autoSync: false,
	remoteSort: true,//全局(远程)排序
	pageSize: 15,
	sorters: {
		direction: 'DESC',property: 'id'
	},
//	listeners:{
//		update:function(stores, record, operation, modifiedFieldNames, details, eOpts){
//			return false;
//		}
//	}
	
});
