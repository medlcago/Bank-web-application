document.getElementById("feedbackForm").addEventListener("submit", event => {
    event.preventDefault();

    let form = event.target;
    let formData = new FormData(form);
    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    fetch("/feedback", {
        method: "POST",
        headers: {
            "X-XSRF-TOKEN": csrfToken
        },
        body: formData
    })
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data);
            if (data) {
                if (data.errors) {
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
                }
                else if (data.message) {
                    form.reset();
                    let errorElements = document.querySelectorAll(".error");
                    errorElements.forEach(element => {
                        element.textContent = "";
                    });
                    let successAlert = document.getElementById("successAlert")
                    successAlert.style.display = "block";
                    successAlert.textContent = data.message;
                }
            }
        })
        .catch(error => {
            console.error("Ошибка при отправке формы:", error);
        });
});