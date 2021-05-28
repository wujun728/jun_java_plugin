<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>${vo.siteName!}</title>
  </head>
  <body style="background-color:#fafbfc;">
    
    <div class="container-fluid" style="width:80%;">
        <div class="row" style="margin:50px 0;">
            <div class="col-md-4">
                <a href="${vo.siteUrl!}" target="_blank"><img src="http://www.roncoo.com/images/logo.png" alt="龙果学院"></a>
            </div>
            <div class="col-md-8">
                <div class="row">
                    <div class="col-md-12"  style="line-height: 25px;font-size: 24px;">
                    ${vo.siteName!}
                    </div>
                    <div class="col-md-12" style="line-height: 25px;margin: 5px 0;">
                    ${vo.siteDesc!}
                    </div>
                    <div class="col-md-12">
                    <a href="${vo.siteUrl!}" target="_blank">${vo.siteUrl!}</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <#if vo.list??>
            <#list vo.list as bean>
            <div class="col-md-3">
                <div class="row" style="border:1px #ddd solid;border-radius:5px;margin-right:5px;">
                    <div class="col-md-12" style="line-height: 25px;font-size: 24px;    margin: 10px 0;color:#0366d6;">
                    ${bean.urlName}
                    </div>
                    <div class="col-md-12" style="height:100px;line-height: 25px;margin: 5px 0;">
                    ${bean.urlDesc}
                    </div>
                    <div class="col-md-12" style="margin: 5px 0;">
                        <div class="row">
                            <div class="col-md-4" style="text-align: right;">
                            </div>
                            <div class="col-md-4" style="text-align: right;">
                                <a href="${bean.inNet}" target="_blank">内网</a>
                            </div>
                            <div class="col-md-4" style="text-align: left;">
                                <a href="${bean.outNet}" target="_blank">公网</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </#list>
            </#if>
        </div>
    </div>
  </body>
</html>