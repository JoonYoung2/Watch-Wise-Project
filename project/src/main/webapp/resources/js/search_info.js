const query = () => {
    let query = document.getElementById("query").value;
    if(query.length == 0){
        location.href="/";
        return;
    }
    location.href="search?query=" + query;
}