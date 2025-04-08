// ==UserScript==
// @name         导航自动跳转
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  导航自动跳转
// @author       github/TeaHeart
// @match        *://*.miaoaaa.com/sites/*
// @match        *://*.rrnav.com/sites/*
// @match        *://*.acgbox.link/h/*
// @match        *://*.miaodh.net/sites/*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=
// @grant        none
// ==/UserScript==

(function () {
    'use strict';
    location.href = atob(decodeURIComponent($(".site-go-url a")[0].href.split("=")[1]));
})();