$(document).ready(function () {
    var currentPosition = parseInt($("#aside").css("top"));
    $(window).scroll(function () {
        var position = $(window).scrollTop();
        $("#aside").stop().animate({
            "top": Number(currentPosition) + "px"
        }, 1);
    });
});