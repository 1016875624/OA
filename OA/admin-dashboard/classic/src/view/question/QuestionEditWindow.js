
Ext.define('Admin.view.question.QuestionEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.questionEditWindow',
    height: 600,
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
        items: [
    	{
			xtype     : 'textareafield',
			grow      : true,
			name      : 'textQuestion',
			fieldLabel: '题目',
			anchor    : '100%'
		} ,{
            xtype: 'textfield',
            fieldLabel: '题目类型',
            name:'type'
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
