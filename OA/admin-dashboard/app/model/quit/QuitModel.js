Ext.define('Admin.model.quit.QuitModel', {
    extend: 'Ext.data.Model',
	//alias: 'widget.userModel',
	/*requires: [
		'Ext.data.proxy.Rest'
	],*/
	/*fields: [
	    {type: 'int',name: 'id'},
	    {type: 'date',name: 'orderDate',dateFormat:'Y/m/d H:i:s'},
	    {type: 'string',name: 'orderNo'}
	],*/
	fields: [
	    {type: 'int',name: 'id'},
	    {type: 'string',name: 'employeeid'},
	    {type: 'string',name: 'employeeName'},
	    {type: 'string',name: 'departmentName'},
	    {type: 'string',name: 'reason'},
	    {type: 'date',name: 'applyDate',dateFormat:'Y/m/d H:i:s'},
	    {type: 'date',name: 'quitDate',dateFormat:'Y/m/d H:i:s'},
	    {type: 'string',name: 'status'}
	],
	proxy: {
        type: 'rest',
		//url:"/order",
        url:'http://localhost:8080/quit',
		//url: '~api/search/users'	//mvc url  xxx.json
	    reader:{
	    	type:'json',
	    	rootProperty:'content',
			totalProperty:'totalElements'
	    },
		simpleSortMode: true
    },
});