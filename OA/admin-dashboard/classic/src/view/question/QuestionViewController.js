Ext.define('Admin.view.question.QuestionViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.questionViewController',
    /********************************************** Controller View *****************************************************/
    /*Add*/
	openAddWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('questionAddWindow')).show();
	},
    /*Edit*/
	openEditWindow:function(grid, rowIndex, colIndex){
         var record = grid.getStore().getAt(rowIndex);
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		if (record ) {
			var win = grid.up('panel').up('container').add(Ext.widget('questionEditWindow'));
			win.show();
			win.down('form').getForm().loadRecord(record);
		}
	},
	/*Search More*/	
	openSearchWindow:function(toolbar, rowIndex, colIndex){
		toolbar.up('panel').up('container').add(Ext.widget('questionSearchWindow')).show();
	},
	searchComboboxSelectChuang:function(combo,record,index){
		var searchField = this.lookupReference('searchFieldName').getValue();
		if(searchField==='textQuestion'){
			this.lookupReference('searchTextQuesValue').show();
			this.lookupReference('questionType').hide();
		}
		else{
			this.lookupReference('searchTextQuesValue').hide();
			this.lookupReference('questionType').show();
		}
		
	},
	/********************************************** Submit / Ajax / Rest *****************************************************/
	/*Add Submit*/	
	submitAddForm:function(btn){
		var win    = btn.up('window');
		var form = win.down('form');
		var record = Ext.create('Admin.model.question.QuestionModel');
		var values  =form.getValues();//获取form数据
		if(values.textQuestion==""){
			Ext.toast("题目不能为空");
			return false;
		}
		if(values.type==""){
			Ext.toast("要选择一个题目类型");
			return false;
		}
		record.set(values);
		record.save();
		Ext.data.StoreManager.lookup('questionGridStroe').load();
		win.close();
	},
	/*Edit Submit*/	
	submitEditForm:function(btn){
		var win    = btn.up('window');
		var store = Ext.data.StoreManager.lookup('questionGridStroe');
		var values  = win.down('form').getValues();//获取form数据
		var record = store.getById(values.id);//获取id获取store中的数据
		record.set(values);//rest put 
		//store.load();
		win.close();
		Ext.toast("修改成功");
	},
		
	/*Quick Search*/	
	quickSearch:function(btn){
		var store =	btn.up('gridpanel').getStore();
		Ext.apply(store.proxy.extraParams, {type:"",textQuestion:"",realanswer:"",answers:"",status:""});
		var searchField = this.lookupReference('searchFieldName').getValue();
		
		var searchValue = this.lookupReference('searchTextQuesValue').getValue();
		var searchQuestionTypeValue = this.lookupReference('questionType').getValue();
		
		//var store = Ext.getCmp('userGridPanel').getStore();// Ext.getCmp(）需要在questionPanel设置id属性
		
		
		if(searchField==='textQuestion'){
			Ext.apply(store.proxy.extraParams, {textQuestion:searchValue});
		}
		if(searchField==='type'){
			Ext.apply(store.proxy.extraParams, {type:searchQuestionTypeValue});
		}
		store.load({params:{start:0, limit:20, page:1}});
	},
	submitSearchForm:function(btn){
		var store =	Ext.data.StoreManager.lookup('questionGridStroe');
		var win = btn.up('window');
		var form = win.down('form');
		var values  = form.getValues();
		Ext.apply(store.proxy.extraParams, {id:"",name:"",entryTime:""});
		Ext.apply(store.proxy.extraParams,{
			id:values.id,
			name:value.name,
			entryTime:Ext.util.Format.date(values.entryTime, 'Y/m/d H:i:s')
		});
		store.load({params:{start:0, limit:20, page:1}});
		win.close();
	},
	/*Delete One Row*/	
	deleteOneRow:function(grid, rowIndex, colIndex){
	   Ext.MessageBox.confirm('提示', '确定要进行删除操作吗？数据将无法还原！',
  			function(btn, text){
            	if(btn=='yes'){
            		var store = grid.getStore();
					var record = store.getAt(rowIndex);
					store.remove(record);//DELETE //http://localhost:8081/question/112
					//store.sync();
				}
        	}
        , this);
	},
	/*Delete More Rows*/	
	deleteMoreRows:function(btn, rowIndex, colIndex){
		var grid = btn.up('gridpanel');
		var selModel = grid.getSelectionModel();
        if (selModel.hasSelection()) {
            Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                if (button == "yes") {
                    var rows = selModel.getSelection();
                    var selectIds = []; //要删除的id
                    Ext.each(rows, function (row) {
                        selectIds.push(row.data.id);
                    });
                  	Ext.Ajax.request({ 
						url : 'http://localhost:8080/textquestion/deletes', 
						method : 'post', 
						params : { 
							//ids[] :selectIds
							ids :selectIds
						}, 
						success: function(response, options) {
			                var json = Ext.util.JSON.decode(response.responseText);
				            if(json.success){
				            	Ext.Msg.alert('操作成功', json.msg, function() {
				                    grid.getStore().reload();
				                });
					        }else{
					        	 Ext.Msg.alert('操作失败', json.msg);
					        }
			            }
					});
                }
            });
        }else {
            Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
        }
    },

	/*Check*/	
	onCheckButton:function(grid, rowIndex, colIndex){
		var record = grid.getStore().getAt(rowIndex);
        var win = grid.up('panel').up('container').add(Ext.widget('questionLookWindow'));
        win.show();
        var form=win.down('form').getForm();
		//获取选中数据的字段值：console.log(record.get('id')); 或者 console.log(record.data.id);
		if (record ) {
			form.loadRecord(record);
		}
		var containerRealanswer=Ext.getCmp("containerRealanswer");
        var realAnswer =form.findField("realanswer").getValue();
        var answers =form.findField("answers").getValue();
        //var realAnswerCmp =form.findField("realanswer");
        var containerAnswers =Ext.getCmp("containerAnswers");
        var realContainerCmp=Ext.getCmp("realContainerCmp");
        
        if (form.findField("type").getValue() == 1) {//多选
            //form.findField("answers").setHidden(false);
            var strs = realAnswer.split("&");
            /*var items = Ext.create("Ext.form.CheckboxGroup", {
                disabled: true,
                margin: '0 0 20 0',
                cls: 'x-check-group-alt',
                columns: 1
            });*/
            for (var i = 0; i < strs.length; i++) {		//遍历标准答案选项
                //console.log(strs[i]);
                var checkboxitems = Ext.create("Ext.form.field.Display", {value: strs[i]});
                //items.add(checkboxitems);
                containerRealanswer.add(checkboxitems);
            }
            //containerRealanswer.add(items);
            containerRealanswer.updateLayout();
            
            var arrs = answers.split("&");
            for (var i = 0; i < arrs.length; i++) {		//遍历选择题选项
                //console.log(strs[i]);
                var displayitems = Ext.create("Ext.form.field.Display", {value: arrs[i]});
                //items.add(checkboxitems);
                containerAnswers.add(displayitems);
            }
            containerAnswers.updateLayout();
        }
        else if (form.findField("type").getValue() == 2) {//填空
        	 realContainerCmp.setHidden(true);
        	 var strs = realAnswer.split("&");
             for (var i = 0; i < strs.length; i++) {		//遍历标准答案选项
                 var checkboxitems = Ext.create("Ext.form.field.Display", {value: strs[i]});
                 containerRealanswer.add(checkboxitems);
             }
             containerRealanswer.updateLayout();
        }
        else if (form.findField("type").getValue() == 0) {//单选
        	 var arrs = answers.split("&");
             for (var i = 0; i < arrs.length; i++) {		//遍历选择题选项
                 //console.log(strs[i]);
                 var displayitems = Ext.create("Ext.form.field.Display", {value: arrs[i]});
                 //items.add(checkboxitems);
                 containerAnswers.add(displayitems);
             }
             containerAnswers.updateLayout();
             var checkboxitems = Ext.create("Ext.form.field.Display", {value:realAnswer});
             containerRealanswer.add(checkboxitems);
             containerRealanswer.updateLayout();
        }


	}
});