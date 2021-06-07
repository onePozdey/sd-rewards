<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Profile</title>
    <#include '../common/common.ftlh'>
</head>
<body>
<#include '../common/menu.ftlh'>
<div class="container">
    <h1 class="mt-3 mb-3">Профиль ${user.login}</h1>
    <p>
    <div class="font-weight-bold">Характеристики пользователя:</div>
    <#if specs?has_content>
        <#list specs as spec>
            ${spec.name} : ${spec.count}<br>
        </#list>
    <#else>
        Никто еще не оценил пользователя
    </#if>
    </p>
    <div id="table">
        <table class="table table-striped table-bordered">
            <tr>
                <th>Отправитель</th>
                <th>Сумма</th>
                <th>Дата время</th>
                <th>Оценил</th>
            </tr>
            <#list transactions as t>
                <tr>
                    <td>${t.fromUser.login}</td>
                    <td>${t.sum}</td>
                    <td>${t.dateTime}</td>
                    <td>
                        <#if t.specs?has_content>
                            <#list t.specs as s>
                                ${s.name}
                            </#list>
                        </#if>
                    </td>
                </tr>
            </#list>
    </div>
</div>
</body>
</html>