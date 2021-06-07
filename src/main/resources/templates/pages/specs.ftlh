<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Характеристики</title>
    <#include '../common/common.ftlh'>
</head>
<body>
<#include '../common/menu.ftlh'>
<div class="container">
    <h1 class="mt-3 mb-3">Характеристики</h1>

    <div class="modal fade" id="specModal" tabindex="-1" role="dialog" aria-labelledby="specModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="specModalLabel">Добавление характеристики</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="spec-name" class="col-form-label">Название</label>
                        <input type="text" class="form-control" id="spec-name">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary" onclick="addSpec()">Добавить</button>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#specModal">
        Добавить характеристику
    </button>

    <table class="table table-striped table-bordered">
        <tr>
            <th>Название</th>
        </tr>
        <#list specs as s>
            <tr>
                <td>${s.name}</td>
            </tr>
        </#list>
    </table>
</div>
</body>
<script>
    function addSpec() {
        let name = $('#spec-name').val();

        $.ajax({
            url: '/api/transactions/specs',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                name : name
            }),
            success: function () {
                alert("Успешно добавлена");
                location.reload();
            },
            error: function () {
                alert("Ошибка");
            }
        })
    }
</script>
</html>