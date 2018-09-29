
Ext.define('Admin.view.question.QuestionEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.questionEditWindow',
    height: 450,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Edit question Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your question',
        items: [{
        	xtype:'textfield',
        	hidden:true,
        	readOnly:true,
        	name:'id',
        	fieldLabel:'id'
        	
        },{
			xtype     : 'textareafield',
			grow      : true,
			name      : 'textQuestion',
			fieldLabel: '题目',
			anchor    : '100%'
		} ,{
            xtype: 'combobox',
            fieldLabel: '题目类型',
            name:'type',
            editable:false,
            store:Ext.create('Ext.data.Store',{
            	fields:['value','name'],
            	data : [
					{"value":"0", "name":"单选题"},
					{"value":"1", "name":"多选题"},
					{"value":"2", "name":"填空题"}
				]
            }),
            queryMode:'local',
            displayField:'name',
            valueField:'value'
            	
        }, {
            xtype: 'textfield',
            fieldLabel: '标准答案',
            name:'realanswer'																																																		
        }, {
            xtype: 'textfield',
            fieldLabel: '选择题选项',
            name:'answers'
        }]
    }],
   buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitEditForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
