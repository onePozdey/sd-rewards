<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand text-white">SD Rewards</a>
    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/profile">Профиль</a>
            </li>
            <#if user??>
                <#if user.role == 'USER'>
                    <li class="nav-item">
                        <a class="nav-link" href="/transactions">Транзакции</a>
                    </li>
                </#if>
                <#if user.role == 'ADMIN' || user.role == 'SUPER_ADMIN'>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Транзакции
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="/transactions">Все</a>
                            <a class="dropdown-item" href="/canceled-transactions">Отмененные</a>
                        </div>
                    </li>
                </#if>
            </#if>
            <li class="nav-item">
                <a class="nav-link" href="/rewards">Награды</a>
            </li>
            <#if user??>
                <#if user.role == 'SUPER_ADMIN'>
                    <li class="nav-item">
                        <a class="nav-link" href="/specs">Характеристики</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/orders">Заказы</a>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/my-orders">Заказы</a>
                    </li>
                </#if>
            </#if>
        </ul>
        <span id="balance-info" class="navbar-text text-white"></span>
    </div>
</nav>