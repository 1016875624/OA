Ext.define('Admin.view.main.MainController', {
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
    },
    logoutButton: function(){
		var me = this;
        Ext.Ajax.request({
            url: 'http://localhost:8080/logout',
            method: 'post',
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	me.redirectTo('http://localhost:8080/logout', true);
	            	window.location.href="http://localhost:8080/#login";
		        }else{
		        	Ext.Msg.alert('登出失败', json.msg);
		        }
            }
        });
    },
    
    //打开上传窗口
	onClickGridUpload: function () {
		Ext.widget('uploadWindow').show();
    },
    
    // 预览图片
    previewImage: function () {
    	/*var furl = Ext.getCmp('upload-form').form.findField('photo').getValue();  
	    var type = furl.substring(furl.length - 3).toLowerCase();  
	    if (furl == "" || furl == null) {  
	        return;  
	    }  
	    if (type != 'jpg' && type != 'bmp' && type != 'gif' && type != 'png') {  
	        alert('仅支持jpg、bmp、gif、png格式的图片');  
	        return;  
	    },*/
    	Ext.getCmp('upload').on('change',function(field, newValue, oldValue) {
            var file = field.fileInputEl.dom.files.item(0);
            var fileReader = new FileReader('file://'+newValue);
            fileReader.readAsDataURL(file);
            fileReader.onload=function(e){
                Ext.getCmp('imageId').setSrc(e.target.result);
            }
        });
    },
    /*
    // 预览图片
    previewImage: function () {
        console.log('读取照片');
        var path = '/images/employee/' + "1.jpg";
        var me = this;
        Ext.Ajax.request({
            url: 'http://localhost:8080/userinfo',
            method: 'get',
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            path=json.picture;
            }
        });
        Ext.getCmp('File').on('change', function (field, newValue, oldValue) {//上传图片的控件对象,刚刚选择的图片仿真路径，上次选择的图片仿真路径
            console.log(newValue);
            var show = Ext.getCmp('browseImage');
            console.log(show);
            //获取选择文件的路径信息, 将路径绑定到显示图片的box内加载
            var obj = Ext.getCmp('File').inputEl.dom.files;
            console.log(Ext.getCmp('File'));
            console.log(obj);
            var temp=Ext.clone(obj[0]);
            console.log(temp);
            console.log(obj[0]);
            var imgurl = window.URL.createObjectURL(temp);
            console.log(imgurl)
            Ext.getCmp('browseImage').getEl().dom.src = imgurl;
        }, this);
        
    },*/
    
    //头像上传功能
	onClickUploadFormSumbitButton: function (btn) {
		var form=btn.up("window").down("form").getForm();
		//Ext.getCmp('upload-form').submit({
		form.submit({
				//type:'ajax',
				//method:"post",
				url:"http://localhost:8080/uploadImage/fileupload",
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
    },
    init:function(){
    	/*console.log("init");
    	var headButton = Ext.getCmp('head_Button');
	    var path = 'http://localhost:8080/images/employee/1.jpg';
	    headButton.setIcon(path);*/
    }
    /*getWebSocket:function(){
        websocket = new WebSocket(encodeURI('ws://localhost:8080/Chat/message'));
        return websocket;
    }*/
});
