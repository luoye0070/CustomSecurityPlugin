
<!DOCTYPE HTML>
<html>
<head>
    %{--<meta name="layout" content="_manage_bui"/>--}%
    <title> 用户</title>
    <link href="${resource(dir:'js/bui/css',file:'dpl-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'bui-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'page-min.css')}" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="container">
    <div class="row">
        <form id="searchForm" class="form-horizontal span24">
            <div class="row">
                <div class="control-group span8">
                    <label class="control-label">${message(code: 'member.username.label', default: 'username')}</label>
                    <div class="controls">
                        <input type="text" class="control-text" name="username"/>
                    </div>
                </div>
                <div class="control-group span8">
                   <button  type="button" id="btnSearch" class="button button-primary">搜索</button>
                </div>
            </div>
        </form>
    </div>
    <div class="search-grid-container" style="margin-bottom: 70px;">
        <div id="grid"></div>
    </div>

</div>
<div id="content" class="hide">
    <form id="J_Form" class="form-horizontal" action="${createLink(controller: 'userApi',action:'updMember')}">
        <input type="hidden" name="id" value="">
        <div class="row">
            <div class="control-group span8">
                <label class="control-label"><s>*</s>${message(code: 'member.username.label', default: 'username')}</label>
                <div class="controls">
                    <input name="username" type="text" data-rules="{required:true}" class="input-normal control-text"/>
                </div>
            </div>
            <div class="control-group span8">
                <label class="control-label">${message(code: 'member.password.label', default: 'password')}</label>
                <div class="controls">
                    <input name="password" type="password" class="input-normal control-text" value="111111" id="password"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group">
                <label class="control-label">${message(code: 'member.realName.label', default: 'realName')}</label>
                <div class="controls">
                    <input name="realName" type="text" class="input-normal control-text" style="width: 300px;"/>
                 </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group span8">
                <label class="control-label ">${message(code: 'member.enabled.label', default: 'enabled')}</label>
                <div class="controls">
                    <label class="checkbox" for=""><input type="checkbox" name="enabled" checked=""/></label>
                </div>
            </div>

            <div class="control-group span8">
                <label class="control-label">${message(code: 'member.accountLocked.label', default: 'accountLocked')}</label>
                <div class="controls">
                    <label class="checkbox" for=""><input type="checkbox" name="accountLocked" checked=""/></label>
                </div>
            </div>


        </div>
        <div class="row">
            <div class="control-group span8">
                <label class="control-label">${message(code: 'member.accountExpired.label', default: 'accountExpired')}</label>
                <div class="controls">
                    <label class="checkbox" for=""><input type="checkbox" name="accountExpired" checked=""/></label>
                </div>
            </div>

            <div class="control-group span8">
                <label class="control-label">${message(code: 'member.passwordExpired.label', default: 'passwordExpired')}</label>
                <div class="controls">
                    <label class="checkbox" for=""><input type="checkbox" name="passwordExpired" checked=""/></label>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="control-group">
                <label class="control-label">角色：</label>
                <div class="controls bui-form-field-checklist" data-items='${roles}'>
                    <input name="roles" type="hidden" value="1">
                </div>
            </div>
        </div>
    </form>
</div>
%{--<script type="text/javascript" src="${resource(dir:'js/bui/common',file:'search-min.js')}"></script>--}%
<script type="text/javascript" src="${resource(dir:'js',file:'jquery-1.8.1.min.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/bui',file:'bui-min.js')}"></script>
<script type="text/javascript">
    BUI.use(['common/search','bui/grid'],function (Search,Grid) {

        var enumObj = {'true':'是','false':'否','on':'是','0':'否','off':'否'}, enumObj1=${roles},editing = new BUI.Grid.Plugins.DialogEditing({
                    contentId : 'content', //设置隐藏的Dialog内容
                    triggerCls : 'btn-edit',
                    editor : {
                        success : function(){
                            var edtor = this,
                                    form = edtor.get('form'),
                                    editType = editing.get('editType'),//add 或者 edit
                                    url;
                            if(editType == 'add'){ //可以根据编辑类型决定url
                                url = '${createLink(controller:"userApi",action:'saveMember')}';
                            }else{
                                url = '${createLink(controller:"userApi",action:'updMember')}';
                            }

                            //检验
                            form.valid();
                            if(form.isValid()){
                                form.ajaxSubmit({ //表单异步提交
                                    url : url,
                                    success : function(data){
                                        if(!data.success){ //返回的数据是 {success : false,'error' : 'xxxxx'}
                                            $("#password").val('');
                                            var field = data.field;
                                            if(field){
                                                form.getField(field).showErrors([data.error]); //也可以多个字段的错误信息 例如 errors : [{field : 'a',error: 'addd'},{field : 'a',error: 'addd'}]
                                            }else{
                                                BUI.Message.Alert(data.msg);
                                            }
                                        }else{
                                            if(editType=='add')
                                                form.getField('id').set('value',data.member.id);
                                            edtor.accept();
                                            search.load();
                                        }

                                    },
                                    error : function(){
                                        //do something
                                    }
                                });
                            }
                        }
                    }
                }),
                columns = [
                    {title:"${message(code: 'member.username.label', default: 'username')}",dataIndex:'username',width:200},
                    {title:"${message(code: 'member.realName.label', default: 'realName')}",dataIndex:'realName',width:200},
                    {title:"${message(code: 'member.enabled.label', default: 'enabled')}",dataIndex:'enabled',width:100,renderer : function(value,obj){if(value=='on')return "是";else return "否"; }},
                    {title:"${message(code: 'member.accountLocked.label', default: 'accountLocked')}",dataIndex:'accountLocked',width:100,renderer : function(value,obj){if(value=='on')return "是";else return "否"; }},
                    {title:"${message(code: 'member.accountExpired.label', default: 'accountExpired')}",dataIndex:'accountExpired',width:100,renderer : function(value,obj){if(value=='on')return "是";else return "否"; }},
                    {title:"${message(code: 'member.passwordExpired.label', default: 'passwordExpired')}",dataIndex:'passwordExpired',width:100,renderer : function(value,obj){if(value=='on')return "是";else return "否"; }},
                    {title:'角色',dataIndex:'roles',renderer : Grid.Format.multipleItemsRenderer(enumObj1)},
                    {title:'操作',dataIndex:'',width:200,renderer : function(value,obj){
                        var
                             editStr1 = '<span class="grid-command btn-edit" title="编辑用户信息">编辑</span>',
                                delStr = '<span class="grid-command btn-del" title="删除用户信息">删除</span>';//页面操作不需要使用Search.createLink
                        return  editStr1 + delStr;
                    }}
                ],
                store = Search.createStore('${createLink(controller: "userApi",action:"findMember")}', { autoLoad:true }),
                gridCfg = Search.createGridCfg(columns,{
                    tbar : {
                        items : [
                            {text : '<i class="icon-plus"></i>新建',btnCls : 'button button-small',handler:addFunction}
                        ]
                    },
                    plugins : [editing,BUI.Grid.Plugins.AutoFit] // 插件形式引入多选表格
                });

        var  search = new Search({
                    store : store,
                    gridCfg : gridCfg
                }),
                grid = search.get('grid');

        function addFunction(){
            var newData = {isNew : true,enabled:"on"}; //标志是新增加的记录
            editing.add(newData); //添加记录后，直接编辑
        }

        //删除操作
        function delFunction(){
            var selections = grid.getSelection();
            delItems(selections);
        }

        function delItems(items){
            var ids = [];
            BUI.each(items,function(item){
                ids.push(item.id);
            });

            if(ids.length){
                BUI.Message.Confirm('确认要删除选中的记录么？',function(){
                    $.ajax({
                        type:'post',
                        dataType:'json',
                        url:'${createLink(controller: "userApi",action:"delMember")}',
                        data:{ids:ids.join(',')},
                        success:function(data){
                            if(data.success){
                                store.remove(items);
                            }else{
                                BUI.Message.Alert(data.msg);
                            }
                        }
                    });
                },'question');
            }
        }

        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
            var sender = $(ev.domTarget); //点击的Dom
            if(sender.hasClass('btn-del')){
                var record = ev.record;
                delItems([record]);
            }
        });
    });
</script>

</body>
</html>