<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
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

        .game-image {
            max-width: 100%;
            height: auto;
            border-radius: 5px;
        }
        .confirmation-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #42f5d7;
            color: #1a1a1a;
            text-decoration: none;
            border-radius: 5px;
        }

        h1, h2 {
            color: #42f5d7;
        }

        p {
            line-height: 1.6;
            color: white;
        }

    </style>
</head>
<body>
<div class="container">
    <img class="game-image" src="${image}" alt="Game Image">
    <h1>${title}</h1>
    <p>${description}</p>
    <p style="color:#42f5d7">Цена игры: ${price}</p>
    <a class="confirmation-button" href="as">Купить</a>
</div>
</body>
</html>