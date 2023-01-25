$("#phone").blur( e => {
    if($("#phone").val() != "") {
        let phone = $("#phone").val();
        if(isNaN(phone)){
            document.getElementById('check-phone').innerHTML='번호만 입력해주세요.';
            document.getElementById('check-phone').style.color='red';
            e.preventDefault();
        } else {
            document.getElementById('check-phone').innerText ='';
        }
    }
})
$("#email").blur( e => {
    if($("#email").val() != "") {
        let re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        let email = $("#email").val();
        if(!re.test(email)){
            document.getElementById('check-email').innerHTML="입력하신 '" + email + "'은 적합하지 못한 이메일 형식입니다.";
            document.getElementById('check-email').style.color='red';
            e.preventDefault();
        } else {
            document.getElementById('check-email').innerText ='';
        }
    }
})
$("#password").blur( e => {
    if($("#password").val() != "") {
        let pw = $("#password").val();
        if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/.test(pw)) {
            document.getElementById('check-pwd').innerHTML = '숫자와 영문자 조합으로 8~16자리를 사용해야 합니다.';
            document.getElementById('check-pwd').style.color = 'red';
            e.preventDefault();
        } else {
            document.getElementById('check-pwd').innerText = '';
        }
    }
})