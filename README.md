# RSOI_Kozlova
Курсовой проект 2023

"Автоматизированная система расчета стипендий студентов высших учебных заведений"
 
--------------------------

ПОЯСНЕНИЯ К ПРОЕКТУ.
Курсовой проект находится в пакете "project". 
Классы разделены на следующие пакеты: 
 - DAO для класса, реализующего паттерн DAO (Data Access Object), обеспечивающий доступ к данным и разделение уровней персистентности (сохраняемости Java-Объектов в БД) и бизнес-логики;
 - GUI (классы для реализации фронт-энда - графический пользовательский интерфейс, реализованный с помощью JavaFX):
 - controllers для классов-контроллеров (каждый контроллер обрабатывает соответствующий fxml-файл из папке resources->project);
 - dialogs для диалоговых окон (все компоненты интерфейса, связанные с изменением данных);
 - model: основные классы модели, хранящие в себе данные о предметной области, объекты которых являются сущностями в БД;
 - net: в этой папке находятся классы, связанные с сообщениями, передаваемыми между составляющими программы, типами выполняемых операций в этих сообщениях и с содержанием (структурой,к омплектацией) модели для каждой операции;
 - server: два класса с логикой операций, выполняемых студентом/администратором, и класс с реализацией авторизации;
 - util: класс с реализацией заполнения таблиц интерфейса данными из БД, класс для хеширования паролей и два дополнительных класса (для работы с fxml и создания списка предметов одной специальности);
 - отдельно классы сервера и клиента.
 
В папке resources:
- META-INF: persistence.xml, в котором прописаны настройки подключения к базе данных ( в т.ч. логин и пароль от СУБД, драйвер и url (в т.ч. хост, порт и название БД));
- project: fxml-файлы - страницы графического интерфейса, сконструированные с помощью Scene Builder;

Отдельно помещен скрипт БД и файл pom.xml, описывающий настройки проекта Maven (в т.ч. подключенные компоненты и библиотеки, плагины для сборки проекта), а также данный Readme.md.

---------------------------

ПОЯСНЕНИЯ К РЕАЛИЗОВАННОМУ ФУНКЦИОНАЛУ.
На данный момент в структуре проекта есть все запланированные папки и классы в них, но в некоторых классах не до конца прописан функционал (например, в классе ServerAdminOperations в пакете server).

MVP (по вариантам использования). В коде реализовано:
- из CRUD-операций для администратора: 
- добавление: специальности, предмета, пользователя (надо реализовать: студента, посещаемости, базовой и специальной стипендий, связь предмета и специальности);
- обновление: все, кроме пользователя и связи предмет-специальность;
- удаление: специальности, предмета, пользователя (надо реализовать: посещаемости, студента, базовой и специальной стипендий, связи предмет-специальность);
- чтение информации о всех сущностях реализовано в методах showInfo() классов ServerAdminOperations и ServerStudentOperations.
- из вариантов использования для пользователя реализовано все (изменение личной информации, изменение пароля, расчет стипендии).

Есть вопросы с авторизацией, необходимо будет разобраться с процессом хеширования паролей и их сравнивания, потому что на данный момент вводимый пароль не совпадает с паролем из БД (будет исправлено позже).

Если миновать процесс авторизации (вручную отменить условие совпадения введенного пароля с паролем из БД), то в зависимости от введенного логина можно попасть на страницу администратора либо студента. Страница администратора пока не открывается (необходимо доработать), студента - открывается, но пока она некликабельна (нахожусь в поисках причины, доработаю). Соответственно, далее пока проверить функционал нет возможности.

---------------------------

ПОЯСНИТЕЛЬНАЯ ЗАПИСКА.
Содержание ПЗ составлено согласно бланку задания. Написана вся первая глава и 5 из 6 подпунктов второй (кроме графического интерфейса). Список источников пока не вытсроен по порядку. Инофрмационная модель, скорее всего, будет позже обновлена (возможно, будет изменен тип данныз для пароля). Диаграмма классов будет сгенерирована позже с помощью IntelliJ IDEA.