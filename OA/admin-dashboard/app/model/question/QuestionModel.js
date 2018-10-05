Ext.define('Admin.model.question.QuestionModel', {
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
	    //其他字段,多选题
	    {type: 'string',name: 'realanswerA'},
	    {type: 'string',name: 'realanswerB'},
	    {type: 'string',name: 'realanswerC'},
	    {type: 'string',name: 'realanswerD'},
	    //选择题选项
	    
	    {type: 'string',name: 'answersA'},
	    {type: 'string',name: 'answersB'},
	    {type: 'string',name: 'answersC'},
	    {type: 'string',name: 'answersD'},
	    //填空题
	    {type: 'string',name: 'realanswer4'},
	    {type: 'string',name: 'realanswer1'},
	    {type: 'string',name: 'realanswer2'},
	    {type: 'string',name: 'realanswer3'},
	],
	proxy: {
		type: 'rest',
		url: 'http://localhost:8080/textquestion',
	}
});
