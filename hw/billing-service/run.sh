cd billing-service/deploy/db/
kubectl create namespace billing
kubectl apply -f pg-secrets.yaml
helm install postgresql bitnami/postgresql -n billing -f pg-values.yaml
helm install exporter-billing prometheus-community/prometheus-postgres-exporter -n billing -f postgres-exporter.yaml
cd ..
kubectl apply -f templates/