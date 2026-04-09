# 🕹️ JAVA TETRIS PROJECT
> **Bài tập lớn cuối kỳ môn Lập trình Java**
> **Giảng viên hướng dẫn:** [Tên thầy/cô]

## 📝 Giới thiệu dự án (Description)
Dự án thực hiện xây dựng trò chơi **Tetris (Xếp gạch)** kinh điển trên nền tảng Java Desktop. Ứng dụng tập trung vào việc xử lý logic hình học, quản lý trạng thái mảng hai chiều và kỹ thuật xử lý đa luồng (Multi-threading) để tạo ra trải nghiệm chơi mượt mà.
* **Mục tiêu:** Vận dụng kiến thức về lập trình hướng đối tượng (OOP), xử lý sự kiện (Event Handling) và đồ họa Swing/AWT.
* **Thành quả:** Một trò chơi hoàn chỉnh với đầy đủ tính năng tính điểm, tăng cấp độ và giao diện trực quan.

## 👥 Thông tin nhóm (Team Members)
| STT | Họ và Tên | Mã Sinh Viên | Vai trò / Nhiệm vụ |
|---|---|---|---|
| 1 | [Tên Nhóm Trưởng] | [Mã SV] | **Leader**: Quản lý Game Loop, Xử lý va chạm, Logic xoay khối |
| 2 | [Tên Thành Viên 2] | [Mã SV] | **Developer**: Thiết kế GUI (Board), Xử lý đồ họa Graphics2D |
| 3 | [Tên Thành Viên 3] | [Mã SV] | **Tester & Report**: Quản lý điểm số, Xử lý File I/O, Viết báo cáo |

## ✨ Các chức năng chính (Features)
- [x] **7 Loại khối gạch (Tetrominoes):** Đầy đủ các khối I, J, L, O, S, T, Z với màu sắc riêng biệt.
- [x] **Điều khiển bàn phím:** - `Phím Trái/Phải`: Di chuyển khối.
    - `Phím Lên`: Xoay khối gạch.
    - `Phím Xuống`: Rơi nhanh (Soft Drop).
    - `Phím Space`: Rơi tức thì (Hard Drop).
    - `Phím P`: Tạm dừng trò chơi (Pause).
- [x] **Logic xử lý hàng:** Tự động phát hiện và xóa các hàng đã đầy, đẩy các khối phía trên xuống.
- [x] **Hệ thống Score & Level:** Điểm số tăng dần dựa trên số hàng xóa được; tốc độ rơi tăng dần khi lên cấp.
- [x] **Preview Next Piece:** Hiển thị khối gạch chuẩn bị xuất hiện tiếp theo.
- [x] **Lưu kỷ lục (Highscore):** Lưu trữ điểm số cao nhất qua các phiên chơi bằng File I/O.

## 💻 Công nghệ & Thư viện (Technologies)
* **Ngôn ngữ:** Java (JDK 17+)
* **Thư viện đồ họa:** Java Swing, AWT.
* **Kỹ thuật trọng tâm:** * `Timer`: Điều khiển nhịp độ game.
    * `Graphics2D`: Vẽ giao diện và các khối màu.
    * `Mảng 2 chiều`: Quản lý ma trận bàn cờ $22 \times 10$.

## 📂 Cấu trúc mã nguồn (Project Structure)
Dự án được tổ chức theo mô hình phân lớp rõ ràng:
* **`Tetris.java`**: Lớp khởi tạo chương trình, quản lý cửa sổ (JFrame) và các thành phần phụ (Score, Level).
* **`Board.java`**: Trái tim của game, xử lý logic va chạm, vẽ lại màn hình và điều khiển luồng game.
* **`Shape.java`**: Định nghĩa tọa độ, hình dạng và logic xoay của 7 loại khối gạch.
* **`Data/`**: Thư mục lưu trữ file điểm cao (.txt hoặc .dat).

## 🚀 Hướng dẫn cài đặt
1. Clone dự án:
   ```bash
   git clone [https://github.com/username/java-tetris.git](https://github.com/username/java-tetris.git)
