<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Награды</title>
    <#include '../common/common.ftlh'>
</head>
<body>
<#include '../common/menu.ftlh'>
<div class="container">
    <h1 class="mt-3 mb-3">Награды</h1>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Добавление награды</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="title" class="col-form-label">Название</label>
                        <input type="text" class="form-control" id="title" required>
                    </div>
                    <div class="form-group">
                        <label for="desc" class="col-form-label">Описание</label>
                        <textarea class="form-control" id="desc"></textarea>
                    </div>
                    <div class="form-group custom-file">
                        <label for="image" class="col-form-label custom-file-label">Изображение</label>
                        <input type="file" accept="image/*" class="form-control-file custom-file-input" id="image">
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-form-label">Цена</label>
                        <input type="number" min="0" class="form-control" id="price" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary" onclick="addReward()">Создать</button>
                </div>
            </div>
        </div>
    </div>
    <#if user.role = 'SUPER_ADMIN'>
        <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#exampleModal">
            Добавить награду
        </button>
    </#if>
    <table class="table table-striped table-bordered">
        <tr>
            <th scope="col">Изображение</th>
            <th scope="col">Название</th>
            <th scope="col">Описание</th>
            <th scope="col">Цена</th>
            <th scope="col">Действие</th>
        </tr>
        <#list rewards as r>
            <tr>
                <td>
                    <img src="${r.imageUrl}" class="img-fluid rounded mx-auto d-block" alt=""
                         style="max-width: 200px; height: auto">
                </td>
                <td>${r.title}</td>
                <td>${r.description}</td>
                <td>${r.price}</td>
                <td>
                    <#if r.price <= user.balance>
                        <div class="btn btn-primary" onclick="order(${r.id?c})">Заказать</div>
                    <#else>
                        Недостаточно средств
                    </#if>
                </td>
            </tr>
        </#list>
    </table>
</div>
<script>
    function addReward() {
        let image = ($('#image'))[0]['files'][0];
        let fd = new FormData();
        fd.append('image', image);
        fd.append('title', $('#title').val());
        fd.append('price', $('#price').val());
        fd.append('description', $('#desc').val());
        $.ajax({
            url: '/api/rewards',
            method: 'POST',
            data: fd,
            contentType: false,
            processData: false,
            success: () => {
                location.reload()
            },
            error: function () {
                alert("Ошибка");
            }
        })
    }

    function order(id) {
        $.ajax({
            url: '/api/rewards/' + id + '/order',
            method: 'POST',
            success: function () {
                alert("Награда успешно заказана!")
                location.reload()
            },
            error: function () {
                alert("Ошибка");
            }
        })
    }
</script>
</body>
</html>