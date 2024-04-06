$("#transferForm").on("submit", event => {
    event.preventDefault();

    let form = event.target;
    let formData = new FormData(form);
    const csrfToken = $('input[name="_csrf"]').val();

    $.ajax({
        url: "api/v1/transfer-funds",
        method: "POST",
        headers: {
            "X-XSRF-TOKEN": csrfToken
        },
        data: formData,
        processData: false,
        contentType: false,
        success: data => {
            console.log(data);
            if (data.message) {
                $(".invalid-feedback").text("");

                $("input.transfer-input").removeClass("is-invalid").addClass("is-valid");

                $("#successTransferAlert").css("display", "block").text(data.message)
            }
        },
        error: (xhr, status, error) => {
            console.error("Ошибка при отправке формы:", error);
            if (xhr.responseJSON && xhr.responseJSON.errors) {
                let data = xhr.responseJSON;
                for (let key in data.errors) {
                    if (data.errors.hasOwnProperty(key)) {
                        let errorMessage = data.errors[key];
                        let errorElement = $("#" + key + "Error");
                        let inputElement = $("#" + key);
                        if (errorElement.length && inputElement.length) {
                            errorElement.text(errorMessage);
                            inputElement.removeClass("is-valid").addClass("is-invalid");
                        }
                    }
                }
            }
        }
    });
});