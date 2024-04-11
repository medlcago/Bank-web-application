$(".toggle-button-card").each(function () {
    let button = $(this)
    let cardBody = button.closest(".card-body");
    let cardNumber = cardBody.find(".card-number");
    let cardBalance = cardBody.find(".card-balance");

    let isHidden = localStorage.getItem(button.attr("id")) === "hidden";
    cardNumber.css("display", isHidden ? "none" : "inline");
    cardBalance.css("display", isHidden ? "none" : 'inline');
    button.html(isHidden ? '<i class="fas fa-eye"></i> Показать' : '<i class="fas fa-eye-slash"></i> Скрыть');

    button.click(() => {
        let isHidden = cardNumber.css("display") === "none";
        cardNumber.css("display", isHidden ? "inline" : "none");
        cardBalance.css('display', isHidden ? "inline" : 'none');
        button.html(isHidden ? '<i class="fas fa-eye-slash"></i> Скрыть' : '<i class="fas fa-eye"></i> Показать');

        localStorage.setItem(button.attr("id"), isHidden ? "visible" : "hidden");
    });
});