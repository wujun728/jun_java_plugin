"use strict";
layui.define([], function (exprots) {
    let okMock = {
        api: {
            login: "https://www.easy-mock.com/mock/5d5d0dd46cfcbd1b8627bf1d/ok-admin-v2.0/login",
            bsgrid: "http://rap2api.taobao.org/app/mock/233041/bsgrid",
            datatables: "http://rap2api.taobao.org/app/mock/233041/datatables",
            listUser: "http://rap2api.taobao.org/app/mock/233041/user/listUser",
            listRole: "http://rap2api.taobao.org/app/mock/233041/role/listRole",
            listArticle: "http://rap2api.taobao.org/app/mock/233041/article/listArticle",
            listMessage: "http://rap2api.taobao.org/app/mock/233041/message/listMessage",
            listProduct: "http://rap2api.taobao.org/app/mock/233041/product/listProduct",
            listDownload: "http://rap2api.taobao.org/app/mock/233041/download/listDownload",
            listLink: "http://rap2api.taobao.org/app/mock/233041/link/listLink",
            listTask: "http://rap2api.taobao.org/app/mock/233041/task/listTask",
            listImage: "http://rap2api.taobao.org/app/mock/233041/image/listImage",
            listBbs: "http://rap2api.taobao.org/app/mock/233041/bbs/listBbs",


            menu: {
                list: "https://easy-mock.com/mock/5d0ce725424f15399a6c2068/okadmin/menu/list"
            },
            user: {
                list: "http://rap2api.taobao.org/app/mock/233041/user/list",
            },
            role: {
                list: "http://rap2api.taobao.org/app/mock/233041/role/list"
            },
            permission: {
                list: "http://rap2api.taobao.org/app/mock/233041/permission/list",
            },
            article: {
                list: "http://rap2api.taobao.org/app/mock/233041/article/list"
            },
            task: {
                list: "http://rap2api.taobao.org/app/mock/233041/task/list"
            },
            link: {
                list: "http://rap2api.taobao.org/app/mock/233041/link/list"
            },
            product: {
                list: "http://rap2api.taobao.org/app/mock/233041/product/list"
            },
            log: {
                list: "https://easy-mock.com/mock/5d0ce725424f15399a6c2068/okadmin/log/list"
            },
            message: {
                list: "http://rap2api.taobao.org/app/mock/233041/message/list"
            },
            download: {
                list: "http://rap2api.taobao.org/app/mock/233041/download/list"
            },
            bbs: {
                list: "http://rap2api.taobao.org/app/mock/233041/bbs/list"
            }
        }
    };
    exprots("okMock", okMock);
});
