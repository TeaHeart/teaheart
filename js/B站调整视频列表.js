// ==UserScript==
// @name         B站调整视频列表
// @namespace    http://tampermonkey.net/
// @version      2024-07-08
// @description  B站调整视频列表
// @author       github/TeaHeart
// @match        https://www.bilibili.com/video/*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=bilibili.com
// @grant        none
// ==/UserScript==

(async function() {
    'use strict';
    console.log('B站调整视频列表...');
    function sleep(ms) {
        return new Promise(r => setTimeout(r, ms));
    }
    await sleep(2000);
    let list = document.querySelector('.video-sections-content-list');
    list.style.height = '100%';
    list.style.maxHeight = '500px';
    document.querySelectorAll('.video-episode-card__info-title')
            .forEach(e => e.style.width = '100%')
})();