<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="EstiloLogin.css">
    </head>
   <body>

        <div>
            <form>
                <h2>Login</h2>
                <label for="username" data-info="Ingresa tu nombre de usuario">
                    Nombre de Usuario
                    <span>admin</span>
                </label>
                <input type="text" id="username" name="username">

                <label for="password" data-info="Ingresa tu contraseña">
                    Contraseña
                    <span>123456</span>
                </label>
                <input type="password" id="password" name="password">

                <div class="remember">
                    <div class="left-section">
                        <input type="checkbox" id="remember" name="remember">
                        <label for="remember">Recordar</label>
                    </div>
                    <div class="right-section">
                        <p>Olvidé Contraseña</p>
                    </div>
                </div>
                <button type="button" class="btn1" onclick="login()">Iniciar Sesión</button>
            </form>
        </div>
        <div class="footer">
            <div class="copyright text-center my-auto">
                <span>Copyright &copy; Andrle</span>
            </div>
        </div>
        <script>
            function login() {
                var username = document.getElementById('username').value;
                var password = document.getElementById('password').value;

                if (username === 'admin' && password === '123456') {
                    setTimeout(function () {
                        window.location.href = 'principal.jsp';
                    }, 2000);
                } else {
                    window.location.href = 'invalido.jsp';
                }
            }
        </script>
    </body>
</html>
