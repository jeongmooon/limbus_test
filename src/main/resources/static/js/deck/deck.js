let deckCount = 0;
let draggedElement = null;

const deckAddBtn = document.querySelector("#deck-add-btn");
const deckContainer = document.getElementById("deck-zones-container");
const deckSaveBtn = document.querySelector(".save-deck");
const newDeckBtn = document.querySelector(".new-deck-list");
const topLabel = document.querySelector("#top-label");
const selectDeckCode = topLabel.querySelector("select");
let deckUUID = document.querySelector("#deck-code").value;

function addDeck() {
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
    deckTitle.classList = "text-lg text-yellow-300 mb-2";
    deckTitle.textContent = `🧩 덱 ${deckCount}`;
    deckWrapper.append(deckTitle);

    const deckMain = document.createElement("div");
    deckMain.classList = "flex flex-wrap gap-2";
    deckMain.id = newDeckId;
    deckWrapper.append(deckMain);

    deckContainer.append(deckWrapper);

    return newDeckId;
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
/*
    // 원본 삭제
    if (draggedFrom !== targetDeckId && draggedFrom !== '') {
        const sourceDeck = document.getElementById(draggedFrom);
        const children = sourceDeck.querySelectorAll("img");
        children.forEach(child => {
            if (child.dataset.identity === data.id && child.dataset.sinner === data.sinnerId) {
                child.remove();
            }
        });
    }*/
}

function saveDeck(){
    const dataArray = new Array;
    const deckNum = deckContainer.children.length;

    for(i=1; i<=deckNum; i++){
        const deckList = document.querySelector(`#deck-container-${i}`);
        const deckName = deckList.previousSibling.textContent;
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
    }

    fetch("/deck", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dataArray)
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
        window.location.href = "/deck";
    })
    .catch(error => {
        alert(JSON.parse(error.message).message);
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
        const targetDeckId = addDeck();
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

function deckCodeChange(event){
    if(deckUUID === "") {
        if(!confirm("현재 덱이 저장되지 않았습니다. 새로운 덱을 생성하면 작성 중인 정보가 모두 사라집니다. 새로운 덱을 만드시겠습니까?")) return;
    }
    for(option of selectDeckCode.children) {
        if(option.value==="") option.remove();
    }
    deckUUID = event.target.value;
    deckCount = 0;
    getDeckList();
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
    selectDeckCode.addEventListener('change', deckCodeChange);
    getDeckList();
});

