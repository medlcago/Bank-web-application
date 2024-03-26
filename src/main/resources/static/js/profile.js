// Скрыть\Показать баланс и номер счета
const toggleButtons = document.querySelectorAll('.toggle-button-card');

toggleButtons.forEach(button => {
    const cardBody = button.closest('.card-body');
    const cardNumber = cardBody.querySelector('.card-number');
    const cardBalance = cardBody.querySelector('.card-balance');

    const isHidden = localStorage.getItem(button.id) === 'hidden';
    cardNumber.style.display = isHidden ? 'none' : 'inline';
    cardBalance.style.display = isHidden ? 'none' : 'inline';
    button.innerHTML = isHidden ? '<i class="fas fa-eye"></i> Показать' : '<i class="fas fa-eye-slash"></i> Скрыть';


    button.addEventListener('click', function () {
        const isHidden = cardNumber.style.display === 'none';
        cardNumber.style.display = isHidden ? 'inline' : 'none';
        cardBalance.style.display = isHidden ? 'inline' : 'none';

        button.innerHTML = isHidden ? '<i class="fas fa-eye-slash"></i> Скрыть' : '<i class="fas fa-eye"></i> Показать';

        localStorage.setItem(button.id, isHidden ? 'visible' : 'hidden');
    });
});
