cd stock-service/deploy/db/
kubectl create namespace stock
kubectl apply -f pg-secrets.yaml
helm install postgresql bitnami/postgresql -n stock -f pg-values.yaml
helm install exporter-stock prometheus-community/prometheus-postgres-exporter -n stock -f postgres-exporter.yaml
cd ..
kubectl apply -f templates/