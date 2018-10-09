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
	    			//reference:"timu",
	    			itemId:"timu",
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
	    			margin: '0 40 0 0',
	    			emptyText : '选择题目类型',
	    			queryMode : 'local',
	    			displayField : 'name',
	    			valueField : 'value',
    				listeners:{
    					change:function(box,newValue,oldValue,eOpts){
    						var questype=this.getValue();
    						this.up("window").down("form").down('#answerId').setDisabled(false);
    						
    						//Ext.getCmp("answerId").setDisabled(false);
    						if(questype=='0'){
    							this.up('window').down("form").down("#answersId").setHidden(false);
    							this.up("window").down("form").down('#answerId').setHidden(true);
    							this.up("window").down("form").down('#includeRealanswer').setHidden(true);
    						}else if(questype=='1'){
    							this.up('window').down("form").down("#answersId").setHidden(false);
    							this.up("window").down("form").down('#answerId').setHidden(true);
    							this.up("window").down("form").down('#includeRealanswer').setHidden(true);
    						}else if(questype=='2'){
    							this.up('window').down("form").down("#answersId").setHidden(true);
    							this.up("window").down("form").down('#answerId').setHidden(false);
    							this.up("window").down("form").down('#includeRealanswer').setHidden(false);
    						}
    					}
    				}
            		},
            		{
		            	xtype:"button",
		        		text:"添加答案",
		        		itemId:"answerId",
		        		disabled:true,
		        		hidden:true,
		        		handler:function(btn){
		        			n++;
		        			if(n>4){
		        				Ext.Msg.alert("error","max is four.");
		        				return;
		        			}
		        			var fd = new Ext.form.TextField({
		        				name : 'realanswer' + n,
		        				id : 'realanswer' + n,
		        				fieldLabel:"答案"+n,
		        			});
		        			var form=btn.up("window").down("form").down("#includeRealanswer");
		        			form.add(fd);
		        		}
            		}
            ]
			
		},
		{
			xtype:"container",
			itemId:"includeRealanswer",
			layout:"vbox",
			hidden:true
		},{//单选题答案的隐藏域
			xtype:"textfield",
			itemId:"realAn",
			name:"realanswer",
			hidden:true
		},{
			//多选题答案的隐藏域
			xtype:"textfield",
			itemId:"realA",
			name:"realanswerA",
			hidden:true
		},{
			xtype:"textfield",
			itemId:"realB",
			name:"realanswerB",
			hidden:true
		},{
			xtype:"textfield",
			itemId:"realC",
			name:"realanswerC",
			hidden:true
		},{
			xtype:"textfield",
			itemId:"realD",
			name:"realanswerD",
			hidden:true
		},{
			//xtype : 'textfield',
			//text: '选择题选项',
			//name : 'answers',
			hidden : true,
			itemId:"answersId",
			layout: 'vbox',
			//align:"left",
            margin: '0 0 9 0',
			xtype:"container",
			items:[
				{
					layout: 'hbox',xtype:"container", margin: '0 0 5 0',
					items:[
					{fieldLabel:"A",name:"answersA",xtype:"textfield",margin: '0 40 0 0',itemId:"answersAid"},
					{xtype:"button",text:"设为答案",itemId:"foranswerA",disabled:false,hidden:false,
						handler:function(btn){
							var touchChange=btn.up("window").down("form");
							//console.log(touchChange.down("timu"))
							if(touchChange.down("#timu").getValue()=="0"){
								touchChange.down("#foranswerA").setText("答案");
								
								touchChange.down("#foranswerB").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerC").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerD").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerA").setDisabled(true);
								touchChange.down("#realAn").setValue(touchChange.down("#answersAid").getValue());
								console.log(touchChange.down("#realAn").getValue());
							}else if(touchChange.down("#timu").getValue()=="1"){
								touchChange.down("#foranswerA").setText("答案");
								touchChange.down("#foranswerA").setDisabled(true);
								touchChange.down("#realA").setValue(touchChange.down("#answersAid").getValue());
							}
								
							
						}
					}]
				},
				{
					layout: 'hbox',xtype:"container",margin: '0 0 5 0',
					items:[
					{fieldLabel:"B",name:"answersB",xtype:"textfield",margin: '0 40 0 0',itemId:"answersBid"},
					{xtype:"button",text:"设为答案",itemId:"foranswerB",disabled:false,hidden:false,
						handler:function(btn){
							var touchChange=btn.up("window").down("form");
							//console.log(touchChange.down("timu"))
							if(touchChange.down("#timu").getValue()=="0"){
								touchChange.down("#foranswerB").setText("答案");
								
								touchChange.down("#foranswerA").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerC").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerD").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerB").setDisabled(true);
								touchChange.down("#realAn").setValue(touchChange.down("#answersBid").getValue());
								console.log(touchChange.down("#realAn").getValue());
							}else if(touchChange.down("#timu").getValue()=="1"){
								touchChange.down("#foranswerB").setText("答案");
								touchChange.down("#foranswerB").setDisabled(true);
								touchChange.down("#realB").setValue(touchChange.down("#answersBid").getValue());
							}
						}
					}]
				},
				{
					layout: 'hbox',xtype:"container",margin: '0 0 5 0',
					items:[
					{fieldLabel:"C",name:"answersC",xtype:"textfield",margin: '0 40 0 0',itemId:"answersCid"},
					{xtype:"button",text:"设为答案",itemId:"foranswerC",disabled:false,hidden:false,
						handler:function(btn){
							var touchChange=btn.up("window").down("form");
							//console.log(touchChange.down("timu"))
							if(touchChange.down("#timu").getValue()=="0"){
								touchChange.down("#foranswerC").setText("答案");
								
								touchChange.down("#foranswerB").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerA").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerD").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerC").setDisabled(true);
								touchChange.down("#realAn").setValue(touchChange.down("#answersCid").getValue());
								console.log(touchChange.down("#realBn").getValue());
							}else if(touchChange.down("#timu").getValue()=="1"){
								touchChange.down("#foranswerC").setText("答案");
								touchChange.down("#foranswerC").setDisabled(true);
								touchChange.down("#realC").setValue(touchChange.down("#answersCid").getValue());
							}
						}
					}]
				},
				{
					layout: 'hbox',xtype:"container",margin: '0 0 5 0',
					items:[
					{fieldLabel:"D",name:"answersD",xtype:"textfield",margin: '0 40 0 0',itemId:"answersDid"},
					{xtype:"button",text:"设为答案",itemId:"foranswerD",disabled:false,hidden:false,
						handler:function(btn){
							var touchChange=btn.up("window").down("form");
							//console.log(touchChange.down("timu"))
							if(touchChange.down("#timu").getValue()=="0"){
								touchChange.down("#foranswerD").setText("答案");
								
								touchChange.down("#foranswerB").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerA").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerC").setDisabled(true).setHidden(true);
								touchChange.down("#foranswerD").setDisabled(true);
								touchChange.down("#realAn").setValue(touchChange.down("#answersDid").getValue());
								console.log(touchChange.down("#realBn").getValue());
							}else if(touchChange.down("#timu").getValue()=="1"){
								touchChange.down("#foranswerD").setText("答案");
								touchChange.down("#foranswerD").setDisabled(true);
								touchChange.down("#realD").setValue(touchChange.down("#answersDid").getValue());
							}
						}
					}]
				},
			]
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
