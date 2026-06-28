const username = document.getElementById("userdata");
username.innerHTML = `Welcome ${localStorage.getItem('name')}`;
const menuitem1 = document.getElementById("menuitem");
const assetsmenuitem = document.getElementById("assetmenuitem");
const uploadfile = document.getElementById("uploadmultiple");
const addasset = document.getElementById("singleentry");
const updateasset = document.getElementById("updateasset");
const status = document.getElementById("status");
const items = document.querySelectorAll('#userinformation input, #userinformation select');
const url = window.location.search;
const urlparam = new URLSearchParams(url);
const uid = urlparam.get('id');
if(uid != null){
    edit(uid);
}
async function edit(uid){
    const res = await fetch(`/user/${uid}`);
    const result = await res.json();
    items[0].value = result.employee_id;
    items[0].disabled = true;
    items[1].value = result.employee_name;
    items[4].value = result.userrole;
}
uploadfile.addEventListener('click',()=>{
    window.location.href = "uploadmultiple.html";
    menuitem1.style.display = "none";
});
addasset.addEventListener('click',()=>{
    window.location.href = "singleentry.html";
    menuitem1.style.display = "none";
});
function menuitemview(e){
    e.stopPropagation();
    if(menuitem1.style.display == "flex")
        menuitem1.style.display = "none";
    else{
        menuitem1.style.display = "flex";    
        assetsmenuitem.style.display = "none";
    }
}
function assetsmenu(e){
    e.stopPropagation();
    if(assetsmenuitem.style.display == "flex")
        assetsmenuitem.style.display = "none";
    else{
        assetsmenuitem.style.display = "flex";
        menuitem1.style.display = "none";
    }
}
window.addEventListener('click', ()=>{
    if(menuitem1.style.display == "flex")
        menuitem1.style.display = "none";
    if(assetsmenuitem.style.display == "flex")
        assetsmenuitem.style.display = "none";
});
const formdata = new FormData();
async function saveuser(){
    formdata.append("employee_id",items[0].value);
    formdata.append("employee_name",items[1].value);
    formdata.append("password",items[2].value);
    formdata.append("userrole",items[4].value);
    try{
    const res = await fetch(`/saveuser/`,{
        method: "POST",
        body: formdata
    });
    const result = await res.text();
    if(result === "user created successfully"){
        showstatus(result,"green");
    }
    else
        showstatus(result,"red");
}catch(error){
        status.innerHTML = "failed";
}
}
function showstatus(res,color){
    status.style.color = color;
    status.innerHTML = res ;
    setTimeout(()=>{
        status.style.display = "none";
        window.location.href = "homepage.html";
    },500);
}