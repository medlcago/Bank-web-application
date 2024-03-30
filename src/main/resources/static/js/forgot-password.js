class ForgotPasswordForm {
    constructor(formId, messageContainers = {}) {
        this.form = document.getElementById(formId);
        this.errorMessagesContainer = document.querySelectorAll(messageContainers.error);
        this.warningMessageContainer = document.getElementById(messageContainers.warning);
        this.warningMessageText = document.getElementById("warningMessageText");
        this.fetchUrl = 'forgot-password';

        this.form.addEventListener("submit", this.handleSubmit.bind(this));
        const closeAlertButton = document.getElementById(messageContainers.closeButton);
        closeAlertButton.addEventListener("click", this.handleCloseAlert.bind(this));
    }

    handleSubmit(event) {
        event.preventDefault();

        const email = this.form.querySelector('input[name="email"]').value;

        this.sendRequest(email)
            .then(() => {
                console.log("Request successful");
            })
            .catch((error) => {
                console.error('Request failed:', error);
            });
    }

    async sendRequest(email) {
        try {
            const formData = new FormData();
            formData.append("email", email);

            const csrfToken = document.querySelector('input[name="_csrf"]').value;

            const response = await fetch(this.fetchUrl, {
                method: "POST",
                headers: {
                    "X-XSRF-TOKEN": csrfToken
                },
                body: formData
            });

            const data = await response.json();


            if (data.errors) {
                console.log(data.errors);
                for (let key in data.errors) {
                    if (data.errors.hasOwnProperty(key)) {
                        let errorMessage = data.errors[key];
                        let errorElement = document.getElementById(key + "Error");
                        let inputElement = document.getElementById(key);
                        if (errorElement && inputElement) {
                            errorElement.textContent = errorMessage;
                            inputElement.classList.add("is-invalid");
                        }
                    }
                }
            } else if (data.message) {
                console.log(data);
                this.showSuccessMessage(data.message);
            }
            this.removeDangerMessage();

        } catch (error) {
            console.error("Ошибка при отправке формы:", error);
        }
    }

    showSuccessMessage(message) {
        this.warningMessageText.textContent = message;
        this.warningMessageContainer.style.display = "block";
        this.errorMessagesContainer.forEach(element => {
            element.textContent = "";
        });

        const inputElements = this.form.querySelectorAll('input[name="email"]');
        inputElements.forEach(element => {
            element.classList.remove("is-invalid");
            element.classList.add("is-valid");
        });
    }

    handleCloseAlert() {
        this.warningMessageContainer.style.display = "none";
    }

    removeDangerMessage() {
        const dangerMessageContainer = document.getElementById("dangerMessageContainer");
        if (dangerMessageContainer) {
            dangerMessageContainer.remove();
        }
    }
}

const passwordResetForm = new ForgotPasswordForm("passwordResetForm", {
    error: ".invalid-feedback",
    warning: "warningMessageContainer",
    closeButton: "closeAlertButton"
});
