const container = document.querySelector("#container");
const editBtn = document.querySelector(".edit-identity-button");
const addIdentityList = new Set;
const deleteIdentityList = new Set;

function getUserIdentityList(){
    fetch("/user-identity/main", {
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
        console.log(result)
        makeThumbSinner(result);
    })
    .catch(error => {
        alert(JSON.parse(error.message).message);
    });
}

function makeThumbSinner(result){
    const userText = document.querySelector("#identity-dictionary-text");
    userText.textContent = `(${result.prisonerGuid}/${result.sinnerIdentityCount}) ${result.rate}%`;

    const rate = document.querySelector("#identity-dictionary-rate");
    rate.style.width = `${result.rate}%`;

    result.userSinnerList.map(sinner=>{
        const sinnerNameTag = document.createElement("h2");
        sinnerNameTag.classList = "text-2xl text-yellow-300 font-bold mt-10 mb-4";
        sinnerNameTag.textContent = sinner.name;
        container.append(sinnerNameTag);

        const identityTag = document.createElement("div");
//        identityTag.classList = `grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4`;
        identityTag.classList = `grid grid-cols-4 sm:grid-cols-6 md:grid-cols-8 gap-4`;

        sinner.userIdentities.map(identity=>{
            identityTag.append(makeThumbIdentity(identity,sinner.id));
        });
        container.append(identityTag);
    });
}

function makeThumbIdentity(identity,sinnerId){
    const identityContainer = document.createElement("div");
    identityContainer.classList = 'bg-zinc-900 border border-gray-700 rounded-lg p-4 shadow-md identity-container';
//    identityContainer.classList = 'bg-zinc-900 border border-gray-700 rounded-lg p-2 shadow-md identity-container w-full max-w-[120px]';

    const identityImg = document.createElement("img");
    identityImg.classList = `rounded-md mb-3 ${identity.owned ? '' : 'grayscale'}`;
//    identityImg.classList = `rounded-md mb-1 w-full aspect-[3/4] object-cover ${identity.owned ? '' : 'grayscale'}`;
    identityImg.src = `/image/prisoner/${sinnerId}/${identity.imgPath}.webp`;
    identityImg.alt = "Identity Image";
    identityContainer.append(identityImg);

    const identityName = document.createElement("h3");
//    identityName.classList = `text-orange-300 font-semibold text-sm mb-1`;
    identityName.classList = `text-orange-300 font-semibold text-[10px] mb-1`; // 더 작은 텍스트
    identityName.textContent = `${identity.name}`;
    identityContainer.append(identityName);

    const identityOwned = document.createElement("p");
//    identityOwned.classList = `text-xs text-gray-300 mb-1`;
    identityOwned.classList = `text-xs text-gray-300 mb-1`;
    identityContainer.append(identityOwned);

    const identityOwnedText = document.createElement("span");
    if(identity.owned){
        identityOwnedText.classList = `text-green-400 is-identity`;
        identityOwnedText.textContent = `✅ 보유`;
    } else {
        identityOwnedText.classList = `text-red-500 is-identity`;
        identityOwnedText.textContent = `❌ 미보유`;
    }
    identityOwned.textContent = `보유 상태:`;
    identityOwned.append(identityOwnedText);
    identityContainer.append(identityOwned);

    const identityGrade = document.createElement("p");
    identityGrade.classList = `text-xs text-gray-400`;
    identityGrade.textContent =  `등급 : `;

    const identityGradeImg = document.createElement("img");
    identityGradeImg.src = `/image/identity/grade/${identity.grade}.webp`;
    identityGradeImg.alt = `${identity.grade}`;
    identityGradeImg.classList = "w-6 h-6 inline";
    identityGrade.append(identityGradeImg);

    identityContainer.append(identityGrade);

    const identityId = document.createElement("p");
    identityId.classList = `text-xs text-gray-400 identity-id`;
    identityId.textContent = `${identity.id}`;
    identityId.dataset['identityId'] = `${identity.id}`;

    identityContainer.append(identityId);

    return identityContainer;
}

function editButton(){
    if(editBtn.classList.contains('edit')){
        const data = {
            "addIdentity" : [...addIdentityList],
            "deleteIdentity" : [...deleteIdentityList]
        }

        fetch("/user-identity/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
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
            addIdentityList.clear();
            deleteIdentityList.clear();

            editBtn.classList.remove('edit');
            editBtn.textContent = `✏️ 수정`;

            const keep = container.querySelector('div.mb-8.dictionary-bar');
            container.innerHTML = ''; // 전체 비우고
            if (keep) container.appendChild(keep); // 원하는 div만 다시 붙이기
            getUserIdentityList();
        })
        .catch(error => {
            alert(JSON.parse(error.message).message);
        });
    } else {
        editBtn.classList += ' edit';
        editBtn.textContent = `✏️ 수정 중`;
    }

    const identityContainer = document.querySelectorAll(".identity-container");
    identityContainer.forEach(div => {
        div.addEventListener("click",()=>{
            const isIdentity = div.querySelector(".is-identity").textContent;
            const identityId = div.querySelector(".identity-id").dataset['identityId'];
            const imgTag = div.querySelector("img");
            if(isIdentity.includes("✅")){
                if(!addIdentityList.delete(identityId)){
                    deleteIdentityList.add(identityId);
                }
                div.querySelector(".is-identity").classList = `text-red-500 is-identity`;
                div.querySelector(".is-identity").textContent = `❌ 미보유`;
                imgTag.classList = `rounded-md mb-3 grayscale`;
            } else {
                if(!deleteIdentityList.delete(identityId)){
                    addIdentityList.add(identityId);
                }
                div.querySelector(".is-identity").classList = `text-green-400 is-identity`;
                div.querySelector(".is-identity").textContent = `✅ 보유`;
                imgTag.classList = `rounded-md mb-3`;
            }
        });
    });
}

getUserIdentityList();
editBtn.addEventListener('click',editButton);