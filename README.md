# duplicate-files

Сервер для работы с дубликатами файлов

# Создание отчёта для работы сервера

```shell
ROOT_FOLDER=/d/repository/duplicate/example; \
BEGIN_DATE=$(date +%Y-%m-%d-%H-%M-%S); \
RESULT_FILE=$ROOT_FOLDER/duplicate-${BEGIN_DATE}.txt; \
rm -f $RESULT_FILE; \
find $ROOT_FOLDER -type f -exec md5sum {} >> $RESULT_FILE \;; \
wc $RESULT_FILE; \
END_DATE=$(date +%Y-%m-%d-%H-%M-%S); \
printf "complete: begin %s - end %s" $BEGIN_DATE $END_DATE;
```

# Настройки

[Основные настройки приложения](src/main/resources/application.yml)

# Сборка

```shell
gradlew build
```
