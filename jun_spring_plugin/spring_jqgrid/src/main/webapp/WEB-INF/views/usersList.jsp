<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Users list</title>

<link rel="stylesheet" type="text/css" media="screen" href="${resourceUrl}/css/main.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${resourceUrl}/css/redmond/jquery-ui-1.8.16.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${resourceUrl}/css/ui.jqgrid.css" />

<script src="${resourceUrl}/js/jquery-1.6.2.min.js" type="text/javascript"></script>
<script src="${resourceUrl}/js/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="${resourceUrl}/js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script type="text/javascript">
    $(function() {
        $("#list").jqGrid({
            url : '${ctx}/users/getUsersList.do',
            datatype : 'json',
            mtype : 'GET',
            colNames : ['User Id', 'First Name', 'Last Name'],
            colModel : [{
                name : 'userId',
                index : 'userId',
                width : 60
            }, {
                name : 'firstName',
                index : 'firstName',
                width : 150
            }, {
                name : 'lastName',
                index : 'lastName',
                width : 150
            }],
            pager : '#pager',
            rowNum : 10,
            rowList : [10, 15, 20],
            sortname : 'firstName',
            sortorder : 'asc',
            viewrecords : true,
            gridview : true,
            altRows : true,
            caption : '${title}',
            jsonReader : {
                root : "rows",
                page : "pageNumber",
                total : "pagesAmount",
                records : "recordsTotalAmount",
                repeatitems: false
            }
        });
    });
</script>

</head>
<body>

    <div id="container">
        <table id="list">
            <tr>
                <td />
            </tr>
        </table>
        <div id="pager"></div>
    </div>
</body>
</html>
