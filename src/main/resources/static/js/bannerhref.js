$(document).on("click", "#banner-slider-tg", function(e){
    console.log(e.target.src.split("/")[4].split(".")[0]);
    if(e.target.src.split("/")[4].split(".")[0] === "banner1") { // 추후 banner 테이블과 work 테이블 연결하여 변경
        location.href = '/contact';
        // location.href = 'http://localhost:50000/work/detail?id=1';
    }
    if(e.target.src.split("/")[4].split(".")[0] === "banner2") {
        location.href = '/work/detail?id=6';
        //location.href = 'http://localhost:50000/work/detail?id=2';
    }
    if(e.target.src.split("/")[4].split(".")[0] === "banner3") {
        location.href = '/work/detail?id=1';
        //location.href = 'http://localhost:50000/work/detail?id=3';
    }
})