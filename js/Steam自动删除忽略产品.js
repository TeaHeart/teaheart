// ==UserScript==
// @name         Steam自动删除忽略产品
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  Steam自动删除忽略产品
// @author       github/TeaHeart
// @match        *://store.steampowered.com/account/notinterested/
// @icon         https://www.google.com/s2/favicons?sz=64&domain=steampowered.com
// @grant        none
// ==/UserScript==

(async function() {
    'use strict';
    function sleep(ms) {
        return new Promise((r) => setTimeout(r, ms));
    }
    let arr = document.getElementsByClassName("ignored_apps")[0].getElementsByTagName("span");
    for (let i = 0; i < arr.length; i += 2) {
        arr[i].click();
        await sleep(1000);
    }
    await sleep(1000);
    location.reload();
})();