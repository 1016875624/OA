Ext.define('Admin.view.common.DepartmentCombobox', {
    extend: 'Ext.form.field.ComboBox',
    xtype: 'departmentcombobox',

    /*requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],*/
    displayField:'name',
    valueField:'id',
    editable:false,
    //controller: 'searchresults',
   // viewModel: {type: 'orderViewModel'},
    layout: 'fit',
    emptyText: '请选择一个部门',
    width: 135,
    listeners:{
        //change:'tbarSelectChange',
        /*focus:function (box) {
            console.log("123");
            console.log(Ext.ClassManager.getName(Ext.data.StoreManager.lookup('departmentLoadStroe')));

            //console.log(departmentList);
            //console.log(Ext.getCmp("departmentNameStore"));
            //box.bindStore(Ext.data.StoreManager.lookup('departmentLoadStroe'));
        },*/
        afterrender:function (box) {
            box.bindStore(Ext.data.StoreManager.lookup('departmentLoadStroe'));
        }
    },
});
