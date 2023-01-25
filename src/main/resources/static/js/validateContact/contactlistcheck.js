//비밀번호 확인창에 있는 확인 버튼 클릭시
$("#password").click(e => {
    let pw = $("#password").val();
    if(!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/.test(pw)){
        document.getElementById('check-msg').innerHTML='숫자와 영문자 조합으로 8~16자리를 사용해야 합니다.';
        document.getElementById('check-msg').style.color='red';
        e.preventDefault();
    }
})

//문의글 클릭시
$("#contact_list tr").click(e => {
    let passForm = document.pwdForm;
    $('input[name=user-id]').attr('value',e.currentTarget.firstElementChild.textContent);
    if(!$("#exampleModalCenter")[0].className.includes('show')) {
        document.getElementById('password').autofocus;
    }
})

let searchForm = document.searchForm;

$("#search-btn").click( e => {
    if ($("#keyword").val() === "" && $("#type option:selected").val() === "검색 내용 선택") {
        alert("검색 내용을 선택해주세요.");
    } else {
        searchForm.submit();
    }
})