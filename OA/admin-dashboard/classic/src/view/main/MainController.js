﻿Ext.define('Admin.view.main.MainController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.main',

    listen : {
        controller : {
            '#' : {
                unmatchedroute : 'onRouteChange'
            }
        }
    },

    routes: {
        ':node': 'onRouteChange'
    },

    lastView: null,

    setCurrentView: function(hashTag) {
        hashTag = (hashTag || '').toLowerCase();

        var me = this,
            refs = me.getReferences(),
            mainCard = refs.mainCardPanel,
            mainLayout = mainCard.getLayout(),
            navigationList = refs.navigationTreeList,
            store = navigationList.getStore(),
            node = store.findNode('routeId', hashTag) ||
                   store.findNode('viewType', hashTag),
            view = (node && node.get('viewType')) || 'page404',
            lastView = me.lastView,
            existingItem = mainCard.child('component[routeId=' + hashTag + ']'),
            newView;

        // Kill any previously routed window
        if (lastView && lastView.isWindow) {
            lastView.destroy();
        }

        lastView = mainLayout.getActiveItem();

        if (!existingItem) {
            newView = Ext.create({
                xtype: view,
                routeId: hashTag,  // for existingItem search later
                hideMode: 'offsets'
            });
        }

        if (!newView || !newView.isWindow) {
            // !newView means we have an existing view, but if the newView isWindow
            // we don't add it to the card layout.
            if (existingItem) {
                // We don't have a newView, so activate the existing view.
                if (existingItem !== lastView) {
                    mainLayout.setActiveItem(existingItem);
                }
                newView = existingItem;
            }
            else {
                // newView is set (did not exist already), so add it and make it the
                // activeItem.
                Ext.suspendLayouts();
                mainLayout.setActiveItem(mainCard.add(newView));
                Ext.resumeLayouts(true);
            }
        }

        navigationList.setSelection(node);

        if (newView.isFocusable(true)) {
            newView.focus();
        }

        me.lastView = newView;
    },

    onNavigationTreeSelectionChange: function (tree, node) {
        var to = node && (node.get('routeId') || node.get('viewType'));

        if (to) {
            this.redirectTo(to);
        }
    },

    onToggleNavigationSize: function () {
        var me = this,
            refs = me.getReferences(),
            navigationList = refs.navigationTreeList,
            wrapContainer = refs.mainContainerWrap,
            collapsing = !navigationList.getMicro(),
            new_width = collapsing ? 64 : 250;

        if (Ext.isIE9m || !Ext.os.is.Desktop) {
            Ext.suspendLayouts();

            refs.senchaLogo.setWidth(new_width);

            navigationList.setWidth(new_width);
            navigationList.setMicro(collapsing);

            Ext.resumeLayouts(); // do not flush the layout here...

            // No animation for IE9 or lower...
            wrapContainer.layout.animatePolicy = wrapContainer.layout.animate = null;
            wrapContainer.updateLayout();  // ... since this will flush them
        }
        else {
            if (!collapsing) {
                // If we are leaving micro mode (expanding), we do that first so that the
                // text of the items in the navlist will be revealed by the animation.
                navigationList.setMicro(false);
            }
            navigationList.canMeasure = false;

            // Start this layout first since it does not require a layout
            refs.senchaLogo.animate({dynamic: true, to: {width: new_width}});

            // Directly adjust the width config and then run the main wrap container layout
            // as the root layout (it and its chidren). This will cause the adjusted size to
            // be flushed to the element and animate to that new size.
            navigationList.width = new_width;
            wrapContainer.updateLayout({isRoot: true});
            navigationList.el.addCls('nav-tree-animating');

            // We need to switch to micro mode on the navlist *after* the animation (this
            // allows the "sweep" to leave the item text in place until it is no longer
            // visible.
            if (collapsing) {
                navigationList.on({
                    afterlayoutanimation: function () {
                        navigationList.setMicro(true);
                        navigationList.el.removeCls('nav-tree-animating');
                        navigationList.canMeasure = true;
                    },
                    single: true
                });
            }
        }
    },
	
    onMainViewRender:function() {
        if (!window.location.hash) {
            this.redirectTo("login");
        }
    },
    onRouteChange:function(id){
        this.setCurrentView(id);
        //登录校验:没有登录无法访问其他模块.
        /*
        var me = this;
	    if(loginUser!="null" || id=="login"){
			me.setCurrentView(id);
		}else{
			Ext.Msg.alert('警告', '非法登录系统!',function(){
				me.setCurrentView('login');
			});
		}*/
    }
	,logoutButton: function(){
		var me = this;
        Ext.Ajax.request({
            url: 'logout',
            method: 'post',
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	me.redirectTo('login', true);
	            	window.location.reload();
		        }else{
		        	Ext.Msg.alert('登出失败', json.msg);
		        }
            }
        });
    },
    
    //用户名改变
    /*nameTextChange: function(){
    	var changeText = this.lookupReference('nameChange');
    	var newName = changeText.get
    }*/
    
    //头像上传功能
	onClickGridUploadButton: function (btn) {
		btn.up('container').add(Ext.widget('uploadWindow')).show();
    },
	onClickUploadFormSumbitButton: function (btn) {
		var form=btn.up("window").down("form").getForm();
		console.log(Ext.ClassManager.getName(form));
		console.log(form.getValues());
		
		form.submit({
				type:'ajax',
				method:"post",
				url:"http://localhost:8080/uploadImageController/fileupload",
				waitMsg: '正在提交数据...',
				success: function(form,action){
					console.log(form);
					console.log(action);
					console.log(action.result.msg);
					
					json = action.result;
				    if (json.success) {
				      Ext.Msg.alert('成功', '上传成功.');
				      var headButton = Ext.getCmp('head_Button');
				      console.log(json.msg);
				      var path = '/images/employee/' + json.msg;
				      //var path = '"/images/employee/' + json.msg + '"';
				      headButton.setIcon(path);
				      console.log(path);
				    } else {
				      Ext.Msg.alert('失败', '上传失败.');
				    }
					
					
				},
				failure: function(resp){ 
					console.log(resp);
					console.log(resp.responseText);
					msg=Ext.decode(resp.responseText);
					if(msg.success){
						Ext.Msg.alert('成功','上传成功.'); 
					}
					else{
						Ext.Msg.alert('失败', '上传失败.'); 
					}
				} 
		});
		/*Ext.Ajax.request({		
		//被用来向服务器发起请求默认的url		
		url : "http://localhost:8080/uploadImageController/fileupload",		
		//请求时发送后台的参数,既可以是Json对象，也可以直接使用“name = value”形式的字符串		
		params : form.getValues(),		
		//请求时使用的默认的http方法		
		method : "post",		
		//请求成功时回调函数		
		success : function() {		
			Ext.Msg.alert('提示', "上传成功！");
			},		
		//请求失败时回调函数	
		failure : function() {		
			Ext.Msg.alert('提示', "上传失败！");	
			}	
		});*/
    },
    init:function(){
    	/*console.log("init");
    	var headButton = Ext.getCmp('head_Button');
	    var path = 'http://localhost:8080/images/employee/1.jpg';
	    headButton.setIcon(path);*/
    }
});
