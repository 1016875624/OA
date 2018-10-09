Ext.define('Admin.model.department.DepartmentModel', {
    extend: 'Ext.data.Model',
    requires: [
		'Ext.data.proxy.Rest'
	],
	fields: [
	    {type: 'string',name: 'id'},
	    {type: 'string',name: 'name'}
	],
	proxy: {
        type: 'rest',
        url:'http://localhost:8080/department'
    }
});