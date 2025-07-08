const boardWriteBtn = document.querySelector(".board-write-btn");
const contentArea = document.querySelector("#content");

const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    theme: 'dark',
    toolbarItems: [
        ['heading', 'bold', 'italic', 'strike'],
        ['hr', 'quote'],
        ['ul', 'ol', 'task'],
        ['code', 'codeblock'],
        ['link'] // 'image' 항목 제거됨
    ],
    customHTMLSanitizer: (html) => {
        return html.replace(/<img[^>]*>/g, '');
    }
});


// 페이지 준비 시 초기 실행 (예: 에러 메시지 자동 실행)
document.addEventListener("DOMContentLoaded", () => {
    editor.setHTML(contentArea.value);
    document.querySelector('#editor').addEventListener('drop', function (e) {
      if (e.dataTransfer.files.length > 0) {
        // 이미지 드래그 앤 드롭 방지
        e.preventDefault();
      }
    });

    document.getElementById("boardForm").addEventListener("submit", async function(e) {
        e.preventDefault();
        const htmlContent = editor.getHTML();

        if (htmlContent.includes('<img') || htmlContent.includes('![')) {
            alert('이미지는 첨부할 수 없습니다.');
            return;
        }

        document.querySelector('#content').value = htmlContent;

        const formData = {
            id : this[name="boardId"].value,
            title: this[name="title"].value,
            content: this[name="content"].value
        };

        fetchUse("/board/modify", "POST", "application/json", formData, "저장 중입니다...")
        .then(response => {
            if(!response.ok){
                return response.text().then(msg=>{
                    throw new Error(msg);
                });
            }
            return response.text();
        })
        .then(result => {
            hrefUrl("/main");
        })
        .catch(error =>{
            alert(JSON.parse(error.message).message);
        });
    });
});
