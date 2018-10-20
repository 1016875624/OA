Ext.define('Admin.ApplicationController', {
    extend: 'Ext.app.ViewController',
    //alias: 'controller.applicationcontroller',
    
	// init:function(){
    // 	console.log("application init");
	// }
    doInit:function () {
        Ext.Ajax.request({
            url: 'http://localhost:8080/employee/user9',
            async:false,
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                window.datas=obj.root;
            }
        });
    },
    onLaunch:function () {

    },
    finishInit:function () {

    },
});
