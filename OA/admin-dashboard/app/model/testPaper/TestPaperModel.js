Ext.define('Admin.model.testPaper.TestPaperModel', {
    extend: 'Admin.model.Base',
    requires: [
		'Ext.data.proxy.Rest'
	],
    fields: [
	    {type: 'int',name: 'id'},
		{type: 'string',name: 'textQuestion'},
		{type: 'string',name: 'realanswer'},
		{type: 'string',name: 'answers'},
	    {type: 'int', name: 'type'},
	    {type: 'int', name: 'status'},
	],
	proxy: {
		type: 'rest',
		url: 'http://localhost:8080/textquestion',
	}
});
