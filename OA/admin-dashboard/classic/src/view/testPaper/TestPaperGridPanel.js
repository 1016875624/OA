var n = 0;
var theNumOfQuestion = 1;
Ext.define('Admin.view.testPaper.TestPaperGridPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'testPaperGridPanel',	
    requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
        'Ext.form.field.ComboBox',
        'Ext.selection.CheckboxModel',
        'Ext.form.field.Date',
        'Ext.grid.column.Date'
    ],
    layout: 'fit',
    //shadow:true,
    bodyPadding: 7,
    bodyStyle: 'background:#f7f8f9;',//filter:alpha(opacity=50);-moz-opacity:0.5;opacity: 0.5;',
  
    //title:"加薪考试！",
    items: [{
    	xtype:"form",
    	height:800,
    	padding:'30 50 50 50',
    	title:'OA 办 公 自 动 加 薪',
    	scrollable:true,
    	titleAlign:'center',
    	bodyStyle: 'background:#fff;border-radius: 9px;',
		items:[],
		tbar: [{
				xtype: 'displayfield',
				fieldLabel:'考试时间范围:',
				id:"begintestime",
				
			  },{
				xtype: 'displayfield',
				id:"endtestime",
			  },'->',{
				xtype: 'displayfield',
				//text:'',
				margin:'0 50 0 0',
				style:{
					color:'#FF7744',
					size:'15px'
				},
				id:'scoreId',
				hidden:true
			  },{
				  xtype: 'label',
			      width: 100,
			      margins: {left: 10},
			      id: 'clock',
			      listeners: {
			      'render': function() {
			    	  Ext.TaskManager.start({
			    		    run: function() {
			    		      Ext.getCmp("clock").setText(Ext.Date.format(new Date(), 'H:i:s A'));
			    		    },
			    		    interval: 1000
			    	   });
			    	  
			       }
			      
			     }
			  },{
		    	text: '提交试卷',
		        iconCls:'fa fa-pencil',
		        id: 'tbarSubmitId',
		        hidden:false,
		        handler:'submitTestForm'
//		        	function(btn){
//		        	
//		        	var form=this.up("form");
//		        	console.log(form);
//		        	console.log(Ext.ClassManager.getName(form));
//		        	var values=form.getValues();
//		        	
//		        	var questionStore=Ext.data.StoreManager.lookup("paperQuestionGridStore");
////		        	var questionDatas=questionStore.getRange();		//传回后台
////		        	var questionDatas=questionStore.getRange();		//传回后台
//		        	questionDatas=new Array();
//		        	questionStore.each(function(r){
//		        		var temp=r.copy().getData();
//		        		//delete temp.id;
//		        		questionDatas.push(temp);
//		        		
//		        	});
//		        	console.log(values);
//		        	console.log(Ext.ClassManager.getName(values));
//		        	console.log(Ext.ClassManager.getName(questionDatas));
//		        	console.log(questionDatas);
//		        	
//		        	//试卷的store
//		        	var paperTeststore=Ext.data.StoreManager.lookup("testPaperGridStroe");
//		        	paperDatas=new Array();
//		        	paperTeststore.each(function(r){
//		        		paperDatas.push(r.copy().data);
//		        	});
//		        	
//		        	//var lists = new Array();
//		        	/*var newanswer="";
//		        	for(var i=1;i<=10;i++){
//		        		if(i>=1 && i<=3){
//		        			var answeri=values.get("answer"+i);
//		        			lists.push(answeri);
//		        		}
//		        		else if(i>=4 && i<=6){
//		        			var answeri=values.get("answer"+i);
//		        			if(answeri.length==1){
//		        				lists.push(answeri);
//		        			}else if(answeri>1){
//		        				for(var i=0;i<answeri.length;i++){
//		        					newanswer=answeri[i]+"&";
//		        				}
//		        			}
//		        		}
//		        		else if(i>=7 && i<=10){
//		        			values.get("answer"+i);
//		        		}
//		        	}
//		        	
//		        	/*console.log(Ext.ClassManager.getName(lists));
//		        	var index345="";
//		        	for(var i=0;i<values.length;i++){
//		        		console.log("11000");
//		        		console.log(values[i].length);
//		        		if(i>=0 && i<=2){
//		        			lists.push(values[i]);
//		        		}
//		        		
//		        		if(i>=3 && i<=5){
//		        			if(values[i].length>1){
//		        				
//		        			}
//		        		}
//		        		if(i>=6 && i<=9){
//		        			if(values[i].length>1){
//		        				lists.push(values[i]);
//		        			}
//		        		}
//		        	}*/
//		        	
//		        	Ext.Msg.confirm("警告", "确定要提交试卷吗？", function (button) {
//		                if (button == "yes") {
////		                	var myMask = new Ext.LoadMask(Ext.getBody(), { 
////		                         msg: '正在统计考试分数，请稍后！'
////		                    }); 
////		                    myMask.show();
//		                  	Ext.Ajax.request({ 
//								url : 'http://localhost:8080/testPaper/getScore', 
//								method : 'post', 
//								/*params : { 
//									testPaperDTO :values,
//									questions:questionDatas
//								}, */
//								jsonData:{
//									answers :values,
//									questions:questionDatas,
//									testPaper:paperDatas
//								},
//								success: function(response, options) {
//					                var json = Ext.util.JSON.decode(response.responseText);
//						            if(json.success){
//						            	//myMask.hide();
//						            	Ext.Msg.alert('操作成功', "你的分数为："+json.msg+" 分..", function() {
//						            		var scoreProduce=Ext.getCmp("scoreId");
//						            		scoreProduce.setHidden(false);
//						            		scoreProduce.setText(json.msg+" 分");
//						                });
//							        }else{
//							        	 //myMask.hide();
//							        	 Ext.Msg.alert('操作失败', json.msg);
//							        }
//					            }
//							});
//		                }
//		            });
//		        	
//		        }
		}],
		buttons: ['->',{
		        xtype: 'button',
		        text: '提交试卷',
		        hidden:false,
		        id:'buttonSubmitId',
		        iconCls:'fa fa-pencil',
		        handler: 'submitTestForm'
		},'->'],
    	listeners:{
    		afterrender:function() {
    			//时间变化
    			
    			/*over*/
    			var form=this;
    			console.log(form);
    			var testPaperstore=Ext.data.StoreManager.lookup("testPaperGridStroe");//试卷的store
    	    	var store=Ext.data.StoreManager.lookup("paperQuestionGridStore");//题目store
    	    	
    	    	testPaperstore.setListeners({load:function(testPaperstore,records){
    	    		var record=records[0];
    	    		//console.log(record.getData());
    	    		//console.log(record.getData().startTime);
    	    		var starttime=record.getData().startTime;
    	    		starttime=Ext.util.Format.date(starttime,'Y/m/d H:i:s');
    	    		Ext.getCmp("begintestime").setValue(starttime+" 一");
    	    		
    	    		var endtime=record.getData().endTime;
    	    		endtime=Ext.util.Format.date(endtime,'H:i:s');
    	    		Ext.getCmp("endtestime").setValue(endtime);
    	    	}});
    	    	//console.log(testPaperstore.getData());
    	    	//testPaperstore.load();
    	    	//console.log(testPaperstore);
    	    	//console.log(testPaperstore.getData());
    	    	//store.load();
    	    	//var testPaperDa=testPaperstore.getAt(1).getData();
    	    	//Ext.getCmp("begintestime").setValue(testPaperDa.startTime);
    	    	//console.log(testPaperDa);
    	    	//var record=testPaperstore.getAt(0);
    	    	//console.log(record);
    	    	//console.log(Ext.ClassManager.getName(testPaperstore.getData()));
    	    	
    	    	//console.log(store);
    	    	//console.log(store.getData());
    	    	store.setListeners({load:function(store,records){
    	    		//console.log(store);
    	    		console.log(store.getAt(0));
    	    		store.each(function(r){ //循环后台传过来的随机十道题目
        	    		//console.log("111");
        	    		var temp=r.getData();			//获取每一道题
        	    		//console.log(temp);
        	    		
        	    		if(temp.type==0){				//如果是单选题
        	    			var answers=temp.answers;//选择题选项
        	    			var strs=answers.split("&");	//分割选择题选项
        	    			
        	    			var questionText=Ext.create("Ext.Component",{margin:'0 0 0 20',html:"<p style='color:#666666;font-size:15px'>"+"(单选题)   "+theNumOfQuestion+".      "+temp.textQuestion+"</p>"});
        	    			var items= Ext.create("Ext.form.RadioGroup",{margin:'0 0 40 20',cls: 'x-check-group-alt',columns: 1});
        	    			//console.log(strs);
        	    			for(var i=0;i<strs.length;i++){		//遍历选择题选项
        	    				
        	    				console.log(strs[i]);
        	    				var radioitems=Ext.create("Ext.form.field.Radio",{boxLabel:strs[i],name:"answer"+theNumOfQuestion,inputValue:strs[i]});
        	    				
        	    				items.add(radioitems);
        	    				
        	    			}
        	    			//console.log(items);
        	    			//console.log(Ext.ClassManager.getName(items));
        	    			//console.log(form);
        	    			//console.log(form.items);
        	    			form.add(questionText);
        	    			form.add(items);
        	    			//form.items.items.push(items);
        	    			form.updateLayout();
        	    			
        	    		}
        	    		else if(temp.type==1){		//如果是多选题
        	    			var answers=temp.answers;//选择题选项
        	    			var strs=answers.split("&");	//分割选择题选项
        	    			var questionText=Ext.create("Ext.Component",{margin:'0 0 0 20',html:"<p style='color:#666666;font-size:15px'>"+"(多选题)   "+theNumOfQuestion+".      "+temp.textQuestion+"</p>"});
        	    			var items= Ext.create("Ext.form.CheckboxGroup",{margin:'0 0 40 20',cls: 'x-check-group-alt',columns: 1});
        	    			
        	    			for(var i=0;i<strs.length;i++){		//遍历选择题选项
        	    				
        	    				//console.log(strs[i]);
        	    				var checkboxitems=Ext.create("Ext.form.field.Checkbox",{boxLabel:strs[i],name:"answer"+theNumOfQuestion,inputValue:strs[i]});
        	    				
        	    				items.add(checkboxitems);
        	    				
        	    			}
        	    			
        	    			form.add(questionText);
        	    			form.add(items);
        	    			//form.items.items.push(items);
        	    			form.updateLayout();
        	    			
        	    		}else if(temp.type==2){		//填空题	
        	    			var realanswer=temp.realanswer;
        	    			var strs=realanswer.split("$");	
        	    			
        	    			var questionText=Ext.create("Ext.Component",{margin:'0 0 0 20',html:"<p style='color:#666666;font-size:15px'>"+"(填空题)   "+theNumOfQuestion+".      "+temp.textQuestion+"</p>"});
        	    			var items=Ext.create("Ext.form.FieldContainer",{margin: '0 0 40 20',hideLabel: true});
        	    			for(var i=1;i<=strs.length;i++){
        	    				var textfielditems=Ext.create("Ext.form.field.Text",{fieldLabel:"第"+i+"空",name:"answer"+theNumOfQuestion});
        	    				items.add(textfielditems);
        	    			}
        	    			form.add(questionText);
        	    			form.add(items);
        	    			form.updateLayout();
        	    			
        	    		}
        	    		theNumOfQuestion++;
        	    	});

        	    	
    	    	}})
    	    	
    		}
    	}
    }],
   
   
            
});
//var submitTestForm=submitTestForm:function(btn){
//	var form=btn.up("panel").down("form");
//	var values=form.getValue();
//	console.log(form);
//	console.log(values);
//	
//}