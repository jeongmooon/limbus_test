let deckCount = 0;
let draggedElement = null;

const deckCardContainer = document.querySelector("#deck-card-container");
const deckAddBtn = document.querySelector("#deck-add-btn");
const deckUuidSearchBtn = document.querySelector("#deck-uuid-search-btn");
const deckContainer = document.getElementById("deck-zones-container");
const deckSaveBtn = document.querySelector(".save-deck");
const newDeckBtn = document.querySelector(".new-deck-list");
const deckUuidCopyBtn = document.querySelector(".deck-uuid-copy");
const topLabel = document.querySelector("#top-label");
const selectDeckCode = topLabel.querySelector("select");

let deckUUID = document.querySelector("#deck-code").value;

function getDeckCardList(){
    fetch(`/deck/card`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(msg => {
                throw new Error(msg);
            });
        }
        return response.json();
    })
    .then(result => {
        result.map(sinner =>{
            sinner.userIdentities.map(identity =>{
                const identityImgCard = document.createElement("div");
                identityImgCard.classList = "w-full aspect-square bg-gray-800 rounded overflow-hidden";
                identityImgCard.draggable = true;
                identityImgCard.ondragstart = handleDragStart;
                identityImgCard.dataset.id = identity.id;
                identityImgCard.dataset.sinner = sinner.id;
                identityImgCard.dataset.img = `/image/prisoner/${sinner.id}/${identity.imgPath}.webp`;

                const identityImg = document.createElement("img");
                identityImg.classList = "object-cover w-full h-full hover:scale-105 transition-transform duration-200"
                identityImg.src = `/image/prisoner/${sinner.id}/${identity.imgPath}.webp`;
                identityImg.alt = `${identity.name}`;

                identityImgCard.append(identityImg);
                deckCardContainer.append(identityImgCard);
            })
        });
    })
    .catch(error => {
        alert(JSON.parse(error.message).message);
    });
}

function addDeck(event, deck) {
    deckCount++;

    const newDeckId = `deck-container-${deckCount}`;

    const deckWrapper = document.createElement("div");
    deckWrapper.classList = "relative group border-2 border-dashed border-yellow-300 p-4 rounded-md min-h-[100px]";
    deckWrapper.dataset.deckId = newDeckId;

    // 삭제 버튼
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "✖";
    deleteBtn.classList = "absolute top-2 right-2 text-yellow-300 hover:text-red-500 font-bold text-lg hidden group-hover:block";
    deleteBtn.onclick = () => deckWrapper.remove();
    deckWrapper.appendChild(deleteBtn);

    // ▼ 토글 버튼 추가
    const toggleBtn = document.createElement("button");
    toggleBtn.textContent = "▼";
    toggleBtn.classList = "absolute top-2 right-10 text-yellow-300 hover:text-blue-400 font-bold text-lg hidden group-hover:block";
    deckWrapper.appendChild(toggleBtn);

    if(typeof deck !== "undefined") {
        const keyword = deck.deckIdentity.map(identity=>identity.keyword)
        console.log(keyword);
    }

    // 설명 영역 (기본 숨김)
    const infoBox = document.createElement("div");
    infoBox.classList = "mt-2 text-sm text-yellow-100 bg-gray-800 rounded p-2 hidden";
    infoBox.innerHTML = `
        <p class="keyword"><strong>키워드:</strong> 아직 설정되지 않음</p>
        <p class="소속"><strong>소속:</strong> 아직 설정되지 않음</p>
    `;
    deckWrapper.appendChild(infoBox);

    // 토글 이벤트 연결
    toggleBtn.addEventListener("click", () => {
        infoBox.classList.toggle("hidden");
    });

    // 드래그 오버 / 드롭
    deckWrapper.ondragover = (event) => event.preventDefault();
    deckWrapper.ondrop = (event) => handleDrop(event, newDeckId);

    // 드래그 요소가 나갈 때 삭제 로직
    deckWrapper.ondragenter = (event) => event.preventDefault();

    deckWrapper.ondragleave = (event) => {
        event.preventDefault();

        // relatedTarget이 null이거나, 덱 내부가 아니면 바깥으로 나간 것
        const isLeavingToOutside = (typeof related !== "undefined") ? !related || !deckWrapper.contains(related) : null;

        if (draggedElement && isLeavingToOutside) {
            // 이 deckWrapper 안에 있었던 이미지일 경우만 삭제
            if (deckWrapper.contains(draggedElement)) {
                const draggedImg = deckWrapper.querySelector(`[data-id='${draggedElement.dataset.id}'][data-sinner='${draggedElement.dataset.sinner}']`);
                if (draggedImg) {
                    draggedImg.remove();
                }
            }
            draggedElement = null;
        }
    };

    // 타이틀 + 이미지 표시 영역 생성
    const deckTitle = document.createElement("h2");
    deckTitle.classList = "text-lg text-yellow-300 mb-2 deck-name";
    deckTitle.textContent = (typeof deck === "undefined") ? `🧩 덱 ${deckCount}` : deck.name;
    deckTitle.addEventListener("dblclick", () => makeEditable(deckTitle));
    deckWrapper.append(deckTitle);

    const deckMain = document.createElement("div");
    deckMain.classList = "flex flex-wrap gap-2";
    deckMain.id = newDeckId;
    deckWrapper.append(deckMain);

    deckContainer.append(deckWrapper);

    return newDeckId;
}

function makeEditable(deckTitleElement) {
    const currentText = deckTitleElement.textContent.trim();
    const input = document.createElement("input");
    input.type = "text";
    input.value = currentText;
    input.className = "text-lg text-yellow-300 mb-2 deck-name bg-transparent border-b border-yellow-300 outline-none w-full";

    // 기존 h2 대체
    deckTitleElement.replaceWith(input);
    input.focus();
    input.select();

    input.addEventListener("blur", () => {
        const newText = input.value.trim() || "🧩 덱";
        const newTitle = document.createElement("h2");
        newTitle.className = "text-lg text-yellow-300 mb-2 deck-name";
        newTitle.textContent = newText;

        // 다시 더블클릭 이벤트 연결
        newTitle.addEventListener("dblclick", () => makeEditable(newTitle));

        input.replaceWith(newTitle);
    });

    input.addEventListener("keydown", e => {
        if (e.key === "Enter") input.blur();
    });
}

// 드래그 시작 시 데이터 저장
function handleDragStart(e) {
    draggedElement = e.currentTarget;
    draggedElement._originalParent = draggedElement.parentElement;
    draggedElement._moved = false;

    const { id, sinner, img } = draggedElement.dataset;
    const sourceDeckId = draggedElement.parentElement.id;

    e.dataTransfer.setData("text/plain", JSON.stringify({ id, sinnerId: sinner, img, sourceDeckId }));
    e.dataTransfer.effectAllowed = "move";
}

// 드롭 시 처리
function handleDrop(e, targetDeckId) {
    e.preventDefault();

    const data = JSON.parse(e.dataTransfer.getData("text/plain"));
    const targetContainer = document.getElementById(targetDeckId);
    const draggedFrom = data.sourceDeckId;

    // 같은 덱 내부 정렬
    if (draggedFrom === targetDeckId) {
        const existingElement = targetContainer.querySelector(`[data-id='${data.id}'][data-sinner='${data.sinnerId}']`);
        const dropTarget = e.target.closest("img");

        if (!dropTarget || dropTarget.tagName !== "IMG") {
            targetContainer.appendChild(existingElement);
        } else {
            targetContainer.insertBefore(existingElement, dropTarget);
        }

        if (existingElement) existingElement._moved = true;
        return;
    }

    // 제약 조건 확인
    const identityImgs = targetContainer.querySelectorAll("img");
    if (identityImgs.length > 11) {
        alert("인격 갯수는 12개 까지 가능합니다.");
        if (draggedElement) draggedElement._moved = true;
        return;
    }

    for (const identityImg of identityImgs) {
        if (identityImg.dataset.sinner === data.sinnerId) {
            alert("동일 수감자는 등록할 수 없습니다.");
            if (draggedElement) draggedElement._moved = true;
            return;
        }
        if (identityImg.dataset.identity === data.id) {
            alert("동일 인격은 등록할 수 없습니다.");
            if (draggedElement) draggedElement._moved = true;
            return;
        }
    }

    // 새 이미지 생성
    const img = document.createElement("img");
    img.src = data.img;
    img.alt = "덱 인격";
    img.dataset.identity = data.id;
    img.dataset.sinner = data.sinnerId;
    img.dataset.id = data.id;
    img.dataset.img = data.img;
    img.className = "w-32 h-32 object-cover rounded shadow";
    img.draggable = true;
    img.ondragstart = handleDragStart;

    targetContainer.appendChild(img);
    if (draggedElement) draggedElement._moved = true;
}

function saveDeck(){
    const dataArray = new Array;
    const deckNum = deckContainer.children.length;

    const deckContainers = document.querySelectorAll("[id^='deck-container-']");
    deckContainers.forEach(deckList => {
        const deckName = deckList.previousElementSibling?.textContent?.trim();
        const identityImgList = deckList.querySelectorAll("img");
        const identityList = new Array;

        for(identity of identityImgList){
            identityList.push(identity.dataset["identity"]);
        }

        const data = {
            uuid : deckUUID,
            name : deckName,
            identityList :identityList
        }

        dataArray.push(data);
    });

    fetchUse("/deck","POST","application/json",dataArray, "저장 중입니다...")
    .then(response => {
        if (!response.ok) {
            return response.text().then(msg => {
                throw new Error(msg);
            });
        }
        return response.json();
    })
    .then(result => {
        hrefUrl("/deck");
        //window.location.href = "/deck";
    })
    .catch(error => {
        alert(JSON.parse(error.message).message);
    })
    .finally(() => {
        loading.style.display = "none";
    });
}

function getDeckList(){
    if(deckUUID !== ""){
        fetch(`/deck/${deckUUID}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => {
                    throw new Error(msg);
                });
            }
            return response.json();
        })
        .then(result => {
            makeDeckContainer(result);
        })
        .catch(error => {
            alert(JSON.parse(error.message).message);
        });
    }
}
// addDeck
function makeDeckContainer(result){
    removeDeckList();
    const deckList = result.deckList;
    for(deck of deckList){
        const targetDeckId = addDeck(null, deck);
        const targetContainer = document.getElementById(targetDeckId);

        for(identity of deck.deckIdentity){
            // 새 이미지 생성
            const img = document.createElement("img");
            img.src = identity.imgPath;
            img.alt = "덱 인격";
            img.dataset.identity = identity.identityId;
            img.dataset.sinner = identity.sinnerId;
            img.dataset.id = identity.identityId;
            img.dataset.img = identity.imgPath;
            img.className = "w-32 h-32 object-cover rounded shadow";
            img.draggable = true;
            img.ondragstart = handleDragStart;

            targetContainer.appendChild(img);
            if (draggedElement) draggedElement._moved = true;
        }
    }
}

function removeDeckList(){
    while (deckContainer.firstChild) {
      deckContainer.removeChild(deckContainer.firstChild);
    }
}

function newDeckList(){
    if(deckUUID === ""){
        if(!confirm("현재 덱이 저장되지 않았습니다. 새로운 덱을 생성하면 작성 중인 정보가 모두 사라집니다. 새로운 덱을 만드시겠습니까?")) return;
    } else {
        const option = document.createElement("option");
        option.value = '';
        option.textContent = "새로운 덱";
        option.selected = true;
        selectDeckCode.append(option);
        deckUUID = "";
    }
    removeDeckList();
    deckCount = 0;
}

function deckCodeChange(event, uuid){
    if(deckUUID === "") {
        if(!confirm("현재 덱이 저장되지 않았습니다. 새로운 덱을 생성하면 작성 중인 정보가 모두 사라집니다. 새로운 덱을 만드시겠습니까?")) return;
    }
    for (let i = selectDeckCode.children.length - 1; i >= 0; i--) {
        const option = selectDeckCode.children[i];
        if (option.value === "") {
            option.remove();
        }
    }
    deckUUID = event.target.value;
    deckCount = 0;
    getDeckList();
}

function deckUuidSearch(){
    if(deckUUID === "") {
        if(!confirm("현재 덱이 저장되지 않았습니다. 새로운 덱을 생성하면 작성 중인 정보가 모두 사라집니다. 새로운 덱을 만드시겠습니까?")) return;
    }
    for (let i = selectDeckCode.children.length - 1; i >= 0; i--) {
        const option = selectDeckCode.children[i];
        if (option.value === "") {
            option.remove();
        }
    }

    const option = document.createElement("option");
    option.value = '';
    option.textContent = "새로운 덱";
    option.selected = true;
    selectDeckCode.append(option);
    deckUUID = "";
    const uuid = document.querySelector("input[name='deck-uuid']").value;

    deckUUID = uuid;
    getDeckList();
    deckUUID = "";
}

function deckUuidCopy(event){
        if(deckUUID === "") return alert("저장 후에 복사버튼 눌러주세요.");
        navigator.clipboard.writeText(deckUUID)
            .then(() => {
                alert("클립보드에 복사되었습니다!");
            });
}

// 드래그가 끝났지만 드롭되지 않은 경우만 삭제
document.addEventListener("dragend", () => {
    if (draggedElement && !draggedElement._moved) {
        const isDeckImage = draggedElement.closest("[data-deck-id]");
        if (isDeckImage) {
            draggedElement.remove();
        }
    }

    draggedElement = null;
});

window.addEventListener('DOMContentLoaded', function() {
    deckAddBtn.addEventListener('click', addDeck);
    deckSaveBtn.addEventListener('click', saveDeck);
    newDeckBtn.addEventListener('click', newDeckList);
    deckUuidSearchBtn.addEventListener('click',deckUuidSearch);
    deckUuidCopyBtn.addEventListener('click',deckUuidCopy);
    selectDeckCode.addEventListener('change', deckCodeChange);
    getDeckCardList();
    getDeckList();
});

