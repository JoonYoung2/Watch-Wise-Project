function openModalForReport(author, comment, movieId){
	console.log("dkdkdkdkdk");
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal');
	
	let authorArea = document.getElementById('storage-for-author');
	let commentArea = document.getElementById('storage-for-comment');
	let movieIdArea = document.getElementById('storage-for-movieId');
	
	modal.style.display = 'block';
	body.style.display = 'block';
	modal.style.zIndex = '2';
	body.style.zIndex = '1';
	authorArea.value = author;
	commentArea.value = comment;
	movieIdArea.value = movieId;
}

function closeModalForReport(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal');
	modal.style.display = 'none';
	body.style.display = 'none';
	modal.style.zIndex = '-2';
	body.style.zIndex = '-2';
}

	let badWord = document.getElementById('bad-words');
	let spam = document.getElementById('spam');
	let falseFact = document.getElementById('false-fact');
	let racism = document.getElementById('racism');
	let spoiler = document.getElementById('spoiler');

//------------------ Report 유형 별 ajax 및 ui 구성 ----- START ---------------------------------	
function reportForBadWord(){
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let reason = '욕설 또는 비속어';
	badWord.style.backgroundColor = "grey";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "";

	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);		
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}
function reportForSpam(){
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let reason = '도배 및 스팸';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "grey";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "";
	
	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);		
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}

function reportForFalseFact(){
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let reason = '허위 정보';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "grey";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "";
	
	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}

function reportForRacism(){
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let reason = '차별 / 혐오 발언';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "grey";
	spoiler.style.backgroundColor = "";

	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}

function reportForSpoiler(){
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let reason = '스포일러';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "grey";

	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}
//------------------ Report 유형 별 ajax 및 ui 구성 ----- END ---------------------------------	

function cancelReport(movieId){
	let author = document.getElementById('storage-for-author').value;
	
	$.ajax({
		url: 'cancelReport',
	 	type: 'GET',
	 	data:{
			 movieId, author
		 },
		 success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);
		 },
		 error:() => {
			 console.log(error);
		 }
	});

    closeModalForReport();
}