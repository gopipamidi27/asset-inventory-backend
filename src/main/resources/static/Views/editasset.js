const nodelist = document.querySelectorAll('#assetcont input, #assetcont select, #assetcont input[type="file"]');
const savebt = document.getElementById("savedetails");
const sempid = document.getElementById("alert");
const sasttag = document.getElementById("alert1");
const sastsn = document.getElementById("alert2");
const sl = document.getElementById("alert3");
const shf = document.getElementById("alert4");
const alrt = document.getElementById("alertmsg");
const menuitem1 = document.getElementById("menuitem");
const assetsmenuitem = document.getElementById("assetmenuitem");
const uploadfile = document.getElementById("uploadmultiple");
const addasset = document.getElementById("singleentry");
const updateasset = document.getElementById("updateasset");
const tdata = document.getElementById("tabledata");
const hf = document.getElementById("hdf");
const infile = document.getElementById("infile");
const urlstr = window.location.search ;
const urlparam = new URLSearchParams(urlstr);
const assttag = urlparam.get('id');
const username = document.getElementById("userdata");
username.innerHTML = `Welcome ${localStorage.getItem('name')}`;
const userrole = `${localStorage.getItem('role')}`;
const menu1 = document.getElementById("addasset");
const menu3 = document.getElementById("Users");
if(userrole === 'user'){
    menu1?.style.display = 'none';
    menu3?.style.display = 'none';

}
if(userrole === 'admin'){
    menu3?.style.display = 'none';
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
let result = "" ;
const formdata = new FormData();
infile.addEventListener('change',()=>{
    if(infile.files && infile.files.length > 0)
        hf.textContent = infile.files[0].name;
    else
        hf.textContent = "Upload File";
});
async function assets() {
    const res = await fetch(`/inventory/${assttag}`);
    result = await res.json();
    console.log(result);
    nodelist[0].value = result.employeeid;
    nodelist[1].value = result.employee_name;
    nodelist[2].value = result.employee_department;
    nodelist[3].value = result.assettag;
    nodelist[3].disabled = true;
    nodelist[4].value = result.asset_type;
    nodelist[5].value = result.asset_model;
    nodelist[6].value = result.assetserialnumber;
    nodelist[6].disabled = true;
    nodelist[7].value = result.os_info;
    nodelist[8].value = result.location;
    if(result.handover_form)
        hf.innerHTML = `${result.assettag}.pdf`;
   console.log(result);
}
function menuitemview(){
    if(menuitem1.style.display == "flex")
        menuitem1.style.display = "none";
    else
        menuitem1.style.display = "flex";    
}
function assetsmenu(){
    if(assetsmenuitem.style.display == "flex")
        assetsmenuitem.style.display = "none";
    else
        assetsmenuitem.style.display = "flex";
}
function reset(){
    nodelist.forEach(ele =>{
        ele.value = "";
    });
    hf.innerHTML = "upload File";
}

nodelist[0].addEventListener('focus',()=>{
    sempid.innerHTML = ""
});
nodelist[3].addEventListener('focus',()=>{
    sasttag.innerHTML = ""
});
nodelist[6].addEventListener('focus',()=>{
    sastsn.innerHTML = ""
});
nodelist[8].addEventListener('focus',()=>{
    sl.innerHTML = ""
});
nodelist[9].addEventListener('focus',()=>{
    shf.innerHTML = ""
});
function savedata(){
    if(nodelist[0].value == ""){
        sempid.style.display = "inline-block";
        sempid.innerHTML = "Please enter Employee ID";
    }
    if(nodelist[3].value == ""){
        sasttag.style.display = "inline-block";
        sasttag.innerHTML = "Please enter Assettag";
    }
    if(nodelist[6].value == ""){
        sastsn.style.display = "inline-block";
        sastsn.innerHTML = "Please enter Asset serialnumber";
    }
    if(nodelist[8].value == ""){
        sl.style.display = "inline-block";
        sl.innerHTML = "Please enter location";
    }
    
    formdata.append("employee_id",nodelist[0].value);
    formdata.append("employee_name",nodelist[1].value);
    formdata.append("employee_department",nodelist[2].value);
    formdata.append("asset_tag",nodelist[3].value);
    formdata.append("asset_type",nodelist[4].value);
    formdata.append("asset_model",nodelist[5].value);
    formdata.append("asset_serialnumber",nodelist[6].value);
    formdata.append("os_info",nodelist[7].value);
    formdata.append("location",nodelist[8].value);
    if(hf.innerHTML == `${result.assettag}.pdf`){
        const rawdata = atob(result.handover_form);
        const bytenumbers = new Array(rawdata.length);
        for(i = 0; i < rawdata.length; i++){
            bytenumbers[i] = rawdata.charCodeAt(i);
        }
        const bytearray = new Uint8Array(bytenumbers);
        const newfile = new File([bytearray], "handoverform.pdf", { type: 'application/pdf'});
        formdata.append("handover_form", newfile);
    }
    else
        formdata.append("handover_form",infile.files[0]);
    updateassetdetails();

}
reset();
async function updateassetdetails(){
    const stat = await fetch(`/updatedetails/${result.assettag}`,{
                                method: "PUT",
                                body: formdata
    });
    const statmsg = await stat.text();
    if(statmsg == "Details Updated"){
        showStatus(statmsg,"green");
    }
    else
        showStatus(statmsg, "red");
}
function showStatus(message, color) {
    const rs = document.getElementById('result');
    if (rs) {
        rs.style.display = "block";  
        rs.style.color = color;
        rs.innerHTML = message;      
    }
    setTimeout(()=>{
        rs.style.display = "none";
        window.location.href = "manageassets.html";
    },2000);
}