# 🕹️ JAVA TETRIS PROJECT
> **Bài tập lớn cuối kỳ môn Lập trình Java**
> **Giảng viên hướng dẫn:** [Nguyễn Hoàng Hải]

## 📝 Giới thiệu dự án (Description)
Dự án thực hiện xây dựng trò chơi **Tetris (Xếp gạch)** kinh điển trên nền tảng Java Desktop. Ứng dụng tập trung vào việc xử lý logic hình học, quản lý trạng thái ma trận bàn cờ và kỹ thuật đa luồng để tạo ra trải nghiệm chơi mượt mà.
* **Mục tiêu:** Vận dụng kiến thức về lập trình hướng đối tượng (OOP), xử lý sự kiện bàn phím và đồ họa Swing/AWT.
* **Thành quả:** Một trò chơi hoàn chỉnh với đầy đủ tính năng tính điểm, hiển thị khối tiếp theo và lưu kỷ lục điểm cao.

## 👥 Thông tin nhóm (Team Members)
| STT | Họ và Tên | Mã Sinh Viên | Vai trò / Nhiệm vụ chính |
|---|---|---|---|
| 1 | [Lê Thị Thu Phương] | [3120225122] | **Leader**: Quản lý Game Loop, Logic va chạm, Xoay khối & Xử lý đa luồng. |
| 2 | [Phan Thị Diệu Hoa] | [3120225054] | **Developer**: Thiết kế GUI (Board), Xử lý đồ họa Graphics2D & Hệ thống lưu điểm (File I/O). |

## ✨ Các chức năng chính (Features)
- [x] **7 Loại khối gạch (Tetrominoes):** Đầy đủ các khối I, J, L, O, S, T, Z với màu sắc và hình dạng chuẩn.
- [x] **Điều khiển bàn phím linh hoạt:** - `Phím Trái/Phải`: Di chuyển.
    - `Phím Lên`: Xoay khối.
    - `Phím Xuống`: Rơi nhanh.
    - `Phím Space`: Rơi tức thì (Hard Drop).
    - `Phím P`: Tạm dừng (Pause).
- [x] **Logic xử lý hàng:** Tự động phát hiện và xóa các hàng đã đầy, cộng điểm tương ứng.
- [x] **Hệ thống Score & Level:** Điểm số và cấp độ tăng dần, tốc độ game nhanh dần theo thời gian.
- [x] **Preview Next Piece:** Ô hiển thị khối gạch sắp xuất hiện tiếp theo.
- [x] **Lưu kỷ lục (Highscore):** Tự động lưu và hiển thị điểm số cao nhất (Record) bằng File I/O.

## 💻 Công nghệ & Thư viện (Technologies)
* **Ngôn ngữ:** Java (JDK 17+)
* **Thư viện đồ họa:** Java Swing, AWT.
* **Kỹ thuật trọng tâm:** * `Timer`: Điều phối nhịp độ rơi của khối gạch.
    * `Graphics2D`: Vẽ giao diện màn hình và các khối màu.
    * `Mảng 2 chiều`: Quản lý lưới bàn cờ kích thước $22 \times 10$.

## 📂 Cấu trúc mã nguồn (Project Structure)
Dự án được tổ chức theo mô hình phân lớp chuyên nghiệp:
* **`Tetris.java`**: Lớp khởi tạo JFrame, quản lý các thành phần hiển thị (Score, Level, Next).
* **`Board.java`**: Lớp quan trọng nhất, xử lý toàn bộ logic va chạm, xóa hàng và vẽ lại màn hình (Render).
* **`Shape.java`**: Định nghĩa tọa độ, hình dạng và thuật toán xoay cho 7 loại khối gạch.

## 🚀 Hướng dẫn cài đặt
1. Clone dự án về máy:
   ```bash
   git clone [https://github.com/username/java-tetris.git](https://github.com/username/java-tetris.git)
