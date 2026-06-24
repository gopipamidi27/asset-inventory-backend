const fil = document.querySelectorAll('#ifbody1 input[type="file"]');
async function uploadinputfile(){
    const fileinput = document.getElementById("inputfile");
    const rs = document.getElementById("result");
    if(!fileinput || fileinput.isDefaultNamespace.length === 0){
        alert("please select file");
        return ;
    }
    const rawfile = fileinput.files[0];
    const formData = new FormData();
    formData.append('file',rawfile);
    try{
        const res = await fetch('/uploaddetails/',{
                    method: 'POST',
                    body: formData
        });
        const resulttext = await res.text();
        if(!res.ok){
            showStatus(`Server Error (${response.status}): ${resultText}`, "red");
            
            return;
        }
        if(resulttext === "Details uploaded successfully"){
            showStatus(resulttext, "green");
        }
        else{
            showStatus(resulttext, "red");
        }
    }catch (error) {
        console.error("Upload Error:", error);
        showStatus("Network error: Could not reach the server.", "red");
    }
}
function showStatus(message, color) {
    const rs = document.getElementById('result');
    if (rs) {
        rs.style.display = "block";
        rs.style.color = color;
        rs.innerHTML = message;
        fil[0].value = "";       
    }
}