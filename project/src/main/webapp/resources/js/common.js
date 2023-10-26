//---------------------Sign out-----------------------------------------
function localConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/signOut";
	}
}

function kakaoConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/kakaoSignOut";
	}
}

function naverConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/naverSignOut";
	}
}

function googleConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/googleSignOut";
	}
}


//---------------------Unregister-----------------------------------------

function socialConfirmUnregister(){
	var confirmation = confirm("정말 탈퇴 하시겠습니까?");
	if(confirmation){
		window.location.href="/selectAgreedSocial";
	}
}
function kakaoConfirmUnregister(){
	var confirmation = confirm("정말 탈퇴 하시겠습니까?");
	if(confirmation){
		window.location.href="/kakaoUnregister";
	}
}

function naverConfirmUnregister(){
	var confirmation = confirm("정말 탈퇴 하시겠습니까?");
	if(confirmation){
		window.location.href="/naverUnregister?token=${sessionScope.accessToken }";
	}
}

function googleConfirmUnregister(){
	var confirmation = confirm("정말 탈퇴 하시겠습니까?");
	if(confirmation){
		window.location.href="/googleUnregister";
	}
}
