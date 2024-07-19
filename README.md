# Hướng dẫn chạy ứng dụng bằng Docker

## Cài đặt Docker và Docker Compose

Trước tiên, đảm bảo rằng bạn đã cài đặt Docker và Docker Compose trên máy tính của mình. Bạn có thể tải và cài đặt Docker từ [trang chính thức của Docker](https://docs.docker.com/get-docker/).

## Chạy file docker-compose

Để khởi động ứng dụng, bạn cần chạy file `docker-compose.yml` bằng lệnh sau:
```bash
docker-compose -f docker-compose.yml up -d

```

### Truy cập Swagger

Sau khi ứng dụng đã được khởi chạy, bạn có thể truy cập Swagger UI để khám phá và kiểm thử các API.

- Truy cập Swagger bằng đường dẫn: `localhost:8031/swagger-ui.html`

