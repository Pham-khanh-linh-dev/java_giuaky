# E-Commerce Spring Boot Project

## Giới thiệu

Đây là dự án website thương mại điện tử được xây dựng bằng **Java Spring Boot**, sử dụng **Thymeleaf** cho giao diện và **Spring Security** để xác thực, phân quyền người dùng. Dự án hỗ trợ các chức năng cơ bản như đăng ký, đăng nhập, quản lý giỏ hàng, đặt hàng (checkout), và quản trị người dùng.

---

## Tính năng chính

- Đăng ký, đăng nhập, phân quyền (USER/ADMIN)
- Quản lý sản phẩm, giỏ hàng, đặt hàng
- Giao diện động với Thymeleaf
- Bảo mật với Spring Security (CSRF, session-based authentication)
- Quản trị viên có thể quản lý người dùng và sản phẩm

---

## Cấu trúc dự án

```
src/
 └── main/
     ├── java/
     │    └── com/example/demo/
     │         ├── controller/      # Controllers cho các chức năng
     │         ├── dto/             # Data Transfer Objects
     │         ├── model/           # Các entity/model
     │         ├── repositories/    # JPA repositories
     │         ├── service/         # Interface service
     │         └── service/implementation/ # Triển khai service
     └── resources/
          ├── static/               # Tài nguyên tĩnh (css, js, img)
          ├── templates/            # Thymeleaf templates (html)
          └── application.properties
```

---

## Hướng dẫn cài đặt & chạy

1. **Yêu cầu:**
   - Java 8 trở lên
   - Maven

2. **Cài đặt:**
   - Clone dự án về máy:
     ```
     git clone <link-repo>
     ```
   - Cài đặt các dependency:
     ```
     mvn clean install
     ```

3. **Chạy ứng dụng:**
   - Bước 1: Tạo database có tên phù hợp với file appliaction.properties (project_java_mid_db), điều chỉnh cổng phù hợp( hiện tại là 3037)
   - Bước 2: Sử dụng lệnh:
     ```
     mvn spring-boot:run
     ```
   - Ứng dụng mặc định chạy tại: [http://localhost:3000](http://localhost:3000)

5. **Tài khoản mẫu:**
   - **Admin:**  
     Email: `admin@gmail.com`  
     Mật khẩu: `admin123`
   - **User:**  
     Email: `khanhbl9848@gmail.com`  
     Mật khẩu: `pklinh123`

