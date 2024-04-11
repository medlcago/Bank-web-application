$('#createAccountBtn').click(event => {
    event.preventDefault();

    let cardType = $('#cardType').val();
    let currencyType = $('#currencyType').val();

    $.ajax({
        url: "api/v1/create-account",
        method: "POST",
        data: {
            cardType: cardType,
            currencyType: currencyType
        },
        success: response => {
            console.log(response);
            let successMessage = response.message;
            $("#createAccountErrorAlert").hide();
            $("#createAccountSuccessAlert").text(successMessage).show();
            $("#createAccountBtn").hide();
            $("#createAccountForm").hide()
            $("#closeCreateAccountModal").click(() => {
                location.reload();
            })
        },
        error: (xhr, status, error) => {
            let errorMessage = xhr.responseJSON.message;
            $("#createAccountSuccessAlert").hide();
            $("#createAccountErrorAlert").text(errorMessage).show();
            $("#createAccountBtn").hide();
            $("#createAccountForm").hide()
        }
    });
});