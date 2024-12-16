cd mail-service/deploy/db/
kubectl create namespace mail
kubectl apply -f pg-secrets.yaml
helm install postgresql bitnami/postgresql -n mail -f pg-values.yaml
helm install exporter-mail prometheus-community/prometheus-postgres-exporter -n mail -f postgres-exporter.yaml
cd ..
kubectl apply -f templates/