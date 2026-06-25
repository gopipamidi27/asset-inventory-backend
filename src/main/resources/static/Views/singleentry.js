const nodelist = document.querySelectorAll('#assetcont input, #assetcont select, #assetcont input[type="file"]');
const savebt = document.getElementById("savedetails");
const sempid = document.getElementById("alert");
const sasttag = document.getElementById("alert1");
const sastsn = document.getElementById("alert2");
const sl = document.getElementById("alert3");
const shf = document.getElementById("alert4");
function reset(){
    nodelist.forEach(ele =>{
        ele.value = "";
    });
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


async function savedata(){
    if(nodelist[0].value == "")
        sempid.innerHTML = `Please enter Employee ID`;
    if(nodelist[3].value == "")
        sasttag.innerHTML = `Please enter AssetTag`;
    if(nodelist[6].value == "")
        sastsn.innerHTML = `Please enter Asset SerialNumber`;
    if(nodelist[8].value == "")
        sl.innerHTML = `Please select Location`;
    if(nodelist[9].value == "")
        shf.innerHTML = `Please select HandoverForm`;
    
    const formdata = new FormData();
    const inputfile = document.getElementById("infile");
    formdata.append("employee_id", nodelist[0].value);
    formdata.append("employee_name", nodelist[1].value);
    formdata.append("employee_department",nodelist[2].value);
    formdata.append("asset_tag",nodelist[3].value);
    formdata.append("asset_type",nodelist[4].value);
    formdata.append("asset_model",nodelist[5].value);
    formdata.append("asset_serialnumber",nodelist[6].value);
    formdata.append("os_info",nodelist[7].value);
    formdata.append("location",nodelist[8].value);
    formdata.append("handover_form",inputfile.files[0]);
    try{
    const res = await fetch(`/savedetails/`,{
                  method: "POST",
                  body: formdata

    });
    const result = await res.text();
    if(result === "Details saved"){
        showStatus(result,"green");
    }
    else{
        showStatus(result,"red");
    }    
}catch(error){
    rs.innerHTML = "Failed";
}
reset();
function showStatus(message, color) {
    const rs = document.getElementById('result');
    if (rs) {
        rs.style.display = "block";
        rs.style.color = color;
        rs.innerHTML = message;      
    }
    setTimeout(()=>{
        rs.style.display = "none";
        window.location.href = "homepage.html";
    },500);
}
}