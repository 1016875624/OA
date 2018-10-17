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
    bodyPadding: 7,
    title:"加薪考试！",
    items: [{
    	xtype:"form",
    	padding:'50 50 50 50',
		items:[],
		tbar: ['->',{
		    	text: '提交试卷',
		        iconCls:'fa fa-pencil',
		        itemId: 'submit',
		        handler:function(btn){
		        	
		        	var form=this.up("form");
		        	console.log(form);
		        	console.log(Ext.ClassManager.getName(form));
		        	var values=form.getValues();
		        	
		        	var questionStore=Ext.data.StoreManager.lookup("paperQuestionGridStore");
		        	var questionDatas=questionStore.getData();		//传回后台
		        	
		        	console.log(values);
		        	console.log(Ext.ClassManager.getName(values));
		        	
		        	/*var lists = new Array();
		        	console.log(Ext.ClassManager.getName(lists));
		        	var index345="";
		        	for(var i=0;i<values.length;i++){
		        		console.log("11000");
		        		console.log(values[i].length);
		        		if(i>=0 && i<=2){
		        			lists.push(values[i]);
		        		}
		        		
		        		if(i>=3 && i<=5){
		        			if(values[i].length>1){
		        				
		        			}
		        		}
		        		if(i>=6 && i<=9){
		        			if(values[i].length>1){
		        				lists.push(values[i]);
		        			}
		        		}
		        	}*/
		        	
		        	Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
		                if (button == "yes") {
		                	var myMask = new Ext.LoadMask(Ext.getBody(), { 
		                         msg: '正在统计考试分数，请稍后！'
		                    }); 
		                    myMask.show();
		                  	Ext.Ajax.request({ 
								url : 'http://localhost:8080/testPaper/getScore', 
								method : 'post', 
								params : { 
									testPaperDTO :values,
									questions:questionDatas
								}, 
								success: function(response, options) {
					                var json = Ext.util.JSON.decode(response.responseText);
						            if(json.success){
						            	myMask.hide();
						            	Ext.Msg.alert('操作成功', json.msg, function() {
						            		
						                });
							        }else{
							        	 myMask.hide();
							        	 Ext.Msg.alert('操作失败', json.msg);
							        }
					            }
							});
		                }
		            });
		        	
		        }	
		}],
		buttons: ['->',{
		        xtype: 'button',
		        text: '提交试卷',
		        iconCls:'fa fa-pencil',
		        handler: 'submitTestForm'
		},'->'],
    	listeners:{
    		afterrender:function() {
    			var form=this;
    			console.log(form);
    			var testPaperstore=Ext.data.StoreManager.lookup("testPaperGridStroe");//试卷的store
    	    	var store=Ext.data.StoreManager.lookup("paperQuestionGridStore");//题目store
    	    	
    	    	
    	    	testPaperstore.load();
    	    	//store.load();
    	    	
    	    	console.log(testPaperstore);
    	    	testPaperstore.setListeners({load:function(store,records){
    	    		
    	    	}})
    	    	//console.log(store);
    	    	//console.log(store.getData());
    	    	store.setListeners({load:function(store,records){
    	    		console.log(store);
    	    		store.each(function(r){ //循环后台传过来的随机十道题目
        	    		//console.log("111");
        	    		var temp=r.getData();			//获取每一道题
        	    		//console.log(temp);
        	    		
        	    		if(temp.type==0){				//如果是单选题
        	    			var answers=temp.answers;//选择题选项
        	    			var strs=answers.split("&");	//分割选择题选项
        	    			
        	    			var questionText=Ext.create("Ext.Component",{html:"<h3>"+"(单选题)   "+theNumOfQuestion+".      "+temp.textQuestion+"</h3>"});
        	    			var items= Ext.create("Ext.form.RadioGroup",{cls: 'x-check-group-alt',columns: 1});
        	    			console.log(strs);
        	    			for(var i=0;i<strs.length;i++){		//遍历选择题选项
        	    				
        	    				console.log(strs[i]);
        	    				var radioitems=Ext.create("Ext.form.field.Radio",{boxLabel:strs[i],name:"answer"+theNumOfQuestion,inputValue:strs[i]});
        	    				
        	    				items.add(radioitems);
        	    				
        	    			}
        	    			console.log(items);
        	    			console.log(Ext.ClassManager.getName(items));
        	    			console.log(form);
        	    			console.log(form.items);
        	    			form.add(questionText);
        	    			form.add(items);
        	    			//form.items.items.push(items);
        	    			form.updateLayout();
        	    			
        	    		}
        	    		else if(temp.type==1){		//如果是多选题
        	    			var answers=temp.answers;//选择题选项
        	    			var strs=answers.split("&");	//分割选择题选项
        	    			var questionText=Ext.create("Ext.Component",{html:"<h3>"+"(多选题)   "+theNumOfQuestion+".      "+temp.textQuestion+"</h3>"});
        	    			var items= Ext.create("Ext.form.CheckboxGroup",{cls: 'x-check-group-alt',columns: 1});
        	    			
        	    			for(var i=0;i<strs.length;i++){		//遍历选择题选项
        	    				
        	    				console.log(strs[i]);
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
        	    			
        	    			var questionText=Ext.create("Ext.Component",{html:"<h3>"+"(填空题)   "+theNumOfQuestion+".      "+temp.textQuestion+"</h3>"});
        	    			var items=Ext.create("Ext.form.FieldContainer",{margin: '0 5 0 0',hideLabel: true});
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