// ==UserScript==
// @name         关闭弹窗
// @namespace    http://tampermonkey.net/
// @version      2024-08-11
// @description  关闭弹窗
// @author       github/TeaHeart
// @match        https://www.mitang.tv/*
// @match        https://www.agedm.org/*
// @match        https://vidhub1.cc/*
// @icon
// @grant        none
// ==/UserScript==

(async function() {
    'use strict';
    function sleep(ms) {
        return new Promise(r => setTimeout(r, ms));
    }
    await sleep(1000);
    document.querySelector(".hl-poptips-btn")?.querySelector("a")?.click();
    document.querySelector(".gnotice_close_today")?.click();
    document.querySelector(".popup-btn")?.click();
})();