async function deleteUser(id) {
    await fetch(`/api/users?id=${id}`, {method: "DELETE"});
}

const modalDelete = document.getElementById("deleteModal");
let userIdToDelete = null;

// Обработчик открытия модального окна
modalDelete.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    userIdToDelete = button.getAttribute('data-user-id');
});

async function DeleteModalHandler() {
    await fillModal(modalDelete);
}

const formDelete = document.getElementById("formDeleteUser");
// Обработчик отправки формы
formDelete.addEventListener("submit", async function (event) {
    event.preventDefault();

    // Отправляем данные на сервер
    await deleteUser(userIdToDelete);

    // Закрываем модальное окно
    const modalBootstrap = bootstrap.Modal.getInstance(modalDelete);
    modalBootstrap.hide();

    // Обновляем таблицу пользователей
    await fillTableWithAllUsers();
});