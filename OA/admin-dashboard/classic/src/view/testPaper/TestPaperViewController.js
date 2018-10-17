Ext.define('Admin.view.testPaper.TestPaperViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.testPaperViewController',
    /********************************************** Controller View *****************************************************/
    submitTestForm:function(btn){
    	var form=btn.up("panel").down("form");
    	var values=form.getValue();
    	console.log(form);
    	console.log(values);
    	
    }
});