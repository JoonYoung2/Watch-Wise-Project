let msg1 = "";
let msg2 = "";
let msg3 = "";
let msg4 = "";
let msg5 = "";
let cnt = 0;
let cnt1 = 0;
let cnt2 = 0;
let cnt3 = 0;

window.onload = () => {
	dailyList(); 
	weeklyList();
	weeklyList2();
	weeklyList3();
}

const dailyList = async () => {
	let dailyMovieNms = "";
	let movieNms = document.getElementById("movieNms");
	await fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20171010",
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				let msg = "";
				let data = result['boxOfficeResult']['dailyBoxOfficeList'];
				for(var i = 0; i < data.length; i++){
					// msg += "<span>" + data[i]['movieNm'] + "</span><br>";
					if(i != data.length-1){
						dailyMovieNms += data[i]['movieCd'] + ",";
					}else{
						dailyMovieNms += data[i]['movieCd'];
					}
				}
				console.log(dailyMovieNms);
				movieNms.value = dailyMovieNms;
				// movieNm(dailyMovieNms.substring(0, dailyMovieNms.length-1), 0);
			})
} 

const weeklyList = async () => {
	let weeklyMovieNms = "";
	let weeklyMovies = document.getElementById("weeklyMovies");
	await fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20231008&weekGb=0",
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				let msg = "";
				let data = result['boxOfficeResult']['weeklyBoxOfficeList'];
				for(var i = 0; i < data.length; i++){
					// msg += "<span>" + data[i]['movieNm'] + "</span><br>";
					if(i != data.length-1){
						weeklyMovieNms += data[i]['movieCd'] + ",";
					}else{
						weeklyMovieNms += data[i]['movieCd']
					}
				}
				weeklyMovies.value = weeklyMovieNms;
				// movieNm(dailyMovieNms.substring(0, dailyMovieNms.length-1), 1);
			})
}

const weeklyList2 = async () => {
	let weeklyMovieNms = "";
	let weeklyMovies = document.getElementById("weekly2Movies");
	await fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20231008&weekGb=1",
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				let msg = "";
				let data = result['boxOfficeResult']['weeklyBoxOfficeList'];
				for(var i = 0; i < data.length; i++){
					// msg += "<span>" + data[i]['movieNm'] + "</span><br>";
					if(i != data.length-1){
						weeklyMovieNms += data[i]['movieCd'] + ",";
					}else{
						weeklyMovieNms += data[i]['movieCd']
					}
				}
				weeklyMovies.value = weeklyMovieNms;
				// movieNm(dailyMovieNms.substring(0, dailyMovieNms.length-1), 2);
			})
}

const weeklyList3 = async () => {
	let weeklyMovieNms = "";
	let weeklyMovies = document.getElementById("weekly3Movies");
	await fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20231008&weekGb=2",
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				let msg = "";
				let data = result['boxOfficeResult']['weeklyBoxOfficeList'];
				for(var i = 0; i < data.length; i++){
					// msg += "<span>" + data[i]['movieNm'] + "</span><br>";
					if(i != data.length-1){
						weeklyMovieNms += data[i]['movieCd'] + ",";
					}else{
						weeklyMovieNms += data[i]['movieCd']
					}
				}
				weeklyMovies.value = weeklyMovieNms;
				// movieNm(dailyMovieNms.substring(0, dailyMovieNms.length-1), 3);
			})
}

const peopleSearch = async () => {
	let peopleNm = document.getElementById("peopleNm").value;
	let peopleCd;
	msg1 = "";
	await fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json?key=f5eef3421c602c6cb7ea224104795888&peopleNm="+peopleNm,
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				console.log(result['peopleListResult']);
				for(var i = 0; i < result['peopleListResult']['peopleList'].length; i++){
					peopleCd = result['peopleListResult']['peopleList'][i]['peopleCd'];
					peopleSearchResult(peopleCd);
				}
			});
}

const peopleSearchResult = async (peopleCd) => {
	let peopleInfoResult;
	let peopleInfo;
	let peopleNm;
	let peopleNmEn;
	let sex;
	let repRoleNm;
	let filmos = [];
	await fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleInfo.json?key=f5eef3421c602c6cb7ea224104795888&peopleCd=" + peopleCd,
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => { 
				console.log(result['peopleInfoResult']);
				peopleInfoResult = result['peopleInfoResult'];
				peopleInfo = peopleInfoResult['peopleInfo'];
				peopleNm = peopleInfo['peopleNm']; 
				peopleNmEn = peopleInfo['peopleNmEn'];
				sex = peopleInfo['sex'];
				repRoleNm = peopleInfo['repRoleNm'];
				filmos = peopleInfo['filmos'];
				
				if(repRoleNm == '배우'){
					if(peopleNmEn.length > 0){
						msg1 += "<div>이름 : "+peopleNm+"("+peopleNmEn+")</div>";		        		
					}else{
						msg1 += "<div>이름 : "+peopleNm+"</div>";	
					}
					if(sex != null){
						msg1 += "<div>성별 : "+sex+"</div>";		        		
					}
					msg1 += "<div>직업 : "+repRoleNm+"</div>";
					for(var i = 0; i < filmos.length; i++){
						if(filmos[i]['moviePartNm'] == '배우'){
							msg1 += "<div>"+(i+1)+"번째 작품 : "+filmos[i]['movieNm']+"</div>";
						}
					}
				}
			})
			if(msg1 == ""){
				let peopleSearchDiv = document.getElementById('peopleSearchDiv');
				peopleSearchDiv.innerHTML = "찾을 수 없는 배우 이름입니다.";
			}else{
				let peopleSearchDiv = document.getElementById('peopleSearchDiv');
				peopleSearchDiv.innerHTML = msg1;
			}
}

const movieNm = async (movieNms, number) => {
	// let movieNm = document.getElementById("movieNm").value;
	let movieListResult = [];
	let openDt;
	let movieName;
	let movieNm = movieNms.split(",");
	
	for(var i = 0; i < movieNm.length; i++){
		await fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888&movieNm=" + movieNm[i],
				{
					method: "get"
				})
				.then(res => res.json())
				.then(result => {
					console.log("movieNm\n-----------------------------\n");
					console.log(result);
					movieListResult = result['movieListResult'];
					for(var j = 0; j < movieListResult['movieList'].length; j++){
						if(movieListResult['movieList'][j]['movieNm'].substring(0, movieNm[i].length) == movieNm[i]){
							openDt = movieListResult['movieList'][j]['openDt'].substring(0, movieListResult['movieList'][j]['openDt'].length-2);
							movieName = movieListResult['movieList'][j]['movieNm']
							if(number == 0){
								test(openDt, movieName);
							}else if(number == 1){
								weekly(openDt, movieName);
							}else if(number == 2){
								weekly2(openDt, movieName)
							}else if(number == 3){
								weekly3(openDt, movieName)
							}
							break;
						}
					}
				})
	}
}

const test = async (openDt, movieName) => {
	let data = [];
	let rows = [];
	let imgId = document.getElementById("imgId");
	await fetch("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&listCount=500&title="+movieName,
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				console.log("result\n-----------------------------\n");
				console.log(result);
				data = result['Data'];
				rows = data[0]['Result'];
				for(var i = 0; i < rows.length; i++){
					if(rows[i]['repRlsDate'].substring(0,rows[i]['repRlsDate'].length-2) == openDt){
						let posters = rows[i]['posters'].split('|');
						msg2 += ++cnt + "위 : <img src=" + posters[0] + ">";
						imgId.innerHTML = msg2;
						break;
					}
				}
			})
}

const weekly = async (openDt, movieName) => {
	let data = [];
	let rows = [];
	let imgId = document.getElementById("weeklyId");
	await fetch("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&listCount=500&title="+movieName,
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				console.log("result\n-----------------------------\n");
				console.log(result);
				data = result['Data'];
				rows = data[0]['Result'];
				for(var i = 0; i < rows.length; i++){
					if(rows[i]['repRlsDate'].substring(0,rows[i]['repRlsDate'].length-2) == openDt){
						let posters = rows[i]['posters'].split('|');
						msg3 += ++cnt1 + "위 : <img src=" + posters[0] + ">";
						imgId.innerHTML = msg3;
						break;
					}
				}
			})
}

const weekly2 = async (openDt, movieName) => {
	let data = [];
	let rows = [];
	let imgId = document.getElementById("weeklyId2");
	await fetch("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&listCount=500&title="+movieName,
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				console.log("result\n-----------------------------\n");
				console.log(result);
				data = result['Data'];
				rows = data[0]['Result'];
				for(var i = 0; i < rows.length; i++){
					if(rows[i]['repRlsDate'].substring(0,rows[i]['repRlsDate'].length-2) == openDt){
						let posters = rows[i]['posters'].split('|');
						msg4 += ++cnt2 + "위 : <img src=" + posters[0] + ">";
						imgId.innerHTML = msg4;
						break;
					}
				}
			})
}

const weekly3 = async (openDt, movieName) => {
	let data = [];
	let rows = [];
	let imgId = document.getElementById("weeklyId3");
	await fetch("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&listCount=500&title="+movieName,
			{
				method: "get"
			})
			.then(res => res.json())
			.then(result => {
				console.log("result\n-----------------------------\n");
				console.log(result);
				data = result['Data'];
				rows = data[0]['Result'];
				for(var i = 0; i < rows.length; i++){
					if(rows[i]['repRlsDate'].substring(0,rows[i]['repRlsDate'].length-2) == openDt){
						let posters = rows[i]['posters'].split('|');
						msg5 += ++cnt3 + "위 : <img src=" + posters[0] + ">";
						imgId.innerHTML = msg5;
						break;
					}
				}
			})
}