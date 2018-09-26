Ext.define('Admin.view.workTime.WorkTimeEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.workTimeEditWindow',
    height: 450,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Edit workTime Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your name',
        items: [
		{
             xtype: 'textfield',
             fieldLabel: 'id',
             name:'id',
             hidden: true,
             readOnly: true
         }, 
		{
            xtype: 'textfield',
            fieldLabel: '员工编号',
            name:'employeeid',
            disabled:true
        }, {
            xtype: 'textfield',
            fieldLabel: '员工姓名',
            name:'employeeName',
            disabled:true
        }, {
            xtype: 'textfield',
            fieldLabel: '部门名称',
            name:'departmentName',
            disabled:true
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
        handler: 'submitEditForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
