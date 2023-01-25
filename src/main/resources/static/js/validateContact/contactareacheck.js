let areaTarget = "area1";
$("#area1, #area2").click(e => {
    if(e.target.id === "area1" && e.target.id !== areaTarget) {
        areaTarget = e.target.id;
        $("#area2").prop("checked", false);

        if($("#sizeNum").val() !== undefined && !isNaN($("#sizeNum").val())) {
            $("#sizeNum").val(Math.round($("#sizeNum").val() * 0.3025));
        }
    } else {
        areaTarget = e.target.id;
        $("#area1").prop("checked", false);

        if($("#sizeNum").val() !== undefined && !isNaN($("#sizeNum").val())) {
            $("#sizeNum").val(Math.round($("#sizeNum").val() * 3.3058));
        }
    }
})