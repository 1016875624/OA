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
        	    				var radioitems=Ext.create("Ext.form.field.Radio",{boxLabel:strs[i],name:"answer"+theNumOfQuestion});
        	    				
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
        	    				var checkboxitems=Ext.create("Ext.form.field.Checkbox",{boxLabel:strs[i],name:"answer"+theNumOfQuestion});
        	    				
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
        	    				var textfielditems=Ext.create("Ext.form.field.Text",{fieldLabel:"第"+i+"空"});
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
    tbar: ['->',{
    	text: '提交试卷',
        iconCls:'fa fa-pencil',
        itemId: 'submit',
        handler: 'submitTestForm'	
    }],
    buttons: ['->',{
        xtype: 'button',
        text: '提交试卷',
        iconCls:'fa fa-pencil',
        handler: 'submitTestForm'
    },'->']
   
            
});