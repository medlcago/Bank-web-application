class CardToggler {
    constructor(button) {
        this.button = button;
        this.cardBody = button.closest('.card-body');
        this.cardNumber = this.cardBody.querySelector('.card-number');
        this.cardBalance = this.cardBody.querySelector('.card-balance');
        this.localStorageKey = button.id;

        this.initToggleState();
        this.button.addEventListener("click", this.toggleCardInfo.bind(this));
    }

    initToggleState() {
        const isHidden = localStorage.getItem(this.localStorageKey) === "hidden";
        this.toggleCardVisibility(isHidden);
        this.showButtonText(isHidden);
    }

    toggleCard(isHidden) {
        this.cardNumber.style.display = isHidden ? "inline" : "none";
        this.cardBalance.style.display = isHidden ? "inline" : "none";

    }

    toggleCardVisibility(isHidden) {
        this.cardNumber.style.display = isHidden ? "none" : "inline";
        this.cardBalance.style.display = isHidden ? "none" : 'inline';
    }

    showButtonText(isHidden) {
        this.button.innerHTML = isHidden ? '<i class="fas fa-eye"></i> Показать' : '<i class="fas fa-eye-slash"></i> Скрыть';
    }

    hideButtonText(isHidden) {
        this.button.innerHTML = isHidden ? '<i class="fas fa-eye-slash"></i> Скрыть' : '<i class="fas fa-eye"></i> Показать';
    }

    toggleCardInfo() {
        const isHidden = this.cardNumber.style.display === "none";
        this.toggleCard(isHidden);
        this.hideButtonText(isHidden);
        localStorage.setItem(this.localStorageKey, isHidden ? "visible" : "hidden");
    }
}

const toggleButtons = document.querySelectorAll(".toggle-button-card");
toggleButtons.forEach(button => new CardToggler(button));
