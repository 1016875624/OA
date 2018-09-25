Ext.define('Admin.view.workTime.WorkTimeEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.workTimeEditWindow',
    height: 600,
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
		//{
            // xtype: 'textfield',
            // fieldLabel: 'id',
            // name:'id',
            // hidden: true,
            // readOnly: true
        // }, 
		{
            xtype: 'textfield',
            fieldLabel: 'id',
            name:'id'
        }, {
            xtype: 'textfield',
            fieldLabel: 'name',
            name:'name'
        }, {
            xtype: 'textfield',
            fieldLabel: 'partment',
            name:'partment'
        }, {
            xtype: 'textfield',
            fieldLabel: 'name',
            name:'name'
        }, {
            xtype: 'textfield',
            fieldLabel: 'name',
            name:'name'
        }, {
            xtype: 'textfield',
            fieldLabel: 'name',
            name:'name'
        }, {
            xtype: 'textfield',
            fieldLabel: 'name',
            name:'name'
        }, {
            xtype: 'datefield',
            fieldLabel: 'Entry Time',
            name:'entryTime',
            format: 'Y/m/d H:i:s'
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
