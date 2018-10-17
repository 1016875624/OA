Ext.define('Admin.model.testPaper.TestPaperModel', {
    extend: 'Admin.model.Base',
    fields: [
	    {type: 'int',name: 'id'},
		{type: 'string',name: 'questionList'},
		{type: 'string',name: 'employeeid'},
	    {type: 'int', name: 'score'},
	    {type: 'int', name: 'status'},
	    {type: 'date',name: 'startTime',dateFormat:'Y/m/d H:i:s'},
	    {type: 'date',name: 'endTime',dateFormat:'Y/m/d H:i:s'},
	]
	
});
