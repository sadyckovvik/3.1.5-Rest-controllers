document.addEventListener('DOMContentLoaded', function () {
    // Назначаем обработчик события на форму
    const addUserForm = document.getElementById("addNewUser");
    addUserForm.addEventListener("submit", handleAddUser);
});


// Обработчик события отправки формы
async function handleAddUser(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    // Получаем данные из формы
    const username = document.getElementById("usernameNew").value;
    const age = document.getElementById("ageNew").value;
    const password = document.getElementById("passwordNew").value;
    const roles = Array.from(document.getElementById("rolesNew").selectedOptions)
        .map(option => ({ name: option.value }));

    // Отправляем данные на сервер
    await addNewUser(username, age, password, roles);

    // Очищаем форму после успешного добавления
    document.getElementById("addNewUser").reset();

    // Переход на вкладку с таблицей пользователей и обновление таблицы
    document.querySelector('#admin-users-table-tab').click();
    await fillTableWithAllUsers();

}

// Функция для отправки данных нового пользователя на сервер
async function addNewUser(username, age, password, roles) {
    try {
        const response = await fetch("/api/users", {
            method: "POST",
            body: JSON.stringify({
                username: username,
                age: age,
                password: password,
                roles: roles
            }),
            headers: {
                "Content-Type": "application/json",
            },
        });

        // Если ответ не успешный (например, 400 BAD_REQUEST)
        if (!response.ok) {
            const errorData = await response.json(); // Парсим JSON с ошибкой
            throw new Error(errorData.message); // Бросаем ошибку с сообщением от сервера
        }

        return await response.json(); // Возвращаем данные, если запрос успешен
    } catch (error) {
        console.error("Ошибка:", error.message);
        alert(error.message); // Показываем сообщение об ошибке пользователю
        throw error; // Пробрасываем ошибку дальше
    }
}
