/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function nextBookmark() {
    var current_bookmarked = $("input[type='button']")[2].value.trim();
    var allBookMarked = $("a.bookmarked");
    for (var i = 1; i < allBookMarked.length; i ++) {
        if (allBookMarked[i - 1].innerText.localeCompare(current_bookmarked) === 0) {
            location.hash = "#" + allBookMarked[i].innerText;
            $("input[type='button']")[2].value = pad(allBookMarked[i].innerText, 20);
            return;
        }
    }
}

function pad(str, len){
    str = str + new Array(len + 1).join(' ');
    return str;
}

function topBookmark(topBookmark) {
    $("input[type='button']")[2].value = pad(topBookmark, 20) ;
    location.hash = "#" + topBookmark;
}

function selectTopic() {
    if (confirm("Are you sure you saved the labels and select other topic?") == true) {
        window.open("view_topics.jsp","_self")
    }
}

$( document ).ready(function() {
    
    $("div.container-fluid").css("margin-top", $("div.navbar").height());
});