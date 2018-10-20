Ext.define('Admin.view.question.QuestionLookWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.questionLookWindow',
	height : 400,
	minHeight : 100,
	minWidth : 300,
	width : 500,
	scrollable : true,
	title : '查看题目',
	closable : true,
	autoShow : true,
	constrain : true,
	defaultFocus : 'textfield',
	modal : true,
	layout : 'fit',
	items : [ {
		xtype : 'form',
		// layout : 'form',
		margin : '20 20 20 20',
		padding : '10px',
		ariaLabel : '修改题目',
		items : [ {
			xtype : 'textfield',
			fieldLabel : 'id',
			name : 'id',
			hidden : true,
			readOnly : true
		}, {
			xtype : 'displayfield',
			grow : true,
			name : 'textQuestion',
			fieldLabel : '题目',
			anchor : '100%',
		}, {

			xtype : 'displayfield',
			fieldLabel : '题目类型',
			name : 'type',
			renderer : function(val) {
				if (val == '0') {
					return '<span>单选题</span>';
				} else if (val == '1') {
					return '<span>多选题</span>';
				} else if (val == '2') {
					return '<span>填空题</span>';
				}
				return val;

			}
		}, {
			//xtype:'displayfield',
			xtype : "textfield",
			//hidden:true,
			name : 'realanswer',
			id : 'hiddenRealanswerId'
		}, {

			xtype : 'container',
			//fieldLabel : '标准答案',
			layout : 'hbox',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '标准答案'

			}, {
				xtype : 'container',
				layout : "vbox",
				id : "containerRealanswer",
			//hidden:true

			} ]
		//name : 'realanswer',
		}, {
			xtype : 'displayfield',
			fieldLabel : '选择题选项',
			name : 'answers',
		} ],
		listeners : {
			//afterrender
			afterrender : function() {
				//console.log(Ext.ClassManager.getName();
				
				console.log(Ext.getCmp("hiddenRealanswerId").getValue());
				var realAnswer = Ext.getCmp("hiddenRealanswerId").getValue();
				console.log(realAnswer);
				var containerRealanswer = Ext.getCmp("containerRealanswer");
				if (this.getForm().findField("type").getValue() == 1) {//多选
					this.getForm().findField("answers").setHidden(false);
					var strs = realAnswer.split("&");
					var items = Ext.create("Ext.form.CheckboxGroup", {
						disabled : true,
						margin : '0 0 20 0',
						cls : 'x-check-group-alt',
						columns : 1
					});
					for (var i = 0; i < strs.length; i++) { //遍历选择题选项
						//console.log(strs[i]);
						var checkboxitems = Ext.create(
								"Ext.form.field.Checkbox", {
									boxLabel : strs[i]
								});
						items.add(checkboxitems);
					}
					containerRealanswer.add(items);
					containerRealanswer.updateLayout();
				} else if (this.getForm().findField("type").getValue() == 2) {//填空
					this.getForm().findField("answers").setHidden(true);

				} else if (this.getForm().findField("type").getValue() == 0) {//单选
					this.getForm().findField("answers").setHidden(false);
					var items = Ext.create("Ext.form.field.Display", {
						margin : '0 0 20 0',
						value : realAnswer
					});
					containerRealanswer.add(items);
					containerRealanswer.updateLayout();
				}

			}
		 },
		afterlayout:function(){}
	} ],

});
