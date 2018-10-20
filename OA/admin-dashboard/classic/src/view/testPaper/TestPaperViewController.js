Ext.define('Admin.view.testPaper.TestPaperViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.testPaperViewController',
    id:"testPaperViewController",
    /********************************************** Controller View *****************************************************/
    submitTestForm:function(btn){
//    	Ext.Msg.confirm("警告", "确定要提交试卷吗？", function (button) {
//    		if (button == "yes") {
//    			submitPaper(btn);
//            }
//        });
    	
    	
    	this.submitPaper();
    },

	submitPaper:function(){
		console.log(111);
//		var form=btn.up("form");
		var form=this.lookupReference("paperForm");
    	console.log(form);
    	console.log(Ext.ClassManager.getName(form));
    	var values=form.getValues();
    	
    	var questionStore=Ext.data.StoreManager.lookup("paperQuestionGridStore");
//    	var questionDatas=questionStore.getRange();		//传回后台
//    	var questionDatas=questionStore.getRange();		//传回后台
    	questionDatas=new Array();
    	questionStore.each(function(r){
    		var temp=r.copy().getData();
    		//delete temp.id;
    		questionDatas.push(temp);
    		
    	});
    	console.log(values);
    	console.log(Ext.ClassManager.getName(values));
    	console.log(Ext.ClassManager.getName(questionDatas));
    	console.log(questionDatas);
    	
    	//试卷的store
    	var paperTeststore=Ext.data.StoreManager.lookup("testPaperGridStroe");
    	paperDatas=new Array();
    	paperTeststore.each(function(r){
    		paperDatas.push(r.copy().data);
    	});
//	Ext.Msg.confirm("警告", "确定要提交试卷吗？", function (button) {
//        if (button == "yes") {
//        	var myMask = new Ext.LoadMask(Ext.getBody(), { 
//                 msg: '正在统计考试分数，请稍后！'
//            }); 
//            myMask.show();
          	Ext.Ajax.request({ 
				url : 'http://localhost:8080/testPaper/getScore', 
				method : 'post', 
				/*params : { 
					testPaperDTO :values,
					questions:questionDatas
				}, */
				jsonData:{
					answers :values,
					questions:questionDatas,
					testPaper:paperDatas
				},
				success: function(response, options) {
	                var json = Ext.util.JSON.decode(response.responseText);
		            if(json.success){
		            	//myMask.hide();
		            	Ext.Msg.alert('操作成功', "你的分数为："+json.msg+" 分..", function() {
		            		var scoreProduce=Ext.getCmp("scoreId");
		            		scoreProduce.setHidden(false);
		            		scoreProduce.setValue("<span color='red'>"+json.msg+" 分</span>");
		            		Ext.getCmp("tbarSubmitId").setHidden(true);
		            		Ext.getCmp("buttonSubmitId").setHidden(true);
		                });
			        }else{
			        	 //myMask.hide();
			        	 Ext.Msg.alert('操作失败', json.msg);
			        }
	            }
			});
	}
});