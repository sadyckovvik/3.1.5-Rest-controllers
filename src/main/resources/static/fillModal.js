async function getUserDataById(id) {
    const response = await fetch(`/api/users/${id}`);
    return await response.json();
}

async function fillModal(modal) {

    //Обработчик события открытия модального окна
    modal.addEventListener("show.bs.modal", async function (event) {
        console.log(event.relatedTarget);

        const userId = event.relatedTarget.dataset.userId;
        const user = await getUserDataById(userId);

        // Находим тело модального окна
        const modalBody = modal.querySelector(".modal-body");

        // Находим поля ввода для имени пользователя, возраста и пароля
        const usernameInput = modalBody.querySelector("input[data-user-id='username']");
        const ageInput = modalBody.querySelector("input[data-user-id='age']");
        const passwordInput = modalBody.querySelector("input[data-user-id='password']");

        // Заполняем поля ввода данными пользователя
        passwordInput.value = user.password;
        usernameInput.value = user.username;
        ageInput.value = user.age;


        let rolesSelect = HTMLSelectElement;

        // Находим выпадающие списки для удаления и редактирования ролей
        let rolesSelectDelete = modalBody.querySelector("select[data-user-id='allRolesDelete']");
        let rolesSelectEdit = modalBody.querySelector("select[data-user-id='allRolesEdit']");
        let userRolesHTML = "";

        if (rolesSelectDelete !== null) {
            rolesSelect = rolesSelectDelete;
            for (let i = 0; i < user.roles.length; i++) {
                const roleName = user.roles[i].name;
                const roleDisplayName = roleName.substring(5); // Убираем префикс "ROLE_"
                userRolesHTML += `<option value="${roleName}" selected>${roleDisplayName}</option>`;
            }
        } else if (rolesSelectEdit !== null) {
            rolesSelect = rolesSelectEdit;
            const allRoles = ["ROLE_USER", "ROLE_ADMIN"];

            allRoles.forEach(role => {
                const isSelected = user.roles.some(userRole => userRole.name === role);
                const roleDisplayName = role.substring(5); // Убираем префикс "ROLE_"
                userRolesHTML += `<option value="${role}" ${isSelected ? "selected" : ""}>${roleDisplayName}</option>`;
            });
        }

        rolesSelect.innerHTML = userRolesHTML;
    })
}