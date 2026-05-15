Журнал обслуживания автомобиля
Android-приложение для учета записей по техническому обслуживанию автомобиля. Приложение реализовано на Jetpack Compose и соответствует учебному заданию: список элементов, детальный экран, экран добавления/редактирования, CRUD-операции, локальная база данных Room, управление состоянием через ViewModel и StateFlow, навигация через Navigation Compose, внедрение зависимостей через Hilt и отдельный слой Repository.[cite:58][cite:18][cite:11]

Назначение
Приложение позволяет хранить записи об обслуживании автомобиля: дату обслуживания, пробег, тип работ, стоимость, место обслуживания и заметки. Все данные сохраняются локально в базе Room, поэтому приложению не требуется доступ к сети, а разрешение INTERNET можно удалить из манифеста.[cite:18][cite:56]

Функциональные возможности
Просмотр списка записей в прокручиваемом списке LazyColumn.[cite:18]

Просмотр подробной информации о выбранной записи на отдельном экране.[cite:58]

Добавление новой записи об обслуживании.[cite:58]

Редактирование существующей записи.[cite:58]

Удаление записи из локальной базы данных.[cite:58]

Автоматическое обновление интерфейса при изменении данных через Flow/StateFlow.[cite:12][cite:15]

Навигация между экранами через Navigation Compose и получение ViewModel через hiltViewModel().[cite:11]

Структура проекта

app/src/main/java/com/example/lab7/
├── App.kt
├── MainActivity.kt
├── components/
│   └── CenterProgress.kt
├── data/
│   ├── AppDatabase.kt
│   ├── CarServiceRepository.kt
│   └── ServiceRecordDao.kt
├── di/
│   └── DatabaseModule.kt
├── model/
│   └── ServiceRecordEntity.kt
├── navigation/
│   └── AppNavigation.kt
├── screens/
│   ├── ServiceDetailScreen.kt
│   ├── ServiceEditScreen.kt
│   └── ServiceListScreen.kt
└── viewmodel/
    └── ServiceViewModel.kt

    Архитектура
Приложение построено по слоистой схеме: Room DAO -> Repository -> ViewModel -> Compose UI, что соответствует рекомендациям Android по выделению data layer и отделению доступа к данным от UI-логики.[cite:58] Room возвращает Flow, который преобразуется во StateFlow в ViewModel через stateIn(...), благодаря чему список записей автоматически обновляется при изменении базы данных.[cite:12][cite:15]

Основные компоненты
Компонент	Назначение
ServiceRecordEntity	Сущность Room для хранения записи об обслуживании автомобиля.
ServiceRecordDao	CRUD-операции для локальной базы данных Room.
AppDatabase	Основная база данных приложения.
CarServiceRepository	Инкапсулирует доступ к данным и отделяет DAO от ViewModel.[cite:58]
ServiceViewModel	Хранит состояние экранов через StateFlow и выполняет операции с данными.[cite:12][cite:15]
AppNavigation	Определяет маршруты list, detail/{recordId}, edit/{recordId} через Navigation Compose.[cite:11]
ServiceListScreen	Экран списка записей на LazyColumn.[cite:18]
ServiceDetailScreen	Экран просмотра полной информации о записи.
ServiceEditScreen	Экран создания и редактирования записи.

Модель данных
Каждая запись обслуживания содержит следующие поля:

id — идентификатор записи.

serviceDate — дата обслуживания.

mileage — пробег автомобиля на момент обслуживания.

serviceType — тип выполненных работ.

cost — стоимость обслуживания.

location — сервис или место выполнения работ.

notes — дополнительные заметки.

createdAt — время создания записи.

Для идентификатора используется @PrimaryKey(autoGenerate = true), что позволяет Room и SQLite автоматически создавать ключ для новых записей при значении 0.[cite:18]

Экраны приложения
1. Список записей
Главный экран показывает все записи обслуживания в виде карточек. Для отображения используется LazyColumn, что соответствует требованию задания о прокручиваемом списке элементов.[cite:18]

2. Детальный экран
Экран деталей открывается при выборе записи из списка и отображает полную информацию: дату, пробег, тип работ, стоимость, место обслуживания и заметки. Навигация на этот экран реализована через Navigation Compose с передачей идентификатора записи в аргументах маршрута.[cite:11]

3. Экран редактирования
Экран используется и для создания новой записи, и для редактирования существующей. В форме применяются OutlinedTextField, а для числовых полей используются KeyboardOptions и KeyboardType, чтобы корректно отображать цифровую клавиатуру в Compose.[cite:37][cite:39]

CRUD-операции
Приложение поддерживает полный набор операций:

Create — добавление новой записи.

Read — чтение списка записей и одной записи по идентификатору.

Update — редактирование существующей записи.

Delete — удаление записи из базы данных.

Все операции выполняются через ServiceRecordDao и CarServiceRepository, а интерфейс автоматически обновляется благодаря реактивной модели Flow/StateFlow.[cite:58][cite:12][cite:15]

Используемые технологии
Kotlin — основной язык разработки.

Jetpack Compose — декларативный UI-фреймворк для Android.[cite:18]

Room — локальная база данных SQLite-уровня с DAO и сущностями.[cite:18]

ViewModel — хранение состояния экранов и переживание конфигурационных изменений.[cite:58]

StateFlow — реактивное состояние UI.[cite:15]

Navigation Compose — навигация между экранами Compose.[cite:11]

Hilt — внедрение зависимостей для Database, Dao, Repository и ViewModel.[cite:11]

Как собрать и запустить
Открыть проект в Android Studio.

Выполнить синхронизацию Gradle (Sync Project with Gradle Files).

Убедиться, что удалены старые файлы, связанные с Person, Todo, Retrofit и PersonApi.

Запустить приложение на эмуляторе или физическом устройстве с Android 7.0+ (minSdk = 24).

Если после удаления старых файлов IDE показывает ложные ошибки импорта, обычно помогает Build > Rebuild Project или Invalidate Caches / Restart, что типично для Android Studio после крупных переименований и удаления классов.[cite:24][cite:25]

Соответствие заданию
Требование задания	Реализация в проекте
Список элементов с прокруткой	ServiceListScreen + LazyColumn.[cite:18]
Детальный экран	ServiceDetailScreen.
Экран редактирования	ServiceEditScreen.
CRUD-операции	ServiceRecordDao и CarServiceRepository.[cite:58]
Локальная база данных	Room, AppDatabase, ServiceRecordEntity.[cite:18]
Управление состоянием	ViewModel + StateFlow.[cite:12][cite:15]
Навигация	Navigation Compose.[cite:11]
Внедрение зависимостей	Hilt.[cite:11]
Репозиторий	CarServiceRepository как отдельный слой data access.[cite:58]

Что было удалено из исходного проекта
Из исходной версии были удалены все файлы и зависимости, относящиеся к старой предметной области и сетевому слою:

PersonApi

PersonDao

PersonRepository

NetworkModule

PersonDto

PersonEntity

TodoDto

PersonListScreen

PersonDetailScreen

PersonEditScreen

UserTodosScreen

старый PersonViewModel

зависимости Retrofit, Moshi и OkHttp

Это упростило приложение и привело его к формату локального учебного CRUD-проекта на Room без лишнего сетевого слоя.[cite:58][cite:56]

Возможные улучшения
В дальнейшем приложение можно расширить следующими возможностями:

сортировка записей по дате или пробегу;

фильтрация по типу обслуживания;

подтверждение удаления через AlertDialog;

начальные демонстрационные данные при первом запуске;

экспорт истории обслуживания;

переход на type-safe navigation в Compose.[cite:13][cite:16]
