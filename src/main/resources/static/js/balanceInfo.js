$(document).ready(function () {
    showBalance();
    setInterval(showBalance, 5000);
})

function showBalance() {
    $.ajax({
        method : 'GET',
        url : '/api/balance/info',
        success : function (data) {
            $('#balance-info').empty()
                .append('Полученный баланс ' + data.balance + ', Дарственный баланс ' + data.giftBalance);
        }
    })
}

$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})