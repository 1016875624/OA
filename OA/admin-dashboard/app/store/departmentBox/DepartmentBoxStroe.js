Ext.define('Admin.store.departmentBox.DepartmentBoxStroe', {
    extend: 'Ext.data.Store',
    alias: 'store.departmentBoxStroe',
	storeId:'departmentBoxStroe',
	fields: ["id", "name"],
   	proxy: {
        type: 'ajax',
        url:'http://localhost:8080/department/simpleget',
	    reader:{
	    	type:'json'
	    },
    },
    autoLoad:true
});
