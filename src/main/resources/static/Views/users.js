const user = document.getElementById("tbody");
viewusers();
let result = "";
async function viewusers(){
    const res = await fetch("/userslist/");
    result = await res.json();
    resultdata(result);
}
function resultdata(result){
    user.innerHTML= "";
    result.forEach(element => {
        const row = document.createElement('tr');
        row.id = `row-${element.employee_id}`;
       row.innerHTML=` <td>element.employee_id</td>
        <td>element.employee_name</td>
        <td>element.userrole</td>
        `;
        user.appendChild(row);
    });
}