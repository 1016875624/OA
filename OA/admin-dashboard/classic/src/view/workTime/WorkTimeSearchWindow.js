Ext.define('Admin.view.workTime.WorkTimeSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.workTimeSearchWindow',
    height: 450,
    minHeight: 300,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Search More Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your searchvalue',
        items: [{
            xtype: 'textfield',
            fieldLabel: 'id',
            name:'id',
            hidden: true,
            readOnly: true
        },{
           xtype: 'textfield',
           fieldLabel: '员工编号',
           name:'employeeid'
       }, {
           xtype: 'textfield',
           fieldLabel: '员工姓名',
           name:'employeeName'
       }, {
           xtype: 'combobox',
           fieldLabel: '请选择部门',
           name:'departmentName',
    	   store:Ext.create("Ext.data.Store", {
    		   proxy: new Ext.data.HttpProxy({ 
    			   type: 'ajax',
    			   method:"post",
    			   url: "/department/simpleget",
    			   //root:'names'
    		   }),  
    		    /*reader: new Ext.data.JsonReader(  
    		                  { rootProperty: "names" },  
    		                  ["value", "name"]  
    		                  ),*/
    		   autoLoad : true
    	   }),
    	   //valueField:'value',
    	   hiddeName:'name',
    	   displayField:'name',
    	   emptyText: '请选择部门',
    	   mode: 'remote',
    	   triggerAction:'all',
    	   selectOnFocus: true,  
           allowBlank: false
           
           //editable: false
//           listeners:{    
//               select : function(combo, record,index){
//                   isEdit = true;
//               }
//           },
//           renderer:function(value,metadata,record){
//        	   if(isEdit){
//                   var index = cbstore.find(Ext.getCmp('content').valueField,value);
//                   var record = cbstore.getAt(index);
//                   return record.data.name;
//               }else{
//                   return value;
//               }
//           }
       }, {
           xtype: 'textfield',
           fieldLabel: '当天工作时间(单位：h)',
           name:'hour'
       }, {
           xtype: 'datefield',
           fieldLabel: '日期',
           name:'date',
           format: 'Y/m/d'
       }]
    }],
	buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitSearchForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
});