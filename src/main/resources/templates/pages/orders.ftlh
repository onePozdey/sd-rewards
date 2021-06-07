<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Заказы</title>
    <#include '../common/common.ftlh'>
</head>
<body>
<#include '../common/menu.ftlh'>
<div class="container">
    <h1 class="mt-3 mb-3">Список заказов</h1>
    <table class="table table-striped table-bordered">
        <tr>
            <th scope="col">Награда</th>
            <th scope="col">Дата</th>
            <th scope="col">Получатель</th>
            <th scope="col"></th>
        </tr>
        <#list orders as r>
            <tr>
                <td>${r.reward.title}</td>
                <td>${r.dateTime}</td>
                <td>${r.receiver.login}</td>
                <td>
                    <div class="btn btn-primary btn-sm" onclick="completeOrder(${r.id})">Исполнено</div>
                </td>
            </tr>
        </#list>
    </table>
</div>
<script>
    function completeOrder(id) {
        $.ajax({
            url: '/api/rewards/orders/' + id + '/complete',
            method: 'POST',
            success: () => {
                location.reload()
            }
        })
    }
</script>
</body>
</html>