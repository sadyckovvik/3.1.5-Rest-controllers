async function sendDataEditUser(user, id) {
    const response = await fetch(`/api/users?id=${id}`, {
        method: "PUT",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    });

    if (!response.ok) {
        throw new Error("Ошибка при обновлении пользователя");
    }
    return await response.json();
}

const modalEdit = document.getElementById("editModal");
let userIdToEdit = null;

// Обработчик открытия модального окна
modalEdit.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    userIdToEdit = button.getAttribute('data-user-id');
});

async function EditModalHandler() {
    await fillModal(modalEdit);
}

// Обработчик отправки формы
modalEdit.addEventListener("submit", async function (event) {
    event.preventDefault();

    // Получаем выбранные роли из выпадающего списка
    const rolesSelected = document.getElementById("allRolesEdit");
    const roles = Array.from(rolesSelected.selectedOptions).map(option => ({ name: option.value }));

    // Формируем объект пользователя
    let user = {
        id: userIdToEdit,
        username: document.getElementById("usernameEdit").value,
        age: document.getElementById("ageEdit").value,
        password: document.getElementById("passwordEdit").value,
        roles: roles // Передаем массив объектов с ролями
    };

    // Отправляем данные на сервер
    await sendDataEditUser(user, userIdToEdit);

    // Закрываем модальное окно
    const modalBootstrap = bootstrap.Modal.getInstance(modalEdit);
    modalBootstrap.hide();

    // Обновляем таблицу пользователей
    await fillTableWithAllUsers();
});


