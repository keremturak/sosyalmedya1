#Burada kendimize ait özel bir docker imajı oluşturuyoruz.
#ilk olarak oluşturacağımız sistem için işletim sistemini seçiyoruz.
FROM amazoncorretto:17.0.8-alpine3.18
#kendi projemizi bu bilgisayarın içine kopyalamak için işlem yapıyoruz.
COPY build/libs/auth-micro-service-1.0-SNAPSHOT.jar app.jar
#son olarakta uygulamamızın çalışması için onu başlatıyoruz.
ENTRYPOINT ["java","-jar","/app.jar"]