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
           name:'departmentid',
           store:Ext.data.StoreManager.lookup("departmentBoxStroe"),
		   displayField:'name',
		   valueField:'id',
		   //value:'',
		   editable:false,
		   
		   triggerAction: 'all',
		   emptyText: 'Select a state...',
		   width: 135
		   
		   
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