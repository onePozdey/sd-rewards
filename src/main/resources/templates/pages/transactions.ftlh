<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Транзакции</title>
    <#include '../common/common.ftlh'>
</head>
<body>
<#include '../common/menu.ftlh'>
<div class="container">
    <h1 class="mt-3 mb-3">Транзакции</h1>

    <div class="modal fade" id="sendModal" tabindex="-1" role="dialog" aria-labelledby="sendModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="sendModalLabel">Отправить деньги</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-form-label">Получатель</label>
                        <select id="send-to" class="form-control">
                            <#list users as u>
                                <#if u.id != user.id>
                                    <option value="${u.id}">${u.login}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Характеристики</label>
                        <#list specs as spec>
                            <div class="form-check" id="specs-table">
                                <input class="form-check-input" name="spec" type="checkbox" value="${spec.id}"
                                       id="spec${spec.id}">
                                <label class="form-check-label" for="spec${spec.id}">
                                    ${spec.name}
                                </label>
                            </div>
                        </#list>
                    </div>
                    <div class="form-group">
                        <label for="send-sum" class="col-form-label">Количество</label>
                        <input type="number" min="1" class="form-control" id="send-sum">
                    </div>
                    <div class="form-group">
                        <label for="task-comment" class="col-form-label">Задача</label>
                        <input type="text" class="form-control" id="task-comment">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary" onclick="sendMoney()">Отправить</button>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#sendModal">
        Отправить монеты
    </button>

    <div class="modal fade" id="transactionCancelModal" tabindex="-1" role="dialog"
         aria-labelledby="transactionCancelModal"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="transactionCancelModal">Отменить транзакцию</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="cancel-comment" class="col-form-label">Комментарий</label>
                        <input type="text" class="form-control" id="cancel-comment">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary" onclick="cancelTransaction()">Отменить транзакцию
                    </button>
                </div>
                <input type="hidden" id="in-modal-id-hidden-input">
            </div>
        </div>
    </div>


    <div id="table">
        <table class="table table-striped table-bordered">
            <tr>
                <th scope="col">Отправитель</th>
                <th scope="col">Получатель</th>
                <th scope="col">Сумма</th>
                <th scope="col">Дата Время</th>
                <th scope="col">Задача</th>
                <th scope="col">Статус</th>
                <#if user.role == 'ADMIN' || user.role = 'SUPER_ADMIN'>
                    <th>Действие</th>
                </#if>
            </tr>
            <#list transactions as t>
                <tr>
                    <td><a href="/user/${t.fromUser.id}" class="text-dark">${t.fromUser.login}</a></td>
                    <td><a href="/user/${t.toUser.id}" class="text-dark">${t.toUser.login}</a></td>
                    <td>${t.sum}</td>
                    <td>${t.dateTime}</td>
                    <td>
                        <#if t.taskComment??>
                            <a href="https://phab.shire.local/${t.taskComment}">Ссылка</a>
                        </#if>
                    </td>
                    <td>
                        <#if t.status == 'SUCCESS'>
                            Проведена
                        </#if>
                        <#if t.status == 'CANCELED'>
                            <a data-toggle="tooltip" data-html="true"
                               title="Дата отмены: ${t.cancelDateTime}<br>Комментарий: ${t.cancelComment!""}">
                                Отменена
                            </a>
                        </#if>
                    </td>
                    <#if user.role == 'ADMIN' || user.role = 'SUPER_ADMIN'>
                        <td>
                            <#if t.status == 'SUCCESS'>
                                <div class="btn btn-primary btn-sm" data-id="${t.id?c}" data-toggle="modal"
                                     data-target="#transactionCancelModal">
                                    Отменить
                                </div>
                            </#if>
                        </td>
                    </#if>
                </tr>
            </#list>
        </table>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#transactionCancelModal").on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget);
            let id = button.attr('data-id');
            $("#in-modal-id-hidden-input").val(id);
        });
    })

    function sendMoney() {
        let specsIds = [];

        $("input:checkbox[name=spec]:checked").each(function () {
            specsIds.push($(this).val());
        });

        let sum = $('#send-sum').val();
        let receiverId = $('#send-to').val();
        let task = $('#task-comment').val();
        $.ajax({
            url: '/api/balance/send',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                receiverId: receiverId,
                sum: sum,
                taskComment: task,
                specsIds: specsIds
            }),
            success: function () {
                alert("Успешно отправлено");
                location.reload();
            },
            error: function () {
                alert("Ошибка");
            }
        })
    }

    function cancelTransaction() {
        let id = $("#in-modal-id-hidden-input").val();
        let comment = $('#cancel-comment').val();
        $.ajax({
            url: '/api/transactions/' + id + '/cancel',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                comment: comment
            }),
            success: function () {
                location.reload();
            },
            error: function () {
                alert("Ошибка");
            }
        })
    }
</script>
</body>

</html>