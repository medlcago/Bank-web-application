$("#topUpAccountModal").on("show.bs.modal", function (event) {
    let button = $(event.relatedTarget)
    let currency = button.data("currency")
    let modal = $(this)
    modal.find("#topUpAccountModalLabel").text(`Пополнение счета (${currency})`)
    modal.find("#amountInput").attr("placeholder", `500 ${currency}`)

    $("#topUpAccountBtn").on("click", function (event) {
        event.preventDefault();

        let amount = $("#amountInput").val()
        if (!amount){
            $("#topUpAccountErrorAlert").text("Пожалуйста, введите сумму").show()
            return;
        }

        $.ajax({
            url: "api/v1/top-up-account",
            method: "POST",
            data: {
                currency: currency,
                amount: amount
            },
            success: response => {
                console.log(response);
                let successMessage = response.message;
                $("#topUpAccountErrorAlert").hide();
                $("#topUpAccountSuccessAlert").text(successMessage).show();
                $("#topUpAccountBtn").hide();
                $("#topUpAccountForm").hide()
                $("#closeTopUpAccountModal").click(() => {
                    location.reload();
                })
            },
            error: (xhr, status, error) => {
                let errorMessage = xhr.responseJSON.message;
                $("#topUpAccountSuccessAlert").hide();
                if (errorMessage && errorMessage.length > 0) {
                    $("#topUpAccountErrorAlert").text(errorMessage).show();
                } else {
                    let errorMessage = xhr.responseJSON.errors.amount
                    if (errorMessage && errorMessage.length > 0){
                        $("#topUpAccountErrorAlert").text("Пожалуйста, введите корректную сумму").show();
                    }

                }
                $("#closeTopUpAccountModal").click(() => {
                    location.reload();
                })
            }
        });
    })
})