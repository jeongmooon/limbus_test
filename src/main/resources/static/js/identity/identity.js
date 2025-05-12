document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault();

            const formData = new FormData(e.target);
            fetch("/identity", {
                method: "POST",
                body: formData
            })
            .then(res => {
                if (!res.ok) throw new Error("요청 실패");
                return res.json(); // or .text() depending on response
            })
            .then(data => {
                alert("업로드 성공!");
                window.location.href = "/identity";
            })
            .catch(err => {
                console.error("에러:", err);
                alert("업로드 중 오류가 발생했습니다.");
            });
        });
    }
});

