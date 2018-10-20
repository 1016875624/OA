

Ext.define('Admin.view.membership.MemberShipController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.membershipcontroller',
    onTooltip: function (component, tooltip, node, element, event) {
        var record = node.data,
            name = record.get('name'),
            //title = record.get('title'),
            title = record.get('position'),
            id=record.get("id");
            departmentName=record.get("departmentName");
            //html = '<span style="font-weight: bold">' + name + '</span><br>' + title;
            html = '<span style="font-weight: bold">'+id+'<br>'+departmentName+'<br>' + name + '</span><br>' + title+'<br>';
        tooltip.setHtml(html);
    },
    init:function () {

        var tree=this.lookupReference("membershipChart");
        var store=tree.getStore();
        Ext.Ajax.request({
            url: 'http://localhost:8080/employee/user9'
        }).then(function(response, opts) {
                var obj = Ext.decode(response.responseText);
                console.dir(obj);
                store.setRoot(obj.root);
            },
            function(response, opts) {
                console.log('server-side failure with status code ' + response.status);
            });
        // store.setListeners({
        //     // rootchange:function (newRoot, oldRoot, eOpts) {
        //     //     tree.
        //     // }
        // });
        /*var store =Ext.data.StoreManager.lookup('membershipstore');
        //var local =Ext.data.StoreManager.lookup('membershiplocalstore');
        var tree=this.lookupReference("membershipChart");
        store.load();
        console.log(store);
        console.log(store.getData());
        console.log(store.getRoot());
        console.log(123)
        console.log(store.getAt(0));*/
    },
    beforeRender:function () {
        // var tree=this.lookupReference("membershipChart");
        // var store=tree.getStore();
        // Ext.Ajax.request({
        //     url: 'http://localhost:8080/employee/user9'
        // }).then(function(response, opts) {
        //         var obj = Ext.decode(response.responseText);
        //         console.dir(obj);
        //         store.setRoot(obj.root);
        //     },
        //     function(response, opts) {
        //         console.log('server-side failure with status code ' + response.status);
        //     });
    },
    beforerender:function () {
        // var tree=this.lookupReference("membershipChart");
        // var store=tree.getStore();
        // Ext.Ajax.request({
        //     url: 'http://localhost:8080/employee/user9'
        // }).then(function(response, opts) {
        //         var obj = Ext.decode(response.responseText);
        //         console.dir(obj);
        //         store.setRoot(obj.root);
        //     },
        //     function(response, opts) {
        //         console.log('server-side failure with status code ' + response.status);
        //     });
    }

});