function openModalForReport(){
	console.log("dkdkdkdkdk");
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal');
	
	modal.style.display = 'block';
	body.style.display = 'block';
	modal.style.zIndex = '2';
	body.style.zIndex = '1';
}

function closeModalForReport(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal');
	modal.style.display = 'none';
	body.style.display = 'none';
	modal.style.zIndex = '-2';
	body.style.zIndex = '-2';
}