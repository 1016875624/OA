Ext.define('Admin.view.question.QuestionEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.questionEditWindow',
    height: 300,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: '修改题目',
    closable: true,
    autoShow: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items : [ {
		xtype : 'form',
		// layout : 'form',
		padding : '10px',
		ariaLabel : '修改题目',
		items : [ {
			xtype : 'textfield',
			fieldLabel : 'id',
			name : 'id',
			hidden : true,
			readOnly : true
		}, {
			xtype : 'textareafield',
			grow : true,
			name : 'textQuestion',
			fieldLabel : '题目',
			anchor : '100%',
			allowBlank:false
		}, {
			
    		xtype : 'combobox',
        	fieldLabel : '题目类型',
			name : 'type',
			//reference:"timu",
			itemId:"timu",
			disabled : true,
			store : Ext.create('Ext.data.Store', {
				fields : [ 'value', 'name' ],
				data : [ {
					"value" : "0",
					"name" : "单选题"
				}, {
					"value" : "1",
					"name" : "多选题"
				}, {
					"value" : "2",
					"name" : "填空题"
				} ]
			}),
			emptyText : '选择题目类型',
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
		}
		]
	}],
	buttons : [ '->', {
		xtype : 'button',
		text : '提交',
		handler : 'submitEditForm'
	}, {
		xtype : 'button',
		text : '取消',
		handler : function(btn) {
			btn.up('window').close();
		}
	}, '->' ],
	listeners:{
		destroy:function(window){
			n=0;
		},
		close:function(window){
			n=0;
		}
	}
  
});
