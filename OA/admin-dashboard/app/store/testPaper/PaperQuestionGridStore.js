Ext.define('Admin.store.testPaper.PaperQuestionGridStore', {
	extend: 'Ext.data.Store',
	storeId:'paperQuestionGridStore',
	alias: 'store.paperQuestionGridStore',
	fields: [
		 {type: 'int',name: 'id'},
			{type: 'string',name: 'textQuestion'},
			{type: 'string',name: 'realanswer'},
			{type: 'string',name: 'answers'},
		    {type: 'int', name: 'type'},
		    {type: 'int', name: 'status'},
	],
	/*data:{
		'lists':[]
	},*/
	proxy: {
		type: 'ajax',
		method : 'post', 
		url : 'http://localhost:8080/testPaper/getQuestion', 
		reader:{
			type:'json',
			//rootProperty:'lists',//对应当前date返回的结果集名称
			//totalProperty: 'totalElements'//分页需要知道总记录数
		},
		writer: {
			type: 'json'
		},
		timeout:99999999,
		simpleSortMode: true	//简单排序模式
	},
	asynchronousLoad:false,
	autoLoad:true,
	autoSync: false,
	
	
});
