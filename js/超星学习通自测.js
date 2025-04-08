// ==UserScript==
// @name         超星学习通自测
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  超星学习通自测
// @author       github/TeaHeart
// @match        *://mooc1.chaoxing.com/mooc-ans/mooc2/work/view*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=
// @grant        none
// ==/UserScript==

(function () {
  "use strict";
  document.querySelector(".mark_title").innerHTML +=
    "<button id='test-self'>自测</button>";

  document.getElementById("test-self").onclick = function () {
    for (const item of document.querySelectorAll(".questionLi")) {
      let all = item.innerText;
      let ans = item.querySelector(".mark_answer").innerText;
      let split = all.length - ans.length;
      if (!confirm(all.substring(0, split)) || !confirm(all.substring(split))) {
        break;
      }
    }
  };
})();
