// ==UserScript==
// @name         蓝奏云自动批量下载
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  蓝奏云自动批量下载
// @author       github/TeaHeart
// @match        *://*.lanzou*.com/*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=lanzoub.com
// @grant        none
// ==/UserScript==

(async function () {
    "use strict";
    function sleep(ms) {
        return new Promise(r => setTimeout(r, ms));
    }
    function close() {
        window.opener = null;
        window.open("about:blank", "_top").close();
    }
    let parent = $(".user-title")[0] || $("#sp_name")[0];
    if (parent !== undefined && $("#downloadAll")[0] === undefined) {
        $(`<button id="downloadAll">下载所有</button>`)
            .click(async () => {
                let arr = $("#infos a");
                for (let i = 0; i < arr.length; i++) {
                    arr[i].click();
                    await sleep(1000);
                }
            })
            .appendTo(parent);
    }
    let a;
    while ((a = $("#tourl a")[0]) === undefined) {
        await sleep(1000);
    }
    a.click();
    await sleep(1000);
    close();
})();