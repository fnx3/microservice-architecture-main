cd auth-service/deploy/db/
kubectl create namespace auth-service
kubectl apply -f pg-secrets.yaml
helm install postgresql bitnami/postgresql -n auth-service -f pg-values.yaml
helm install exporter-auth prometheus-community/prometheus-postgres-exporter -n auth-service -f postgres-exporter.yaml
cd ..
kubectl apply -f templates/