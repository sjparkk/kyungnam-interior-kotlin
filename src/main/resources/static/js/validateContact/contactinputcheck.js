let contactForm = document.contactForm;

$("#contact-btn").click( e => {
    if(contactForm.name.value === "" || contactForm.phone.value === "" || contactForm.email.value === "" || contactForm.postcode.value === "" || contactForm.address.value === "" || contactForm.addressDetail.value === "" ||  contactForm.sizeNum.value === "" || contactForm.password.value === "" || contactForm.description.value === "") {
        if(contactForm.name.value === "") {
            alert('이름을 입력해주세요.');
            e.preventDefault();
            return contactForm.name.focus();
        } else if (contactForm.phone.value === "") {
            alert('번호를 입력해주세요.');
            e.preventDefault();
            return contactForm.phone.focus();
        } else if (contactForm.email.value === "") {
            alert('이메일을 입력해주세요.');
            e.preventDefault();
            return contactForm.email.focus();
        } else if (contactForm.postcode.value === "") {
            alert('우편번호를 입력해주세요.');
            e.preventDefault();
            return contactForm.postcode.focus();
        } else if (contactForm.address.value === "") {
            alert('주소를 입력해주세요.');
            e.preventDefault();
            return contactForm.address.focus();
        } else if (contactForm.addressDetail.value === "") {
            alert('상세 주소를 입력해주세요.');
            e.preventDefault();
            return contactForm.addressDetail.focus();
        } else if (contactForm.sizeNum.value === "") {
            alert('평수를 입력해주세요.');
            e.preventDefault();
            return contactForm.sizeNum.focus();
        } else if (contactForm.password.value === "") {
            alert('비밀번호를 입력해주세요.');
            e.preventDefault();
            return contactForm.password.focus();
        } else {
            alert('문의 내용을 입력해주세요.');
            e.preventDefault();
            return contactForm.description.focus();
        }
    }
    contactForm.submit();
})