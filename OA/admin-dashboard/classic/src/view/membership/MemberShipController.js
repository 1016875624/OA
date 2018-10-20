

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
        var membershipTree=this.lookupReference("membershipChart");
        var store=Ext.data.StoreManager.lookup('membershipstore');
        store.setRoot(window.datas);
        membershipTree.setStore(store);
        membershipTree.up("container").updateLayout();
        console.log(store.getRoot());
        // var membershipTree=this.lookupReference("membershipTree");
        // var tree=this.lookupReference("membershipChart");
        // var store =Ext.data.StoreManager.lookup('membershipstore');
        // Ext.Ajax.request({
        //     url: 'http://localhost:8080/employee/user9'
        // }).then(function(response, opts) {
        //         var obj = Ext.decode(response.responseText);
        //         console.dir(obj);
        //         store.setRoot(obj.root);
        //         tree.setStore(store);
        //     },
        //     function(response, opts) {
        //         console.log('server-side failure with status code ' + response.status);
        //     });

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


    ,add111:function () {
        var panel=Ext.create("Ext.window.Window",{
            layout: 'fit',

            title: 'Sencha Org Chart',
            items: {
                xtype: 'membershipChart',
                reference:"membershipChart",
                interactions: {
                    type: 'panzoom',
                    zoom: {
                        extent: [0.5, 2],
                        doubleTap: false
                    }
                },

                tooltip: {
                    renderer: 'onTooltip'
                },

                nodeSize: [200, 100],

                imagePath: 'http://localhost:8080/images/employee/1.png',

                //store:store,
            }
        });
        membershipTree.add(panel);
        panel.show();
    }

});