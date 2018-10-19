Ext.define('Admin.view.testPaper.TestPaperCenterPanel', {
    extend: 'Ext.container.Container',
    xtype: 'testPaperCenterPanel',
    controller: 'testPaperViewController',
    viewModel: {type: 'testPaperViewModel'},
    layout: 'fit',
    items: [{xtype:'testPaperGridPanel'}]
//   initComponent: function() {
//        this.text = new Date();
//        this.renderTo = Ext.getBody();
//        this.callParent();
    	//var form=this.down("panel").down("form");
    	//console.log(form);
//    	var testPaperstore=Ext.data.StoreManager.lookup("testPaperGridStroe");//试卷的store
//    	var paperQuestionstore=Ext.data.StoreManager.lookup("paperQuestionGridStore");//题目store
//    	
//    	
//    	testPaperstore.load();
//    	paperQuestionstore.load();
//    	
//    	console.log(testPaperstore);
//    	console.log(paperQuestionstore);
//    	
//    	paperQuestionstore.each(function(r){ //循环后台传过来的随机十道题目
//    		var temp=r.getData();			//获取每一道题
//    		if(temp.type==1){				//如果是多选题
//    			var answers=temp.answers;//选择题选项
//    			var strs=answers.split("&");	//分割选择题选项
//    			var items1={	//题目展示
//					xtype : "displayFieldType",
//					value :temp.textQuestion
//    			}
//    			var items={		//多选题选项
//    	    		xtype:"checkboxgroup",
//    	    		cls: 'x-check-group-alt',
//        	    }
//    			for(var i=0;i<strs.length;i++){		//遍历选择题选项
//    				var radioitems={		//选择题选项
//    						boxLabel:strs[i],
//    						name:"answer"+i
//    				} 
//    				items.add(radioitems);	//选择题选项加入到 checkboxgroup
//    				items.doLayout();
//    			}
//    			
//    	    	form.add(items1);
//    	    	form.doLayout();
//    	    	
//    	    	form.add(items);
//    	    	form.doLayout();
//    		}else if(temp.type==0){		//如果是单选题
//    			var answers=temp.answers;//选择题选项
//    			var strs=answers.split("&");	//分割选择题选项
//    			var items1={	//题目展示
//					xtype : "displayFieldType",
//					value :temp.textQuestion
//    			}
//    			var items={		
//        	    		xtype:"radiogroup",
//        	    		cls:'x-check-group-alt',
//        	    }
//    			for(var i=0;i<strs.length;i++){
//    				var radioitems={		//选择题选项
//    						boxLabel:strs[i],		//选项展示
//    						inputValue:"answer"+i	//选项的值
//    				} //选择题选项
//    				items.add(radioitems);	//选择题选项加入到 radiogroup
//    				items.doLayout();
//    			}
//    			
//    	    	form.add(items1);
//    	    	form.doLayout();
//    	    	
//    	    	form.add(items);
//    	    	form.doLayout();
//    			
//    		}else if(temp.type==2){		//填空题	
//    			var strs=answers.split("$");	
//    			var items={	//题目展示
//    					xtype : "displayFieldType",
//    					value :temp.textQuestion
//        		}
//    			form.add(items);
//    			form.doLayout();
//    			for(var i=0;i<strs.length;i++){
//    				var textfield={
//    						xtype:"textfield",
//    						name:"answer"+i
//    				}
//    				form.add(textfield);
//    				form.doLayout();
//    			}
//    			
//    		}
//	    	
//    	});
//    }
});