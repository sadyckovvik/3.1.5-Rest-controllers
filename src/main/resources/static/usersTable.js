async function getAllUsers() {
    const response = await fetch("/api/users")
    return await response.json();
}

async function dataAboutCurrentUser() {
    const response = await fetch("/api/user")
    return await response.json();
}

async function fillTableWithAllUsers() {
    const usersTable = document.getElementById("usersTableId");
    const allUsers = await getAllUsers();

    let usersTableHTML = "";
    allUsers.forEach(user => {
        usersTableHTML +=
            `
            <tr>
                <td>${user.username}</td>
                <td>${user.age}</td>
                <td>${user.password}</td>
                <td>${user.roles.map(role => role.name.substring(5).concat(" ")).toString().replaceAll(",", "")}</td>
             <td>
                    <button class="btn btn-info"
                            data-bs-toggle="modal"
                            data-bs-target="#editModal"
                            data-user-id="${user.id}">
                        Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger"
                            data-bs-toggle="modal"
                            data-bs-target="#deleteModal"
                            data-user-id="${user.id}">                     
                        Delete</button>
                </td>
            </tr>`;
    });
    usersTable.innerHTML = usersTableHTML;

}

async function showUserNameOnNavbar() {
    const currentUserNavbar = document.getElementById("currentUserNavbar");
    const currentUser = await dataAboutCurrentUser();
    currentUserNavbar.innerHTML =
        `<strong>${currentUser.username}</strong>
                 with roles: 
                 ${currentUser.roles.map(role => role.name.substring(5).concat(" ")).toString().replaceAll(",", "")}`;
}
