const query = () => {
    let query = document.getElementById("query").value;
    if(query.length == 0){
        // location.href="/";
        return;
    }
    location.href="search?query=" + query;
}

const searchesClick = () => {
    let query = document.getElementById("query").value;
    if(query.length == 0){
        let searchesId = document.getElementById("searches");
        searchesId.style.display='block';
    }
}

const searchesBlur = () => {
    let query = document.getElementById("query").value;
    // if(query.length == 0){
    //     let searchesId = document.getElementById("searches");
    //     searchesId.style.display='none';
    // }
}

const searchesKeydown = (event) => {
    let query = document.getElementById("query").value;
    if (event.key === "Enter") {
        if(query.length == 0){
            // location.href="/";
            return;
        }
        location.href="search?query=" + query;
        return;
    }

    let searchesId = document.getElementById("searches");
    searchesId.style.display='none';

    if(query.length <= 1){
        searchesId.style.display='block';
    }
}

const searchAllMove = () => {
    $.ajax({
        url: "http://localhost:8080/searchAllMove",
        method: "GET",
        data:{},
        success: function (response) {
            location.reload();
        },
        error: function (xhr, status, error) {
            console.error("오류 발생: " + error);
        }
    });
}