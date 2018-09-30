var n = 0;

Ext.define('Admin.view.question.QuestionAddWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.questionAddWindow',
	// height : 500,
	minHeight : 500,
	minWidth : 300,
	width : 500,
	scrollable : true,
	title : 'Add question Window',
	closable : true,
	constrain : true,

	defaultFocus : 'textfield',
	modal : true,
	// layout : 'fit',
	items : [ {
		xtype : 'form',
		// layout : 'form',
		padding : '10px',
		ariaLabel : 'Enter your TextQuestion',
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
			anchor : '100%'
		}, {
			xtype: 'container',
            layout: 'hbox',
            margin: '0 0 5 0',
            items:[
            	{
            		xtype : 'combobox',
	            	fieldLabel : '题目类型',
	    			name : 'type',
	    			reference:"timu",
	    			editable : false,
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
    				listeners:{
    					change:function(box,newValue,oldValue,eOpts){
    						var questype=this.getValue();
    						this.up("window").down("form").getForm().down('#answerId').setDisabled(false);
    						
    						if(questype=='0'){
    							
    							
    						}
    					}
    				}
            		},
            		{
		            	xtype:"button",
		        		text:"添加答案",
		        		itemId:"answerId",
		        		disabled:true,
		        		handler:function(btn){
		        			n++;
		        			if(n>4){
		        				Ext.Msg.alert("error","max is five.");
		        				return;
		        			}
		        			var fd = new Ext.form.TextField({
		        				name : 'realanswer' + n,
		        				id : 'realanswer' + n,
		        				fieldLabel:"答案"+n,
		        			});
		        			var form=btn.up("window").down("form");
		        			form.add(fd);
		        		}
            		}
            ]
			
		}, 
		{
			xtype : 'textfield',
			fieldLabel : '选择题选项',
			name : 'answers',
			hidden : true
		}
		
		]
	}],
	buttons : [ '->', {
		xtype : 'button',
		text : 'Submit',
		handler : 'submitAddForm'
	}, {
		xtype : 'button',
		text : 'Close',
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
