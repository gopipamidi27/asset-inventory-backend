const username = document.getElementById("userdata");
username.innerHTML = `Welcome ${localStorage.getItem('name')}`;
const menuitem1 = document.getElementById("menuitem");
const assetsmenuitem = document.getElementById("assetmenuitem");
const uploadfile = document.getElementById("uploadmultiple");
const addasset = document.getElementById("singleentry");
const updateasset = document.getElementById("updateasset");
const userrole = localStorage.getItem('role');
const menu1 = document.getElementById("addasset");
const menu3 = document.getElementById("Users");
if(userrole === 'user'){
    menu1?.style.setProperty('display','none');
    menu3?.style.setProperty('display','none');

}
if(userrole === 'admin'){
    menu3?.style.setProperty('display','none');
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