<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login page</title>
    <#include 'common/common.ftlh'>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body class="text-center">

<form class="form-signin" action="/login" method="post">
    <h1 class="h3 m-5 font-weight-normal">Вход</h1>

    <label for="inputEmail" class="sr-only">Логин</label>
    <input type="text" name="username" id="inputEmail" class="form-control" placeholder="Логин" required="" autofocus="">

    <label for="inputPassword" class="sr-only">Пароль</label>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Пароль" required="">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
</form>
</body>
</html>