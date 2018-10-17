Ext.define('Admin.store.testPaper.TestPaperGridStroe', {
	extend: 'Ext.data.Store',
	storeId:'testPaperGridStroe',
	alias: 'store.testPaperGridStroe',
	model:'Admin.model.testPaper.TestPaperModel',
	proxy: {
		type: 'ajax',
		method : 'post', 
		url: 'http://localhost:8080/testPaper/getpaper',
		reader:{
			type:'json',
		},
		writer: {
			type: 'json'
		},
		simpleSortMode: true	//简单排序模式
	},
	//autoLoad: true,
	asynchronousLoad:false,
	autoLoad:true,
	autoSync: false,
});
