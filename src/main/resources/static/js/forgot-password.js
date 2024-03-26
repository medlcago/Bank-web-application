// Обрабатываем данные из формы
const forgotPassword = (email) => {
    const formData = new FormData();
    formData.append('email', email);

    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    fetch('forgot-password', {
        method: 'POST',
        headers: {
            'X-XSRF-TOKEN': csrfToken
        },
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.error) {
                document.getElementById('errorMessageContainer').textContent = data.error;
            } else if (data.message) {
                const warningMessageContainer = document.getElementById('warningMessageContainer');
                const warningMessageText = document.getElementById('warningMessageText');

                warningMessageText.textContent = data.message;
                warningMessageContainer.style.display = 'block';
                document.getElementById('errorMessageContainer').textContent = '';
                form.reset();
            }
            const dangerMessageContainer = document.getElementById('dangerMessageContainer');
            if (dangerMessageContainer) {
                dangerMessageContainer.remove();
            }
        })
        .catch(error => {
            console.error(error);
            document.getElementById('errorMessageContainer').textContent = error.message;
        });
};

// Скрываем алерт при нажатии на крестик
const handleCloseAlert = () => {
    const messageContainer = document.getElementById('warningMessageContainer');
    messageContainer.style.display = 'none';
};

// Получаем данные из формы
const handleSubmit = (event) => {
    event.preventDefault();

    const emailInput = document.getElementById('email');
    const email = emailInput.value;

    forgotPassword(email);
};

// Прослушиваем событие нажатия на кнопку отправки формы
const form = document.getElementById('passwordResetForm');
form.addEventListener('submit', handleSubmit);

// Прослушиваем событие нажатия на крестик
const closeAlertButton = document.getElementById('closeAlertButton');
closeAlertButton.addEventListener('click', handleCloseAlert);