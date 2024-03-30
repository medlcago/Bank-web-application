class ForgotPasswordForm {
    constructor(formId, messageContainers = {}) {
        this.form = document.getElementById(formId);
        this.errorMessageContainer = document.getElementById(messageContainers.error || "errorMessageContainer");
        this.warningMessageContainer = document.getElementById(messageContainers.warning || "warningMessageContainer");
        this.warningMessageText = document.getElementById("warningMessageText");
        this.fetchUrl = 'forgot-password';

        this.form.addEventListener("submit", this.handleSubmit.bind(this));
        const closeAlertButton = document.getElementById(messageContainers.closeButton || "closeAlertButton");
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

            if (data.error) {
                this.errorMessageContainer.textContent = data.error;
            } else if (data.message) {
                this.showSuccessMessage(data.message);
                this.form.reset();
            }

            this.removeDangerMessage();
        } catch (error) {
            console.error(error);
            this.errorMessageContainer.textContent = error.message;
        }
    }

    showSuccessMessage(message) {
        this.warningMessageText.textContent = message;
        this.warningMessageContainer.style.display = "block";
        this.errorMessageContainer.textContent = "";
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
    error: "errorMessageContainer",
    warning: "warningMessageContainer",
    closeButton: "closeAlertButton"
});
