<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Отмененные транзакции</title>
    <#include '../common/common.ftlh'>
</head>
<body>
<#include '../common/menu.ftlh'>
<div class="container">
    <h1 class="mt-3 mb-3">Отмененные транзакции</h1>

    <div id="table">
        <table class="table table-striped table-bordered">
            <tr>
                <th scope="col">Отправитель</th>
                <th scope="col">Получатель</th>
                <th scope="col">Сумма</th>
                <th scope="col">Дата Время отмены</th>
                <th scope="col">Комментарий</th>
            </tr>
            <#list transactions as t>
                <tr>
                    <td>${t.fromUser.login}</td>
                    <td>${t.toUser.login}</td>
                    <td>${t.sum}</td>
                    <td>${t.cancelDateTime}</td>
                    <td>${t.cancelComment}</td>
                </tr>
            </#list>
        </table>
    </div>
</body>
</html>