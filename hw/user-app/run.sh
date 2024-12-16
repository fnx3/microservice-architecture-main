cd user-app/deploy/db/
kubectl create namespace user-profile
kubectl apply -f pg-secrets.yaml
helm install postgresql bitnami/postgresql -n user-profile -f pg-values.yaml
helm install exporter-user-profile prometheus-community/prometheus-postgres-exporter -n user-profile -f postgres-exporter.yaml
cd ..
kubectl apply -f templates/