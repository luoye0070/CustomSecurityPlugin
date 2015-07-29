
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    %{--<meta name="layout" content="_manage_bui"/>--}%
  <title>更新密码</title>
    <link href="${resource(dir:'js/bui/css',file:'dpl-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'bui-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'page-min.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="container">
</div>

<div id="content" class="container">
    <form id="J_Form" class="form-horizontal" method="post" action="${createLink(controller: 'userApi',action:'changePass')}">
        <div class="row hide" id="stip">
            <div class="tips tips-small tips-info">
                <span class="x-icon x-icon-small x-icon-info"><i class="icon icon-white icon-info"></i></span>
                <div class="tips-content" id='smsg'></div>
            </div>
        </div>
        <div class="row hide" id="ftip">
            <div class="tips tips-small tips-warning">
                <span class="x-icon x-icon-small x-icon-error"><i class="icon icon-white icon-bell"></i></span>
                <div class="tips-content" id='fmsg'></div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="control-group">
                <label class="control-label"><s>*</s>旧密码</label>
                <div class="controls">
                    <input name="oldPass" id="oldPass" type="password" data-rules="{required:true}"  style="height:30px;width:200px">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group">
                <label class="control-label"><s>*</s>新密码</label>
                <div class="controls">
                    <input name="newPass" id="newPass" type="password" data-rules="{required:true}"  style="height:30px;width:200px">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group">
                <label class="control-label"><s>*</s>再次输入新密码</label>
                <div class="controls">
                    <input name="newPass1" type="password" id="newPass1"   data-rules="{equalTo:'#newPass'}"   style="height:30px;width:200px">
                </div>
            </div>
        </div>

        %{--<div class="row form-actions actions-bar">--}%
            %{--<div class="span13 offset3 ">--}%
                %{--<button type="submit" class="button button-primary">--}%
                    %{--${message(code: 'default.button.search.label', default: 'Search')}--}%
                %{--</button>--}%
            %{--</div>--}%
        %{--</div>--}%
    </form>
</div>
%{--<script type="text/javascript" src="${resource(dir:'js/bui/common',file:'search-min.js')}"></script>--}%
<script type="text/javascript" src="${resource(dir:'js',file:'jquery-1.8.1.min.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/bui',file:'bui-min.js')}"></script>
<script type="text/javascript">
    BUI.use(['bui/overlay','bui/form'],function (Overlay,Form) {
        var dialog = new Overlay.Dialog({
            width:500,
            height:200,
            mask:false,
            buttons:[
                {
                    text:'提交',
                    elCls : 'button button-primary',
                    handler : function(){
                        form.valid();
                        if(form.isValid())
                            form.ajaxSubmit({
                                dataType:'json',
                                success:function(data){
                                    $("#newPass").val('');
                                    $("#oldPass").val('');
                                    $("#newPass1").val('');
                                    if(data.success){
                                        $("#stip").show();
                                        $("#smsg").html(data.msg);
                                        $("#ftip").hide();
                                    }else{
                                        $("#ftip").show();
                                        $("#fmsg").html(data.msg);
                                        $("#stip").hide();
                                    }

                                }
                            })
                    }
                }
            ],
            contentId:'content',
            closeable:false
        }),form=new Form.Form({
            srcNode : '#J_Form'
        });
        dialog.show();
        dialog.move(50,50);
        form.render();
    });
</script>
</body>
</html>