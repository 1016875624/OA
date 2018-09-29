Ext.define('Admin.model.salarypay.SalarypayModel', {
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
	    {type: 'number',name: 'money'},
        {type: 'int',name: 'realWorktime'},
        {type: 'int',name: 'worktime'},
        {type: 'number',name: 'attendRate'},
	    {type: 'date',name: 'date',dateFormat:'Y/m/d'},
        //{type: 'date',name: 'date',dateFormat:'Y/m/d H:i:s'},
	    {type: 'string',name: 'status'}
	],
	proxy: {
        type: 'rest',
		//url:"/order",
        url:'http://localhost:8080/salarypay',
		//url: '~api/search/users'	//mvc url  xxx.json
	    reader:{
	    	type:'json',
	    	rootProperty:'content',
			totalProperty:'totalElements'
	    },
		simpleSortMode: true
    },
});