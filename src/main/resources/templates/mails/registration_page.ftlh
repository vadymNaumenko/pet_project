<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Подтверждение регистрации</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #1a1a1a;
            color: #fff;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #333;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(255, 255, 255, 0.1);
        }

        h1 {
            color: #42f5d7;
        }

        p {
            line-height: 1.6;
            color: white;
        }

        .confirmation-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #42f5d7;
            color: #1a1a1a;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Добро пожаловать в мир игр!</h1>
    <p>Спасибо за регистрацию. Для завершения процесса, пожалуйста, подтвердите свой аккаунт.</p>
    <p>Нажмите на кнопку ниже, чтобы подтвердить регистрацию:</p>
    <a class="confirmation-button" href="${link}">Подтвердить регистрацию</a>
</div>
</body>
</html>