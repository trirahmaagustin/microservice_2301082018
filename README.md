Aplikasi Ecommerce berbasis microservice dengan spring boot dan Kubernetes

cara menjalankan
1. start cluster
minikube start

2. build docker image dan push ke repositori
docker build -t namaservice:version.
docker tag namaservice:version namaakun_dockerhub/namaservice:version
docker push namaakun_dockerhub/namaservice:version

3. deploy ke Kubernetes
kubectl apply -f eurekaserver-deployment.yaml
kubectl apply -f eurekaserver-service.yaml
kubectl apply -f product-service-deployment.yaml
kubectl apply -f product-service-service.yaml

4. verifikasi semua pod berjalan
kubectl get pods

5. verifikasi semua service dapat request dan response
test api endpoints
http://localhost:8081/api/produk
http://localhost:8082/api/payment
http://localhost:8083/api/orderss
http://localhost:8085/api/customer

jika menggunakan apigateway
http://localhost:8080/api/produk
http://localhost:8080/api/payment
http://localhost:8080/api/orderss
http://localhost:8080/api/customer

link video demo: https://drive.google.com/file/d/1H4quA-PB4vQRw5xnt_PvGH2kVSRGcvu3/view?usp=sharing
