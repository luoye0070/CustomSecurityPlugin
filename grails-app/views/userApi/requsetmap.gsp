<!DOCTYPE HTML>
<html>
<head>
    %{--<meta name="layout" content="_manage_bui"/>--}%
    <title>网站权限配置(开发人员使用)</title>
    <link href="${resource(dir:'js/bui/css',file:'dpl-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'bui-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'page-min.css')}" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="container">


    <div class="search-grid-container" style="margin-bottom: 70px;">
        <div id="grid"></div>
    </div>
</div>

<div id="content" class="hide">
    <form id="J_Form" class="form-horizontal" method="post">
        <input type="hidden" name="id" value="">
        <div class="row">
            <div class="control-group span15">
                <label class="control-label"><s>*</s>url：</label>
                <div class="controls">
                    <input name="url" type="text" data-rules="{required:true}" class="span8 span-width control-text bui-form-field">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group span15">
                <label class="control-label"><s>*</s>configAttribute：</label>
                <div class="controls">
                    <input name="configAttribute" type="text" data-rules="{required:true}" class="span8 span-width control-text bui-form-field">
                </div>
            </div>
        </div>
    </form>
</div>
%{--<script type="text/javascript" src="${resource(dir:'js/bui/common',file:'search-min.js')}"></script>--}%
<script type="text/javascript" src="${resource(dir:'js',file:'jquery-1.8.1.min.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/bui',file:'bui-min.js')}"></script>
<script type="text/javascript">
    BUI.use(['bui/grid','bui/data','bui/form'],function (Grid,Data,Form) {

        var columns = [
                    {title : 'url',dataIndex :'url'},
                    {title : 'configAttribute',dataIndex :'configAttribute'},
                    {title : '操作',renderer : function(){
                        return '<span class="grid-command btn-edit">编辑</span><span class="grid-command btn-del">删除</span>';
                    }}
                ],
                store = new Data.Store({
                    url : '${createLink(controller: "userApi",action:"findAllRequestmap")}',
                    autoLoad:true
                }),
                editing = new Grid.Plugins.DialogEditing({
                    contentId : 'content',
                    triggerCls : 'btn-edit',
                    editor : {
                        success : function(){
                            var edtor = this,
                                    form = edtor.get('form'),
                                    editType = editing.get('editType'),//add 或者 edit
                                    url;
                             if(editType == 'add'){ //可以根据编辑类型决定url
                                url = '${createLink(controller:"userApi",action:'saveRequestmap')}';
                             }else{
                                url = '${createLink(controller:"userApi",action:'updRequestmap')}';
                             }

                            //检验
                            form.valid();
                            if(form.isValid()){
                                form.ajaxSubmit({ //表单异步提交
                                    url : url,
                                    success : function(data){
                                        if(!data.success){ //返回的数据是 {success : false,'error' : 'xxxxx'}
                                            var field = data.field;
                                            if(field){
                                                form.getField(field).showErrors([data.error]); //也可以多个字段的错误信息 例如 errors : [{field : 'a',error: 'addd'},{field : 'a',error: 'addd'}]
                                            }else{
                                                BUI.Message.Alert(data.msg);
                                            }
                                        }else{
                                            if(editType=='add')
                                            form.getField('id').set('value',data.requestmap.id);
                                            edtor.accept();
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
                grid = new Grid.Grid({
                    render : '#grid',
                    columns : columns,
                    width : 700,
                    forceFit : true,
                    store : store,
                    plugins : [Grid.Plugins.CheckSelection,editing],
                    tbar:{
                        items : [{
                            btnCls : 'button button-small',
                            text : '<i class="icon-plus"></i>添加',
                            listeners : {
                                'click' : addFunction
                            }
                        },
                            {
                                btnCls : 'button button-small',
                                text : '<i class="icon-remove"></i>删除',
                                listeners : {
                                    'click' : delFunction
                                }
                            }]
                    }

                });
        grid.render();

        function addFunction(){
            var newData = {url :'',configAttribute:''};
            editing.add(newData);
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
                        url:'${createLink(controller: "userApi",action:"delRequestmap")}',
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

        function delFunction(){
            var selections = grid.getSelection(),ids=[];
            delItems(selections);
        }

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