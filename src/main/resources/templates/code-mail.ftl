<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>hip0ker</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <style>
        * {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        body, html {
            background-color: #F3F3F3;
            font-size: 14px;
        }

        .container {
            width: 100%;
            padding: 15px 0;
            display: flex;
            justify-content: center;
        }

        .code {
            background-color: #FFFFFF;
        }

        /* 超小设备 (手机, 600px 以下屏幕设备) */
        @media only screen and (max-width: 600px) {
            .code {
                width: 80%;
            }
        }

        /* 小设备 (平板电脑和大型手机，600 像素及以上) */
        @media only screen and (min-width: 600px) {
            .code {
                width: 80%;
            }
        }

        /* 中型设备（平板电脑，768 像素及以上） */
        @media only screen and (min-width: 768px) {
            .code {
                width: 60%;
            }
        }

        /* 大型设备（笔记本电脑/台式机，992 像素及以上） */
        @media only screen and (min-width: 992px) {
            .code {
                width: 40%;
            }
        }

        /* 超大型设备（大型笔记本电脑和台式机，1200 像素及以上） */
        @media only screen and (min-width: 1200px) {
            .code {
                width: 30%;
            }
        }

        .code > div {
            width: 100%;
            box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2)
        }

        .code img {
            width: 100%;
        }

        .codeTxt h4 {
            font-size: 18px;
            line-height: 22px
        }

        .code h5 {
            font-size: 18px;
            text-align: center;
            letter-spacing: 2px;
        }

        .code p {
            font-size: 12px;
            margin: 15px 0
        }

        .copyRight {
            font-size: 10px;
            transform: scale(0.9);
            text-align: center;
            color: #071D2A
        }
    </style>
</head>
<body>
<div class="container">
    <div class="code">
        <div>
            <img src='https://bicoin.oss-cn-beijing.aliyuncs.com/bicoin-tss/code.png' alt='email-header'>
            <div style="padding: 5%">
                <h4><span>${username}</span>,您好:</h4>
                <p>以下是您访问 hip0ker 账户所需的唯一登录代码：</p>
                <h5>${code}</h5>
                <p>这是为了给您的账户添加额外的安全层；用户账户的安全性一直是 hip0ker 非常重视的。</p>
                <p class="copyRight">&copy; 2023 hip0ker</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>