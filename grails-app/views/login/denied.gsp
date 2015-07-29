<head>

<title>没有授权</title>
    <link href="${resource(dir:'js/bui/css',file:'dpl-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'bui-min.css')}" rel="stylesheet" type="text/css" />
    <link href="${resource(dir:'js/bui/css',file:'page-min.css')}" rel="stylesheet" type="text/css" />
</head>

<body>
<hr>
<div class="row">
    <div class="span10 offset6">
        <div class="tips tips-large tips-warning">
            <span class="x-icon x-icon-error">×</span>
            <div class="tips-content">
                <h2>没有授权</h2>
                <p class="auxiliary-text">
                    你可以继续操作以下内容：
                </p>
                <p>
                    <a class="direct-lnk" title="返回用户管理" href="${createLink(controller:'manage')}">返回后台管理</a>
                    <a class="direct-lnk" title="返回网站首页" href="./">返回网站首页</a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
