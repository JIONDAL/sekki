/*로그인=============================================================================*/
function loginCheck() {
	let id = document.getElementById('id');
	let pw = document.getElementById('pw');

	if (id.value == "") {
		alert('아이디는 필수 항목입니다.');
	} else if (pw.value == "") {
		alert('비밀번호는 필수 항목입니다.');
	} else {
		var f = document.getElementById('f');
		f.submit();
	}
}

function logout() {
	result = confirm('로그아웃 하시겠습니까?');
	if (result == true) {
		location.href = 'logout';
	}
}

/*약관동의&본인인증=============================================================================*/

document.addEventListener("DOMContentLoaded", function() {
	var allAgreeCheckbox = document.getElementById("allAgree");
	var otherCheckboxes = document.querySelectorAll(".agreeConditionTable tbody input[type='checkbox']");

	allAgreeCheckbox.addEventListener("change", function() {
		var isChecked = allAgreeCheckbox.checked;

		otherCheckboxes.forEach(function(checkbox) {
			checkbox.checked = isChecked;
		});
	});

	otherCheckboxes.forEach(function(checkbox) {
		checkbox.addEventListener("change", function() {
			if (this.checked && areAllCheckboxesChecked(otherCheckboxes)) {
				allAgreeCheckbox.checked = true;
			} else {
				allAgreeCheckbox.checked = false;
			}
		});
	});

	function areAllCheckboxesChecked(checkboxes) {
		for (var i = 0; i < checkboxes.length; i++) {
			if (!checkboxes[i].checked) {
				return false;
			}
		}
		return true;
	}
});

document.getElementById('authenticationNum').addEventListener('input', function(event) {
	var input = event.target;
	var inputValue = input.value;
	var sanitizedValue = inputValue.replace(/[^0-9]/g, ''); // 숫자만 남기도록 변환
	input.value = sanitizedValue;
});

function emailAuthClick() {
	document.getElementById('emailAuth').classList.add('selected');
	document.getElementById('phoneAuth').classList.remove('selected');

	var sendToInput = document.getElementById('sendTo');
	sendToInput.style.height = '40px';
	sendToInput.style.border = '1px solid #ddd';
	sendToInput.style.padding = '0 20px';
	sendToInput.style.boxSizing = 'border-box';
	sendToInput.style.fontSize = '16px';
	sendToInput.style.color = '#222';
	sendToInput.setAttribute('maxlength', '100');

	document.getElementById('authenticationNum').value = '';
	document.getElementById('authenticationNum').setAttribute('onkeyup', "this.value = this.value.replace(/[^0-9]/g, '');");

	document.getElementById('checkAuthenticationLabel').textContent = '';
	document.getElementById('checkAuthenticationNumLabel').textContent = '';

	document.getElementById('sendTo').placeholder = '이메일 입력';
	document.getElementById('sendTo').value = '';
	document.getElementById('sendTo').setAttribute('onkeyup', "this.value = this.value.replace(/[^a-zA-Z0-9@._-]/g, '');"); // 이메일 형식 제한
	document.getElementById('sendTo').setAttribute('placeholder', '이메일 입력');
	document.getElementById('sendAuthenticationNumBtn').setAttribute('onclick', 'sendAuthenticationNumEmail()');

	document.getElementById('sendAuthenticationNumBtn').setAttribute('onclick', 'sendAuthenticationNumEmail()');
	document.getElementById('authenticationNumBtn').setAttribute('onclick', 'checkAuthenticationNumEmail()');
}

function phoneAuthClick() {
	document.getElementById('phoneAuth').classList.add('selected');
	document.getElementById('emailAuth').classList.remove('selected');

	var sendToInput = document.getElementById('sendTo');
	sendToInput.style.height = '40px';
	sendToInput.style.width = '300px';
	sendToInput.style.border = '1px solid #ddd';
	sendToInput.style.padding = '0 20px';
	sendToInput.style.marginRight = '10px';
	sendToInput.style.boxSizing = 'border-box';
	sendToInput.style.fontSize = '16px';
	sendToInput.style.color = '#222';
	sendToInput.setAttribute('maxlength', '11');

	document.getElementById('authenticationNum').value = '';
	document.getElementById('authenticationNum').setAttribute('onkeyup', "this.value = this.value.replace(/[^0-9]/g, '');");

	document.getElementById('checkAuthenticationLabel').textContent = '';
	document.getElementById('checkAuthenticationNumLabel').textContent = '';

	document.getElementById('sendTo').value = '';
	document.getElementById('sendTo').placeholder = '전화번호(- 없이) 입력';
	document.getElementById('sendTo').setAttribute('onkeyup', "this.value = this.value.replace(/[^0-9]/g, '');");
	document.getElementById('sendTo').setAttribute('placeholder', '전화번호(- 없이) 입력');

	document.getElementById('sendAuthenticationNumBtn').setAttribute('onclick', 'sendAuthenticationNumSms()');
	document.getElementById('authenticationNumBtn').setAttribute('onclick', 'checkAuthenticationNumSms()');
}

var xhr;
function sendAuthenticationNumEmail() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'sendAuthenticationNumEmail')
	xhr.send(document.getElementById('sendTo').value)
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById('checkAuthenticationLabel').innerHTML = xhr.responseText;
		}
	}
}

function checkAuthenticationNumEmail() {
	if (xhr == null) {
		document.getElementById('checkAuthenticationNumLabel').innerHTML = '* 이메일로 인증번호가 전송되지 않았습니다.'
		return;
	}
	xhr.open('post', 'checkAuthenticationNumEmail');
	xhr.send(document.getElementById('authenticationNum').value);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById('checkAuthenticationNumLabel').innerHTML = xhr.responseText;
		}
		if (xhr.responseText === '* 인증 성공. 다음 단계로 이동하세요.') {
			var nextStageDiv = document.getElementById('nextStageDiv');
			nextStageDiv.style.display = 'block';
		}

	}
}

function sendAuthenticationNumSms() {
	console.log('sendAuthenticationNumSms 호출')
	xhr = new XMLHttpRequest();
	xhr.open('post', 'sendAuthenticationNumSms')
	xhr.send(document.getElementById('sendTo').value)
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById('checkAuthenticationLabel').innerHTML = xhr.responseText;
		}
	}
}

function checkAuthenticationNumSms() {
	if (xhr == null) {
		document.getElementById('checkAuthenticationNumLabel').innerHTML = '* 휴대폰으로 인증번호가 전송되지 않았습니다.'
		return;
	}
	xhr.open('post', 'checkAuthenticationNumSms');
	xhr.send(document.getElementById('authenticationNum').value);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById('checkAuthenticationNumLabel').innerHTML = xhr.responseText;
		}
		if (xhr.responseText === '* 인증 성공. 다음 단계로 이동하세요.') {
			var nextStageDiv = document.getElementById('nextStageDiv');
			nextStageDiv.style.display = 'block';
		}

	}
}

function agreeConditionCheck() {

	var essentialAgreeCheckbox = document.getElementById('essentialAgree');

	if (essentialAgreeCheckbox.checked) {
		location.href = 'register';
	} else {
		alert('필수 내역은 동의해야 합니다.');
	}
}

/*회원가입=============================================================================*/
/*파일 선택시 input tag에 파일명 표시 (register.jsp에 제이쿼리 추가)*/
$(document).ready(function() {
	$("#profilePicture").on('change', function() {
		var fileName = $(this).val().split('\\').pop();
		$(".upload-name").val(fileName);
	});
});

function execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			if (data.userSelectedType === 'R') {
				document.getElementById('address').value = data.roadAddress;
			} else {
				document.getElementById('address').value = data.jibunAddress;
			}
			document.getElementById('postcode').value = data.zonecode;
		}
	}).open();
}

function regIdCheck() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regIdCheck');
	var sendData = document.getElementById('id').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regIdLabel = document.getElementById('regIdLabel');
			regIdLabel.innerHTML = xhr.responseText;
		};
	}
}

function regPwCheck() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regPwCheck');
	var sendData = document.getElementById('pw').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regPwLabel = document.getElementById('regPwLabel');
			regPwLabel.innerHTML = xhr.responseText;
		};
	}
}

function regConfirmCheck() {
	let pw = document.getElementById('pw');
	confirm = document.getElementById('confirm');
	regConfirmLabel = document.getElementById('regConfirmLabel');
	if (pw.value == confirm.value) {
		regConfirmLabel.innerHTML = ''
	} else {
		regConfirmLabel.innerHTML = '* 비밀번호를 확인하세요.'
	}
}

function regUserNameCheck() {
	console.log('regUserNameCheck 호출')
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regUserNameCheck');
	var sendData = document.getElementById('userName').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regUserNameLabel = document.getElementById('regUserNameLabel');
			regUserNameLabel.innerHTML = xhr.responseText;
		};
	}
}


function regMobileCheck() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regMobileCheck');
	var sendData = document.getElementById('mobile').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regMobileLabel = document.getElementById('regMobileLabel');
			regMobileLabel.innerHTML = xhr.responseText;
		};
	}
}

function regEmailCheck() {
	xhr = new XMLHttpRequest();
	xhr.open('post', 'regEmailCheck');
	var sendData = document.getElementById('email').value;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regMobileLabel = document.getElementById('regEmailLabel');
			regMobileLabel.innerHTML = xhr.responseText;
		};
	}
}

function regEmailSelectCheck() {
	var emailSelect = document.getElementById("emailSelect");
	var selectedOption = emailSelect.value;

	xhr = new XMLHttpRequest();
	xhr.open('post', 'regEmailSelectCheck');
	var sendData = selectedOption;
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var regMobileLabel = document.getElementById('regEmailLabel');
			regMobileLabel.innerHTML = xhr.responseText;
		};
	}
}

function allCheck() {
	var regIdLabel = document.getElementById('regIdLabel');
	var regPwLabel = document.getElementById('regPwLabel');
	var regConfirmLabel = document.getElementById('regConfirmLabel');
	var regUserNameLabel = document.getElementById('regUserNameLabel');
	var regMobileLabel = document.getElementById('regMobileLabel');
	var regEmailLabel = document.getElementById('regEmailLabel');

	if (regIdLabel.textContent !== '') {
		alert('아이디 입력을 확인하세요.')
		return;
	}

	if (regPwLabel.textContent !== '') {
		alert('비밀번호 입력을 확인하세요.')
		return;
	}

	if (regConfirmLabel.textContent !== '') {
		alert('비밀번호 입력을 확인하세요.')
		return;
	}

	if (regUserNameLabel.textContent !== '') {
		alert('이름 입력을 확인하세요.')
		return;
	}

	if (regMobileLabel.textContent !== '') {
		alert('전화번호 입력을 확인하세요.')
		return;
	}

	if (regEmailLabel.textContent !== '') {
		alert('이메일 입력을 확인하세요.')
		return;
	}

	var f = document.getElementById('f');
	f.submit();
}
/*아이디/비밀번호 찾기=============================================================================*/
function findIdByMobile() {
	var regMobileLabel = document.getElementById('regMobileLabel');

	if (regMobileLabel.textContent !== '') {
		alert('전화번호 입력을 확인하세요.')
		return;
	}

	var f = document.getElementById('f');
	f.submit();
}

function findIdByEmail() {
	var regEmailLabel = document.getElementById('regEmailLabel');

	if (regEmailLabel.textContent !== '') {
		alert('이메일 입력을 확인하세요.')
		return;
	}

	var ff = document.getElementById('ff');
	ff.submit();
}

function fiindPwInputCheck() {
    var id = document.getElementById('id').value.trim(); 
	var phoneCheckbox = document.getElementById('phoneCheckbox');
    var emailCheckbox = document.getElementById('emailCheckbox');
    
    if (id === "") { 
        alert('아이디를 입력하세요.');
        return;
    }
	
	if (!phoneCheckbox.checked && !emailCheckbox.checked) {
        alert('발급 방식을 선택하세요.');
        return;
    }
    
    if (phoneCheckbox.checked) {
        deliveryMethod = "휴대폰";
    } else if (emailCheckbox.checked) {
        deliveryMethod = "이메일";
    }
    
    var f = document.getElementById('f');
	f.submit();
}