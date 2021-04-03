<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="/style.css">
<link rel="stylesheet" href="/shake.css">
<title>сайтик</title>
</head>
<body>
    <?php include('templates/header.php'); ?>

    <div class="main-image">
		<img src="images/карта.png" />
                <div class="formCall">
                <input type="text" name="nameUser" placeholder="Ваше имя" class="formName" required><br>
                <input type="text" name="number" placeholder="Ваш номер" class="formEmail" required><br>
                <textarea name="message" placeholder="Введите ваше сообщение" class="formMessage" required  minlength=8"></textarea><br>
                <button type="submit" name="send" class="call" >Перезвоните мне!</button>
                </form>
                </div> 

    </div> 

    <li><div class="phone">
		<img  class="shake-slow"  src="images/трубка.png" />
    </div></li> 

   <div class="contacts">
   <h>Контакты: </h>  <h>Реквизиты: </h><br>
   <a>Телeфон: +7 (499) 703-44-50</a><br>
   <a>E-mail: market@blagovest.ru</a><br>
   <a>Режим работы: ПН.-ПТ. с 9:00 до 18:00</a><br>
   </div> 

   <div class="contacts2">
   <a>Название: ООО "ВИС"</a><br>
   <a>ИНН: </a><br>
   <a>Рассчетный счет:</a><br>
   </div> 
   <br><br>

   <?php include('templates/footer.php'); ?>


</body>
</html>