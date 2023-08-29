/*공통*/
/*파일 선택시 input tag에 파일명 표시 (html(modifyAnnouncement.jsp, writeAnnouncement.jsp)에 제이쿼리 사용을 위한 import 추가*/
document.addEventListener("DOMContentLoaded", function() {
	$("#upfile").on('change', function() {
		var fileName = $(this).val().split('\\').pop();
		$(".upload-name").val(fileName);
	});
});

function regIdCheck(){
	const titleLabel = document.getElementById('titleLabel');
	const titleInput = document.getElementById('title');

	if (titleInput.value.trim() === '') {
		titleLabel.style.display = 'inline';
	} else {
		titleLabel.style.display = 'none';
	}
}

function titleCheck() {
	const titleLabel = document.getElementById('titleLabel');
	const titleInput = document.getElementById('title');

	if (titleInput.value.trim() === '') {
		titleLabel.style.display = 'inline';
	} else {
		titleLabel.style.display = 'none';
	}
}

function contentCheck() {
	const contentLabel = document.getElementById('contentLabel');
	const contentInput = document.getElementById('content');

	if (contentInput.value.trim() === '') {
		contentLabel.style.display = 'inline';
	} else {
		contentLabel.style.display = 'none';
	}
}

function nullCheck() {
	let title = document.getElementById('title');
	let content = document.getElementById('content');

	if (title.value == "") {
		alert('제목을 입력하세요.');
	} else if (content.value == "") {
		alert('내용을 입력하세요.');
	} else {
		var result = confirm("글을 등록하시겠습니까?");
	}

	if (result) {
		var f = document.getElementById('f');
		f.submit();
	}
}

function fileDownload(num) {
	result = confirm('파일을 다운로드 하시겠습니까?');
	if (result == true) {
		location.href = 'fileDownload?num=' + num;
	}
}


/*공지사항*/

//공지사항 내용
//톱니바퀴 클릭시 수정|삭제 나타남
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('settings').addEventListener('click', function(e) {
		e.preventDefault();
		var clickSetting = document.querySelector('.clickSetting');
		clickSetting.style.display = clickSetting.style.display === 'block' ? 'none' : 'block';
	});
});

//문의답변 수정|삭제
document.addEventListener('DOMContentLoaded', function() {
	var settingsIcons = document.querySelectorAll('[id^="settings-"]');

	settingsIcons.forEach(function(icon) {
		icon.addEventListener('click', function(e) {
			e.preventDefault();
			var replyNum = this.id.split('-')[1];
			var clickSetting = document.querySelector('#clickSettings-' + replyNum);
			clickSetting.style.display = clickSetting.style.display === 'block' ? 'none' : 'block';
		});
	});
});

function modifyCheck() {
	let title = document.getElementById('title');
	let content = document.getElementById('content');

	if (title.value === "") {
		alert('제목을 입력하세요.');
	} else if (content.value === "") {
		alert('내용을 입력하세요.');
	} else {
		var result = confirm("글을 수정하시겠습니까?");
	}

	if (result) {
		var f = document.getElementById('f');
		f.submit();
	}
}

function deleteAnnouncementProc(num) {
	result = confirm('공지사항을 삭제하겠습니까?');
	if (result == true) {
		location.href = 'deleteAnnouncementProc?num=' + num;
	}
}

/* 좋아요 기능 */
function clickHeart(num) {
	var heartIcon = document.querySelector('.heart');
	var heartNumElement = document.getElementById('heartNum');
	var currentHeartNum = parseInt(heartNumElement.textContent);

	var xhr = new XMLHttpRequest();
	xhr.open('post', 'clickHeart?num=' + num);
	xhr.send();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var isLiked = parseInt(xhr.responseText);

			if (isLiked == 1) {
				heartIcon.classList.add('heart-filled');
				var print = document.getElementById('heartNum')
				print.innerHTML = (currentHeartNum + 1);
			} else if (isLiked == 2) {
				alert('로그인이 필요합니다.')
			} else {
				heartIcon.classList.remove('heart-filled');
				var print = document.getElementById('heartNum');

				if (currentHeartNum > 0) {
					print.innerHTML = (currentHeartNum - 1);
				} else {
					print.innerHTML = 0;
				}
			}

		}
	};
}

function writeAnnouncementReply(num) {

	let reply = document.getElementById('reply');

	if (reply.value == "") {
		alert('내용을 입력하세요.');
	} else {
		var xhr = new XMLHttpRequest();
		xhr.open('post', 'writeAnnouncementReply?num=' + num);
		var sendData = document.getElementById('reply').value;
		xhr.send(sendData);
		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {

				try {
					var resData = JSON.parse(xhr.responseText);
					var result = "";
					for (i = 0; i < resData.length; i++) {
						result += "<tr class=\"borderBottomTr\">" + "<td>";
						result += "<p>" + resData[i].reply + "</p>";
						result += "<p>" + resData[i].writer + "&nbsp;" + resData[i].writeDate + "</p>";
						result += "</td>" + "</tr>";

					}

					document.getElementById('tbodyMyReply').innerHTML = result;
					
					var replyCountElements = document.querySelectorAll('.replyCount');
					replyCountElements.forEach(function(replyCountElement) {
					    var currentReplyCount = parseInt(replyCountElement.textContent);
					    replyCountElement.textContent = currentReplyCount + 1;
					});
            
					document.getElementById('reply').value = "";
				} catch {
					alert('로그인이 필요합니다.')
				}

			}
		};
	}
}

function myReplyOnly(num) {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'myReplyOnly?num=' + num);
	var checkbox = document.getElementById('myReplyOnly');
	var sendData = checkbox.checked ? 'checked' : 'unchecked';
	xhr.send(sendData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {

			var resData = JSON.parse(xhr.responseText)
			var result = "";
			for (i = 0; i < resData.length; i++) {
				result += "<tr>" + `<td class="myReplyTd" data-replynum="${resData[i].replyNum}">`; // 댓글의 고유 ID인 replyNum을 data-replynum 속성으로 추가
				result += "<p class=\"replyArea\">" + resData[i].reply + "</p>";
				result += "<p class=\"writeInfo\">" + resData[i].writer + "&nbsp;" + resData[i].writeDate + "</p>";
				if (sendData == 'checked')
					result += "<p class=\"modiOrdele\">"
						+ `<span onclick="announcementReplyModifyClick(${resData[i].replyNum}, ${num})">수정<span>` // 수정 버튼 클릭 시 announcementReplyModifyClick() 메서드 실행
						+ " | "
						+ `<span onclick="deleteReply(${resData[i].replyNum}, ${resData[i].centerNum})">삭제<span>`
						+ "</p>";
				result += "</td>" + "</tr>";
			}
			document.getElementById('tbodyMyReply').innerHTML = result;
		}
	};
}

function deleteReply(replyNum, num) {
	result = confirm('삭제하겠습니까?');
	if (result == true) {
		var xhr = new XMLHttpRequest();
		xhr.open('post', 'deleteReply?replyNum=' + replyNum + '&num=' + num);
		xhr.send();

		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {

				var resData = JSON.parse(xhr.responseText)
				var result = "";
				for (i = 0; i < resData.length; i++) {
					result += "<tr>" + "<td class=\"myReplyTd\">";
					result += "<p>" + resData[i].reply + "</p>";
					result += "<p>" + resData[i].writer + "&nbsp;" + resData[i].writeDate + "</p>";
					result += "<p class=\"modiOrdele\">"
						+ "<span onclick=\"announcementReplyModifyClick(" + resData[i].replyNum + ", " + num + ")\">수정<span> "
						+ " | "
						+ "<span onclick=\"deleteReply(" + resData[i].replyNum + ", " + num + ")\">삭제<span> "
						+ "</p>";
					result += "</td>" + "</tr>";

				}
				document.getElementById('tbodyMyReply').innerHTML = result;
				
				// 댓글 수 감소 코드 추가
                var replyCountElements = document.querySelectorAll('.replyCount');
                replyCountElements.forEach(function(replyCountElement) {
                    var currentReplyCount = parseInt(replyCountElement.textContent);
                    replyCountElement.textContent = currentReplyCount - 1;
                });
			}
		};

	} else {
		myReplyOnly(num);
	}
}

function announcementReplyModifyClick(replyNum, num) {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'announcementReplyModifyClick?replyNum=' + replyNum + '&centerNum=' + num);
	xhr.send();

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var resData = JSON.parse(xhr.responseText);

			var replyArea = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .replyArea`);
			if (replyArea) {
				var input = document.createElement('input');
				input.type = 'text';
				input.classList.add('replyArea');
				input.value = resData[0].reply;
				input.style.width = '900px';
				replyArea.parentNode.replaceChild(input, replyArea);

				var writeInfoElement = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .writeInfo`);
				writeInfoElement.style.display = 'none';

				var modifyButton = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .modiOrdele span:nth-child(1)`);
				modifyButton.textContent = '등록';
				modifyButton.onclick = function() {
					announcementReplyModify(replyNum, num);
				};

				var existingCancelButton = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .modiOrdele span:nth-child(2)`);
				if (existingCancelButton) {
					existingCancelButton.textContent = '취소';
					existingCancelButton.onclick = function() {
						myReplyOnly(num);
					};
				} else {
					var cancelButton = document.createElement('span');
					cancelButton.textContent = '취소';
					cancelButton.onclick = function() {
						myReplyOnly(num);
					};

					modifyButton.parentNode.insertBefore(cancelButton, modifyButton.nextSibling);
				}

				var separator = document.createTextNode(' | ');

				modifyButton.parentNode.insertBefore(cancelButton, modifyButton.nextSibling);
				modifyButton.parentNode.insertBefore(separator, modifyButton.nextSibling);
			} else {
				console.error('댓글 영역을 찾을 수 없습니다.');
			}
		}
	};
}

function announcementReplyModify(replyNum, num) {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'announcementReplyModify?replyNum=' + replyNum + '&centerNum=' + num);
	var sendData = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .replyArea`).value;
	xhr.send(sendData);

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var resData = JSON.parse(xhr.responseText);

			var replyArea = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .replyArea`);
			if (replyArea) {
				var pTag = document.createElement('p');
				pTag.classList.add('replyArea');
				pTag.textContent = resData.reply;
				replyArea.parentNode.replaceChild(pTag, replyArea);

				var writeInfoElement = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .writeInfo`);
				writeInfoElement.style.display = 'block';
				writeInfoElement.textContent = resData.writer + ' ' + resData.writeDate;

				var modifyButton = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .modiOrdele span:nth-child(1)`);
				modifyButton.textContent = '수정';
				modifyButton.onclick = function() {
					announcementReplyModifyClick(replyNum, num);
				};

				var existingCancelButton = document.querySelector(`.myReplyTd[data-replynum="${replyNum}"] .modiOrdele span:nth-child(2)`);
				if (existingCancelButton) {
					existingCancelButton.textContent = '삭제';
					existingCancelButton.onclick = function() {
						deleteReply(replyNum, num);
					};
				} else {
					var cancelButton = document.createElement('span');
					cancelButton.textContent = '삭제';
					cancelButton.onclick = function() {
						deleteReply(replyNum, num);
					};

					modifyButton.parentNode.insertBefore(cancelButton, modifyButton.nextSibling);
				}

				var separator = document.createTextNode(' | ');

				modifyButton.parentNode.insertBefore(cancelButton, modifyButton.nextSibling);
				modifyButton.parentNode.insertBefore(separator, modifyButton.nextSibling);

			} else {
				console.error('댓글 영역을 찾을 수 없습니다.');
			}
		}
	};
}

/*문의하기*/
function writeInquiryLoginCheck() {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'writeInquiryLoginCheck');
	xhr.send();

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var loginCheck = parseInt(xhr.responseText);

			if (loginCheck == 1) {
				location.href = 'writeInquiry';
			} else {
				alert('로그인이 필요합니다.');
			}
		}
	};
}

function inquirySecretCheck(num) {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'inquirySecretCheck?num=' + num);
	xhr.send();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var secretCheck = parseInt(xhr.responseText);
			if (secretCheck === 1) {
				var screenWidth = window.screen.width;
				var screenHeight = window.screen.height;

				var width = 400;
				var height = 200;
				var left = (screenWidth - width) / 2;
				var top = (screenHeight - height) / 2;

				window.open('SecretPwInput?num=' + num, '', `width=${width}, height=${height}, left=${left}, top=${top}`);
			} else {
				location.href = 'inquiryContent?num=' + num;
			}
		}
	};
}

function modifyAble(num) {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'modifyAble?centerNum=' + num);
	xhr.send();

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var modifyAble = parseInt(xhr.responseText);

			if (modifyAble == 1) {
				location.href = 'modifyInquiry?num=' + num;
			} else {
				alert('관리자 답변이 있는 문의글은 수정이 불가합니다.');
			}
		};
	}
}

function deleteInquiryProc(num) {
	result = confirm('문의사항을 삭제하겠습니까?');
	if (result == true) {
		location.href = 'deleteInquiryProc?num=' + num;
	}
}

function replyContentCheck() {
	const replyContentInput = document.getElementById('replyContent');

	if (replyContentInput.value.trim() === '') {
		contentLabel.style.display = 'inline';
	} else {
		contentLabel.style.display = 'none';
	}
}

function modifyAdminReplyClick(replyNum, centerNum) {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'modifyAdminReplyClick?replyNum=' + replyNum + '&centerNum=' + centerNum);
	xhr.send();

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var resData = JSON.parse(xhr.responseText);

			var adminReplyDiv = document.querySelector(`#settings-${replyNum}`).closest('.adminReply');

			var contentAreaReply = adminReplyDiv.querySelector('.contentAreaReply');
			var textarea = document.createElement('textarea');
			textarea.classList.add('contentAreaReply');
			textarea.setAttribute('rows', '10');
			textarea.setAttribute('cols', '30');
			textarea.textContent = resData[0].reply;
			adminReplyDiv.replaceChild(textarea, contentAreaReply);

			var writeDateElement = adminReplyDiv.querySelector('.writeDate');
			writeDateElement.style.display = 'none';

			var modifyButton = adminReplyDiv.querySelector('ul.clickSettings li:nth-child(1)');
			modifyButton.textContent = "등록";
			modifyButton.onclick = function() {
				var modifiedContent = textarea.value;
				modifyAdminReply(replyNum, centerNum, modifiedContent);
			};

			var modifyButton = adminReplyDiv.querySelector('ul.clickSettings li:nth-child(3)');
			modifyButton.textContent = "취소";
			modifyButton.onclick = function() {
				location.href = 'inquiryContent?num=' + centerNum;
			};
		}
	};
}

function modifyAdminReply(replyNum, centerNum, modifiedContent) {
	var xhr = new XMLHttpRequest();
	xhr.open('post', 'modifyAdminReply?replyNum=' + replyNum + '&centerNum=' + centerNum);
	xhr.send(modifiedContent);

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var resData = JSON.parse(xhr.responseText);
			var modifiedReply = resData.reply;

			modifiedReply = modifiedReply.replaceAll('<br>', '\n');

			var adminReplyDiv = document.querySelector(`#settings-${replyNum}`).closest('.adminReply');

			var textarea = adminReplyDiv.querySelector('.contentAreaReply');
			var pTag = document.createElement('p');
			pTag.style.whiteSpace = 'pre-line';
			pTag.classList.add('contentAreaReply');
			pTag.innerHTML = modifiedReply;
			adminReplyDiv.replaceChild(pTag, textarea);

			var writeDateElement = adminReplyDiv.querySelector('.writeDate');
			writeDateElement.style.display = 'block';

			var modifyButton = adminReplyDiv.querySelector('ul.clickSettings li:nth-child(1)');
			var cancelButton = adminReplyDiv.querySelector('ul.clickSettings li:nth-child(3)');
			modifyButton.textContent = "수정";
			modifyButton.onclick = function() {
				modifyAdminReplyClick(replyNum, centerNum);
			};
			cancelButton.textContent = "삭제";
			modifyButton.onclick = function() {
				modifyAdminReplyClick(replyNum, centerNum);
			};
		}
	};
}

function deleteAdminReply(replyNum, centerNum) {
	var result = confirm("답변을 삭제하시겠습니까?");

	if (result) {
		location.href = 'deleteAdminReply?replyNum=' + replyNum + '&centerNum=' + centerNum;
	}
}

