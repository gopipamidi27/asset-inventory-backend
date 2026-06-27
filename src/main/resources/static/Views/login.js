const userid = document.getElementById("username");
const userpassword = document.getElementById("password");
const loginbut = document.getElementById("loginbutt");
const am = document.getElementById("alertmessage");
loginbut.addEventListener("click",(e)=>{
    logincheck();
});
loginbut.addEventListener("keydown",(e)=>{
    if(e.key === "Enter"){
        e.preventDefault();
        logincheck();
    }
});
userpassword.addEventListener("keydown",(e)=>{
    if(e.key === "Enter"){
        e.preventDefault();
        logincheck();
    }
});
async function logincheck() {
    const username = userid.value ;
    const password = userpassword.value ;
    let res = await fetch(`/user/${username}`);
    if(!res.ok){
        const err = "User details not found : "
        message(err,username);
        return ;
    }
    data = await res.json();
    if(data.password != password){
        const err = "invalid user credentials : ";
        message(err,username);
        
    }
    else{
        localStorage.setItem('name',username);
        localStorage.setItem('role',data.userrole);
        window.location.href = "/Views/homepage.html";
    }
}
function message (errmsg,user){
    am.style.display = "flex";
    am.textContent = errmsg+user ;

    setTimeout(() =>{
        am.style.display = "none";
    },2000)
}
