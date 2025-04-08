// ==UserScript==
// @name         师德师风自动评分
// @namespace    http://tampermonkey.net/
// @version      2024-06-26
// @description  师德师风自动评分
// @author       github/TeaHeart
// @match        https://jxzlpj.wit.edu.cn/app/variantForm/supervision/renderStu*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=wit.edu.cn
// @grant        none
// ==/UserScript==

(async function () {
    'use strict';
    function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
    console.log('师德师风自动评分 运行中...');
    // 第一个符合, 其他的很符合
    let choose = [1, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    await sleep(1000);
    // 选择评分
    document.querySelectorAll(".el-select-dropdown__item").forEach((e, i) => {
        // 第x个选项
        let j = Math.floor(i / 5);
        // 选择的什么
        let k = i % 5;
        if (choose[j] === k) {
            e.click();
        }
    });
    // 提交
    await sleep(500);
    document.querySelectorAll('.van-button__content')[0].click();
    // 确定
    await sleep(500);
    document.querySelectorAll('.van-button__content')[2].click();
    // 返回
    await sleep(1000);
    document.querySelectorAll('.van-button__content')[0].click();
})();
