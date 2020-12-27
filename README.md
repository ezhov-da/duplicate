# duplicate-files

```
BEGIN_DATE=$(date +%Y-%m-%d); \
ROOT_FOLDER=/d/YandexDisk/Фотокамера; \
RESULT_FILE=$ROOT_FOLDER/duplicate-$(date +%Y-%m-%d).txt; \
rm -f $RESULT_FILE; \
find $ROOT_FOLDER -type f -exec md5sum {} >> $RESULT_FILE \;; \
wc $RESULT_FILE; \
END_DATE=$(date +%Y-%m-%d); \
ptintf "complete: begin %s - end %s" $BEGIN_DATE $END_DATE;
```