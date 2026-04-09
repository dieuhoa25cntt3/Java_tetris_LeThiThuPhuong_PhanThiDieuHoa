# 🎮 ĐỒ ÁN NHÓM: JAVA TETRIS GAME
## 👥 1. Thông tin thành viên & Phân công
Nhóm chúng em gồm 2 thành viên, cùng phối hợp xây dựng và phát triển mã nguồn.
| Họ và Tên | MSSV | Vai trò chính | Nhiệm vụ chi tiết |
| :--- | :--- | :--- | :--- |
| **[Lê Thị Thu Phương]** | [3120225122] | **Game Logic Developer** | Xây dựng thuật toán va chạm, xử lý xoay khối hình, logic xóa hàng và quản lý hệ thống lưu điểm cao nhất (`best_score.txt`). |
| **[Phan Thị Diệu Hoa]** | [3120225054] | **UI/UX & Graphics Designer** | Thiết kế giao diện đồ họa bằng Swing, xử lý màu sắc khối hình, tích hợp hình nền, âm thanh và quản lý kho lưu trữ GitHub. |
---
## 📝 2. Giới thiệu dự án
Dự án là một phiên bản hiện đại của trò chơi xếp gạch (Tetris) cổ điển, được viết hoàn toàn bằng ngôn ngữ **Java**. 
- **Đồ họa:** Sử dụng thư viện `javax.swing` và `java.awt`.
- **Tính năng:** - Đầy đủ 7 loại khối hình (I, J, L, O, S, T, Z) với màu sắc riêng biệt.
  - Chức năng lưu điểm kỷ lục (Best Score) vào file cục bộ.
  - Chế độ Tạm dừng (Pause) và Kết thúc game (Game Over).
  - Tích hợp hình nền động và giao diện trực quan.
---
## 🛠 3. Hướng dẫn cài đặt và khởi chạy
Vì dự án được phát triển trên môi trường Java tiêu chuẩn, thầy/cô và các bạn có thể chạy nhanh theo các bước:
1. **Yêu cầu hệ thống:** Đã cài đặt JDK 11 hoặc mới hơn.
2. **Tải mã nguồn:** `git clone [Link-repo-cua-ban]`
3. **Chạy chương trình:**
   Mở Terminal/Command Prompt tại thư mục chứa file và gõ:
   ```bash
   javac TetrisGame.java
   java TetrisGame
