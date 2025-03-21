# FASHION SHOP

## RUN

1. API

Mở CMD/ Terminal tại thư mục: D:\DoAnMonHoc\FashionShopManagement\API

Chạy lệnh:
```bash
dotnet run --urls="http://localhost:5001"

```


2. Mở tab CMD/ terminal mới, run ngrok:
```bash
ngrok http 5001
```
Ta được 1 url tương tự như sau:
Web Interface: http://127.0.0.1:4040
Forwarding: https://a4f2-115-74-132-168.ngrok-free.app -> http://localhost:5001 

3. Update lại Base url cho android app: https://a4f2-115-74-132-168.ngrok-free.app/api/