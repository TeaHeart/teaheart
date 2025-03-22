const app = {
    list: {},
};

app.initToolbar = function () {
    const tt = $("#toolbar").toolbar({
        toolbar: [
            {
                id: "save",
                name: $.i18n("common.button.add.label"),
                className: "ico16 add_16",
                click: app.saveApp,
            },
            {
                id: "delete",
                name: $.i18n("common.button.delete.label"),
                className: "ico16 del_16",
                click: app.deleteApp,
            },
        ],
    });
};

app.initGrid = function () {
    app.list.grid = $("#table").ajaxgrid({
        gridType: "autoGrid",
        colModel: [
            {
                display: "id",
                name: "id",
                width: "smallest",
                sortable: false,
                align: "center",
                type: "checkbox",
            },
            {
                display: "key",
                name: "key",
                width: "smallest",
                sortable: false,
            },
            {
                display: "value",
                name: "value",
                width: "smallest",
                sortable: false,
            },
        ],
        click: function (data, r, c) {
        },
        dblclick: function (data, r, c) {
        },
        render: function rend(txt, rowData, rowIndex, colIndex, colObj) {
            return txt;
        },
        resizable: false,
        parentId: "layout_bottom",
        managerName: "myAppManager",
        managerMethod: "list",
    });
    $("#table").ajaxgridLoad({});
};

app.saveApp = function () {
    const dialog = $.dialog({
        id: "saveApp",
        url: _ctxPath + "/app.do?method=save" + CsrfGuard.getUrlSurffix(),
        width: 550,
        height: 350,
        targetWindow: getCtpTop(),
        transParams: {},
        title: $.i18n("common.button.add.label"),
        buttons: [
            {
                id: "ok",
                text: $.i18n("common.button.ok.label"),
                handler: function () {
                    dialog.disabledBtn("ok");
                    const params = dialog.getReturnValue();
                    if (!params) {
                        dialog.enabledBtn("ok");
                        return;
                    }
                    callBackendMethod("myAppManager", "save", params, {
                        success: function () {
                            $.infor("操作成功");
                            dialog.close();
                            $("#table").ajaxgridLoad({});
                        },
                        error: function (request) {
                            $.error(JSON.parse(request.response).message);
                            dialog.enabledBtn("ok");
                        },
                    });
                },
            },
            {
                id: "cancel",
                text: $.i18n("common.button.cancel.label"),
                handler: function () {
                    dialog.close();
                },
            },
        ],
    });
};

app.deleteApp = function () {
    const rows = app.list.grid.grid.getSelectRows();
    if (rows && rows.length > 0) {
        $.confirm({
            msg: $.i18n("app.del.confirm.tips"),
            ok_fn: function () {
                const ids = Array.from(rows).map(item => item.id)
                callBackendMethod("myAppManager", "delete", ids, {
                    success: function () {
                        $.infor("操作成功");
                        $("#table").ajaxgridLoad({});
                    },
                    error: function (request) {
                        $.error(JSON.parse(request.response).message);
                    },
                });
            },
        });
    }
};
