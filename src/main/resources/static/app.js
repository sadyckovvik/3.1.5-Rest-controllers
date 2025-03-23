document.addEventListener('DOMContentLoaded', async function () {
    await EditModalHandler();
    await DeleteModalHandler();
    await showUserNameOnNavbar();
    await fillTableWithAllUsers();
});
