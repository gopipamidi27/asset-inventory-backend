let st;
const un = document.getElementById("username");
const np1 = document.getElementById("newpassword1");
const np2 = document.getElementById("newpassword2");
const rs = document.getElementById("resetstatus"); 
const bs = document.getElementById("submit");
function passcheck (){
    event.preventDefault();
    clearTimeout(st);
    rs.innerHTML = "" ;
    rs.style.display = "block";
    const pt = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\W)[a-zA-Z0-9\W]+$/
    let comp = pt.test(np1.value);
    if(un.value == ""){
        rs.innerHTML = "Please enter UserID "
    }
    else if(np1.value.length < 8){
      rs.innerHTML = "Length should be greater then 8" ;
      np1.value = "";
      np2.value = "";
    }
    else if(!comp){
        rs.innerHTML = "Password must include uppercase, lowercase, numbers, and symbols.";
        np1.value = "";
        np2.value = "";
    }
    else if(np1.value != np2.value){
        rs.innerHTML = "Passwprd and Re-entered password not matched" ;
        np1.value = "";
        np2.value = "";
    }
    else{
        const jsobj = {"password":`${np1.value}`};
        updatepassword(un.value,jsobj);
         }

    st = setTimeout(() => {
        rs.style.display = "none";
    }, 3000);
}
async function updatepassword(id,userdata) {
    const url = `/updateuserdetails/${id}`;
    console.log(url);
    try{
    const res = await fetch(url, {
                method: 'PATCH',
                headers: {
                    'Content-Type':'application/json',
                },
                body: JSON.stringify(userdata)
    });
    rs.style.display = "flex";
    if(!res.ok){
            rs.style.color = "red";
            rs.innerHTML = "Id not found"
            setTimeout(() => {rs.style.display = "none"},3000);
            un.value = "";
            np1.value = "";
            np2.value = "";
            return ;
    }
    const result = await res.text();
    rs.style.color = "green";
    rs.innerHTML = "Password Updated successfully! Redirecting...";
    setTimeout(()=> {window.location.href = "C:\Users\GOPI\Documents\workspace-spring-tools-for-eclipse\inventory-my project\AssetInventory\src\main\resources\static\index.html";}, 2000);
    return;
} catch(error){
    setTimeout(() => {rs.innerHTML = "Update failed"},3000);
}
}