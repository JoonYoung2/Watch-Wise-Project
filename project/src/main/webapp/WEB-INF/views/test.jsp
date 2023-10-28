<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.rating-container {
  width: 200px;
  height: 20px;
  background-color: #ccc;
  position: relative;
}

.handle {
  width: 20px;
  height: 20px;
  background-color: #f00;
  position: absolute;
  cursor: grab;
  user-select: none;
}
</style>
<title>Insert title here</title>
<script>
const handle = document.getElementById("handle");
const submitButton = document.getElementById("submit-button");
let isDragging = false;
let rating = 0; // 초기값

handle.addEventListener("mousedown", (event) => {
  isDragging = true;
  handle.style.cursor = "grabbing";
  handle.setPointerCapture(event.pointerId); // 포인터 캡처 활성화
});

document.addEventListener("mouseup", () => {
  isDragging = false;
  handle.style.cursor = "grab";
  handle.releasePointerCapture(event.pointerId); // 포인터 캡처 비활성화
});

document.addEventListener("mousemove", (event) => {
  if (isDragging) {
    const container = document.querySelector(".rating-container");
    const containerRect = container.getBoundingClientRect();
    const mouseX = event.clientX - containerRect.left;
    const handleX = mouseX - handle.offsetWidth / 2;

    if (handleX >= 0 && handleX <= containerRect.width - handle.offsetWidth) {
      handle.style.left = handleX + "px";
      rating = (handleX / containerRect.width) * 5; // 평점 계산
    }
  }
});

submitButton.addEventListener("click", () => {
  // 서버로 평점을 전송
  const endpoint = "/a"; // 엔드포인트 URL
  const data = { rating: rating }; // 전송할 데이터
  fetch(endpoint, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      // 서버 응답 처리
      if (response.status === 200) {
        alert("평점이 성공적으로 제출되었습니다.");
      } else {
        alert("평점 제출 중 오류가 발생했습니다.");
      }
    })
    .catch((error) => {
      console.error("오류:", error);
    });
});

</script>
</head>
<body>
  <div class="rating-container">
    <div id="handle" class="handle"></div>
  </div>
  <button id="submit-button">제출</button>
</body>
</html>