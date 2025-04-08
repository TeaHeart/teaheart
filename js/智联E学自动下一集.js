// ==UserScript==
// @name         智联E学自动下一集
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  智联E学自动下一集
// @author       github/TeaHeart
// @match        https://course.zhaopin.com/
// @icon         https://www.google.com/s2/favicons?sz=64&domain=zhaopin.com
// @grant        none
// ==/UserScript==

(async function () {
    "use strict";
    function sleep(ms) {
        return new Promise((r) => setTimeout(r, ms));
    }
    let video;
    while ((video = document.getElementsByTagName("video")[0]) === undefined) {
        await sleep(1000);
    }
    video.muted = true; // 静音
    video.addEventListener("ended", async function (e) {
        console.log("触发事件");
        let curr;
        while ((curr = document.getElementsByClassName('active')[0]) === undefined) {
            await sleep(1000);
        }
        let next = curr.nextSibling;
        if (next === null && curr.parentElement.nextSibling !== null) {
            next = curr.parentElement.nextSibling.children[1];
        }
        if (next !== null) {
            console.log("下一集", next);
            next.click();
        } else {
            alert("看完了");
        }
    });
    console.log(video, "绑定事件成功");
})();