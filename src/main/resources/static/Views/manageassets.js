const username = document.getElementById("userdata");
username.innerHTML = `Welcome ${localStorage.getItem('name')}`;
const menuitem1 = document.getElementById("menuitem");
const assetsmenuitem = document.getElementById("assetmenuitem");
const uploadfile = document.getElementById("uploadmultiple");
const addasset = document.getElementById("singleentry");
const updateasset = document.getElementById("updateasset");
const tdata = document.getElementById("tabledata");
const searchkeyword = document.getElementById("searching");
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
assets();
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
let result = "";
async function assets() {
    const res = await fetch(`/inventorylist/`);
    result = await res.json();
    resultdata(result);    
}
function resultdata(result){
    tdata.innerHTML = "";
    result.forEach(ele => {
        const row = document.createElement('tr');
        row.id = `row-${ele.assettag}`;
        let hf = "";
        if(ele.handover_form){
            hf = `<a href = "/downloadhandover/${ele.assettag}" target ="_blank">${ele.employeeid}</a>`;
        }
        else 
            hf = "No Handoveform";
        row.innerHTML = `
        <td>${ele.employeeid||""}</td>
        <td>${ele.employee_name || ""}</td>
        <td>${ele.employee_department || ""}</td>
        <td>${ele.assettag || ""}</td>
        <td>${ele.asset_type || ""}</td>
        <td>${ele.asset_model || ""}</td>
        <td>${ele.assetserialnumber || ""}</td>
        <td>${ele.os_info || ""}</td>
        <td>${ele.location || ""}</td>
        <td>${hf}</td>
        ${userrole === 'admin' || userrole === 'master' ?
            <td><button class = "edit" onclick = "editasset('${ele.assettag}')">Edit</button></td>: ''}
        `;
        tdata.appendChild(row);
    });  

}
searchkeyword.addEventListener('input', (event)=>{
    const keyword = searchkeyword.value.toLowerCase().trim();
    const filteredresult = result.filter(ele =>{
        const empid = (ele.employeeid || "").toLowerCase();
        const empname = (ele.employee_name || "").toLowerCase();
        const asttag = (ele.assettag || "").toLowerCase();
        const asttype = (ele.asset_type || "").toLowerCase();
        const astsn = (ele.assetserialnumber || "").toLowerCase();
        const location = (ele.location || "").toLowerCase();
        return empid.includes(keyword) || empname.includes(keyword) || asttag.includes(keyword) || asttype.includes(keyword) || 
               astsn.includes(keyword) ||
               location.includes(keyword);
    });
    resultdata(filteredresult);
});
function editasset(id){
    const row = document.getElementById(`row-${id}`);
    const cell = row.getElementsByTagName(`td`);
    console.log(cell[1].innerText);
    window.location.href = `editasset.html?id=${id}`;
}
function exportdata(){
    fetch(`/downloadinventorylist/`);
}