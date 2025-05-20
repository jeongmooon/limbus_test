let page = 0;
let isLoading = false;
let hasMore = true;

const postListEl = document.getElementById('post-list');
const loading = document.getElementById("loading");

// 게시글 렌더링 함수
function renderPosts(posts) {
    posts.forEach(post => {
        const row = document.createElement('tr');
        row.className = 'border-t border-gray-600 hover:bg-gray-700 transition';
        for (let key in post) {
            if(key === "content") continue;
            const td = document.createElement('td');
            td.className = "p-3";

            if(key === "title"){
                const div = document.createElement('div');
                div.className = "text-yellow-300 hover:underline cursor-pointer";
                div.textContent = `${post.title}`;
                div.onclick = () => hrefUrl(`/board/${post.id}`);
                td.append(div);
            } else {
                td.textContent = `${post[key]}`;
            }
            row.append(td);
        }
        postListEl.appendChild(row);
    });
}

// 게시글 fetch
function fetchPosts() {
    if (isLoading || !hasMore) return;
    isLoading = true;

    fetchUse(`/board/list?page=${page}&size=10`, "GET", null, null, "로딩 중입니다...")
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => {
                    throw new Error(msg);
                });
            }
            return response.json();
        })
        .then(result => {
            renderPosts(result.list);
            page++;
            hasMore = !result.last;
            isLoading = false;
            checkAndFetchIfNotFull();
        })
        .catch(error => {
            try {
                alert(JSON.parse(error.message).message);
            } catch (e) {
                alert("게시글을 불러오는 중 오류가 발생했습니다.");
            }
            console.error('게시글 로딩 중 오류:', error.message);
        })
        .finally(() => {
            isLoading = false;
            loading.style.display = "none";
        });
}

// 스크롤이 없을 정도로 짧은 화면이라면 추가 호출
function checkAndFetchIfNotFull() {
    if (!isLoading && hasMore && document.body.scrollHeight <= window.innerHeight) {
        fetchPosts();
    }
}

// 무한 스크롤 이벤트
window.addEventListener('scroll', () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 300) {
        fetchPosts();
    }
});

// 페이지 로딩 시 초기 데이터 불러오기
document.addEventListener('DOMContentLoaded', () => {
    fetchPosts();
    checkAndFetchIfNotFull(); // 첫 로딩 후 화면이 꽉 찼는지 확인
});
