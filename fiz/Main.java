package fiz;


import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static NhanVien[] danhSachNhanVien;

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("===== Quản lý thông tin nhân viên =====");
            System.out.println("1. Hiển thị danh sách nhân viên");
            System.out.println("2. Thêm nhân viên");
            System.out.println("3. Xóa thông tin lương nhân viên");
            System.out.println("4. Chỉnh sửa thông tin nhân viên");
            System.out.println("5. Tìm kiếm thông tin nhân viên");
            System.out.println("6. Lưu và thoát");

            System.out.print("Nhập lựa chọn của bạn: ");
            int option = scanner.nextInt();
            scanner.nextLine();


            switch (option) {
                case 1:

                    System.out.println("Danh sách nhân viên:");
                    for (NhanVien nhanVien : danhSachNhanVien) {
                        System.out.println("Tên: " + nhanVien.ten + ", Lương: " + nhanVien.tinhLuong());
                    }
                    break;
                case 2:
                    int soLuong;

                    do {
                        System.out.print("Nhập số lượng nhân viên: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Vui lòng nhập một số nguyên.");
                            scanner.next(); // Consume the invalid input
                        }
                        soLuong = scanner.nextInt();
                    } while (soLuong <= 0);

                    danhSachNhanVien = new fiz.NhanVien[soLuong];

                    for (int i = 0; i < soLuong; i++) {
                        System.out.println("Nhập thông tin cho nhân viên thứ " + (i + 1));
                        System.out.print("Tên: ");
                        String ten = scanner.next();
                        String loai;
                        boolean isValidLoai = false;
                        do {
                            System.out.print("Loại (quanly/congnhan/thoivu): ");
                            loai = scanner.next();
                            scanner.nextLine();
                            loai = loai.toLowerCase(); // Chuyển đổi chuỗi nhập vào thành chữ thường để so sánh dễ dàng hơn

                            if (loai.equals("quanly") || loai.equals("congnhan") || loai.equals("thoivu")) {
                                isValidLoai = true;
                            } else {
                                System.out.println("Loại nhập vào không hợp lệ. Vui lòng nhập lại.");
                            }
                        } while (!isValidLoai);

                        int thangLamViec;
                        do {
                            System.out.print("Tháng làm việc: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Vui lòng nhập một số nguyên.");
                                scanner.next(); // Consume the invalid input
                            }
                            thangLamViec = scanner.nextInt();
                        } while (thangLamViec <= 0 || thangLamViec > 12); // Assuming thangLamViec should be a valid month (1-12)
                        if (loai.equals("thoivu")) {
                            int soNgayLamThem;
                            int soGioLamThem;
                            // Validate and re-enter the value for "số ngày làm thêm"
                            do {
                                System.out.print("Số ngày làm thêm: ");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("Vui lòng nhập một số nguyên.");
                                    scanner.next(); // Consume the invalid input
                                }
                                soNgayLamThem = scanner.nextInt();
                            } while (soNgayLamThem < 0); // Assuming số ngày làm thêm cannot be negative

                            // Validate and re-enter the value for "Số giờ làm thêm"
                            do {
                                System.out.print("Số giờ làm thêm: ");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("Vui lòng nhập một số nguyên.");
                                    scanner.next(); // Consume the invalid input
                                }
                                soGioLamThem = scanner.nextInt();
                            } while (soGioLamThem < 0); // Assuming số giờ làm thêm cannot be negative

                            scanner.nextLine(); // Consume the newline character
                            danhSachNhanVien[i] = new NhanVienThoiVu(ten, thangLamViec, soNgayLamThem, soGioLamThem);
                        } else {
                            int soNgayLamThem;
                            // Validate and re-enter the value for "Số ngày làm thêm"
                            do {
                                System.out.print("Số ngày làm thêm: ");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("Vui lòng nhập một số nguyên.");
                                    scanner.next(); // Consume the invalid input
                                }
                                soNgayLamThem = scanner.nextInt();
                            } while (soNgayLamThem < 0); // Assuming số ngày làm thêm cannot be negative

                            scanner.nextLine(); // Consume the newline character
                            danhSachNhanVien[i] = new NhanVienChinhThuc(ten, thangLamViec, loai, soNgayLamThem);
                        }

                    }

                    System.out.println("\nThông tin lương của các nhân viên:");
                    for (fiz.NhanVien nhanVien : danhSachNhanVien) {
                        System.out.println(nhanVien.ten + " - Lương: " + nhanVien.tinhLuong());
                    }
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter("luong_nhan_vien.txt"));
                        writer.println("Tên               Loại              Số ngày làm thêm      Số giờ làm thêm              Lương");
                        for (fiz.NhanVien nhanVien : danhSachNhanVien) {
                            if (nhanVien instanceof NhanVienChinhThuc) {
                                NhanVienChinhThuc nhanVienChinhThuc = (NhanVienChinhThuc) nhanVien;
                                writer.printf("%-15s   %-20s    %-20d    %-15s    %.2f\n", nhanVienChinhThuc.ten, ((nhanVienChinhThuc.loai.equals("quanly")) ? "Quản lý" : "Công nhân"), nhanVienChinhThuc.soNgayLamThem, "0", nhanVienChinhThuc.tinhLuong());
                            } else if (nhanVien instanceof NhanVienThoiVu) {
                                NhanVienThoiVu nhanVienThoiVu = (NhanVienThoiVu) nhanVien;
                                writer.printf("%-15s   %-20s    %-20s    %-15d    %.2f\n", nhanVienThoiVu.ten, "Công nhân thời vụ", "0", nhanVienThoiVu.soGioLamThem, nhanVienThoiVu.tinhLuong());
                            }
                        }
                        writer.close();
                        System.out.println("Đã xuất file thành công");
                    } catch (IOException e) {
                        System.out.println("Xảy ra lỗi khi lưu thông tin vào file: " + e.getMessage());
                    }


                    break;
                case 3:
                    System.out.print("Nhập tên nhân viên cần xóa thông tin lương: ");
                    String tenCanXoa = scanner.nextLine();

                    List<String> updatedContent = new LinkedList<>();
                    boolean found = false;

                    try (BufferedReader reader = new BufferedReader(new FileReader("luong_nhan_vien.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains(tenCanXoa)) {
                                found = true;
                                continue; // Skip this line to effectively "delete" the salary information
                            }
                            updatedContent.add(line);
                        }
                    } catch (IOException e) {
                        System.out.println("Xảy ra lỗi khi đọc file: " + e.getMessage());
                    }

                    if (!found) {
                        System.out.println("Không tìm thấy thông tin lương của nhân viên có tên " + tenCanXoa);
                    } else {
                        try (PrintWriter writer = new PrintWriter(new FileWriter("luong_nhan_vien.txt"))) {
                            for (String line : updatedContent) {
                                writer.println(line);
                            }
                            System.out.println("Đã xóa thông tin lương của nhân viên " + tenCanXoa + " khỏi file.");
                        } catch (IOException e) {
                            System.out.println("Xảy ra lỗi khi ghi file: " + e.getMessage());
                        }
                    }

                    break;


                case 4:
                    System.out.print("Nhập tên nhân viên cần chỉnh sửa thông tin: ");
                    String tenCanChinhSua = scanner.nextLine();

                    boolean foundNhanVien = false;
                    for (int i = 0; i < danhSachNhanVien.length; i++) {
                        NhanVien nhanVien = danhSachNhanVien[i];
                        if (nhanVien.ten.equals(tenCanChinhSua)) {
                            foundNhanVien = true;
                            System.out.println("Thông tin nhân viên cần chỉnh sửa:");
                            System.out.println("Tên: " + nhanVien.ten);
                            System.out.println("Loại: " + ((nhanVien instanceof NhanVienChinhThuc) ? ((NhanVienChinhThuc) nhanVien).loai : "Công nhân thời vụ"));

                            if (nhanVien instanceof NhanVienChinhThuc) {
                                System.out.println("Số ngày làm thêm: " + ((NhanVienChinhThuc) nhanVien).soNgayLamThem);
                            } else {
                                System.out.println("Số giờ làm thêm: " + ((NhanVienThoiVu) nhanVien).soGioLamThem);
                            }
                            System.out.print("Bạn muốn cập nhật tên nhân viên? (y/n) ");
                            String updateTen = scanner.nextLine();
                            if (updateTen.equalsIgnoreCase("y")) {
                                System.out.print("Nhập tên mới: ");
                                String newTen = scanner.nextLine();
                                nhanVien.ten = newTen;
                            }

                            if (nhanVien instanceof NhanVienChinhThuc) {
                                System.out.print("Bạn muốn cập nhật loại nhân viên (yawners/congnhan)? (y/n) ");
                                String updateLoai = scanner.nextLine();
                                if (updateLoai.equalsIgnoreCase("y")) {
                                    boolean isValidLoai = false;
                                    String newLoai;
                                    do {
                                        System.out.print("Loại (quanly/congnhan): ");
                                        newLoai = scanner.next();
                                        scanner.nextLine();
                                        newLoai = newLoai.toLowerCase();
                                        if (newLoai.equals("quanly") || newLoai.equals("congnhan")) {
                                            isValidLoai = true;
                                        } else {
                                            System.out.println("Loại nhập vào không hợp lệ. Vui lòng nhập lại.");
                                        }
                                    } while (!isValidLoai);
                                    ((NhanVienChinhThuc) nhanVien).loai = newLoai;
                                }
                                System.out.print("Bạn muốn cập nhật số ngày làm việc? (y/n) ");
                                String updateSoNgayLamViec = scanner.nextLine();
                                if (updateSoNgayLamViec.equalsIgnoreCase("y")) {
                                    int newSoNgayLamViec;
                                    do {
                                        System.out.print("Số ngày làm việc: ");
                                        while (!scanner.hasNextInt()) {
                                            System.out.println("Vui lòng nhập một số nguyên.");
                                            scanner.next(); // Consume the invalid input
                                        }
                                        newSoNgayLamViec = scanner.nextInt();
                                    } while (newSoNgayLamViec < 0);
                                    ((NhanVienChinhThuc) nhanVien).soNgayLamThem = newSoNgayLamViec;
                                }
                            } else {
                                System.out.print("Bạn muốn cập nhật số giờ làm việc? (y/n) ");
                                String updateSoGioLamViec = scanner.nextLine();
                                if (updateSoGioLamViec.equalsIgnoreCase("y")) {
                                    int newSoGioLamViec;
                                    do {
                                        System.out.print("Số giờ làm việc: ");
                                        while (!scanner.hasNextInt()) {
                                            System.out.println("Vui lòng nhập một số nguyên.");
                                            scanner.next(); // Consume the invalid input
                                        }
                                        newSoGioLamViec = scanner.nextInt();
                                    } while (newSoGioLamViec < 0);
                                    ((NhanVienThoiVu) nhanVien).soGioLamThem = newSoGioLamViec;
                                }
                            }



                        }
                    }
                    if (!foundNhanVien) {
                        System.out.println("Không tìm thấy nhân viên có tên '" + tenCanChinhSua + "'.");
                    }

                    break; // Kết thúc chức năng chỉnh sửa thông tin nhân viên
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    scanner.nextLine();
                    System.out.print("Bạn muốn cập nhật thông tin vào file 'luong_nhan_vien.txt'? (y/n) ");
                    String updateFile = scanner.nextLine();


//

                    break;


                case 5:
                    System.out.print("Nhập tên nhân viên cần tìm kiếm: ");
                    String tenCanTimKiem = scanner.nextLine();

                    System.out.print("Nhập tháng cần tìm kiếm: ");
                    int thangCanTimKiem;
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("Vui lòng nhập một số nguyên.");
                            scanner.next(); // Xóa dữ liệu không hợp lệ khỏi luồng đầu vào
                        }
                        thangCanTimKiem = scanner.nextInt();
                    } while (thangCanTimKiem <= 0 || thangCanTimKiem > 12);

                    boolean found1 = false;
                    for (NhanVien nhanVien : danhSachNhanVien) {
                        if (nhanVien.ten.equalsIgnoreCase(tenCanTimKiem) && nhanVien.thangLamViec == thangCanTimKiem) {
                            found1 = true;
                            if (nhanVien instanceof NhanVienChinhThuc) {
                                NhanVienChinhThuc nhanVienChinhThuc = (NhanVienChinhThuc) nhanVien;
                                // Hiển thị thông tin nhân viên chính thức
                                System.out.println("Tên: " + nhanVienChinhThuc.ten);
                                System.out.println("Tháng làm việc: " + nhanVienChinhThuc.thangLamViec);
                                System.out.println("Loại nhân viên: " + ((nhanVienChinhThuc.loai.equals("quanly")) ? "Quản lý" : "Công nhân"));
                                System.out.println("Số ngày làm thêm: " + nhanVienChinhThuc.soNgayLamThem);
                                System.out.println("Lương: " + nhanVienChinhThuc.tinhLuong());
                            } else if (nhanVien instanceof NhanVienThoiVu) {
                                NhanVienThoiVu nhanVienThoiVu = (NhanVienThoiVu) nhanVien;
                                // Hiển thị thông tin nhân viên thời vụ
                                System.out.println("Tên: " + nhanVienThoiVu.ten);
                                System.out.println("Tháng làm việc: " + nhanVienThoiVu.thangLamViec);
                                System.out.println("Số ngày làm thêm: " + nhanVienThoiVu.soNgayLamThem);
                                System.out.println("Số giờ làm thêm: " + nhanVienThoiVu.soGioLamThem);
                                System.out.println("Lương: " + nhanVienThoiVu.tinhLuong());
                            }
                        }
                    }
                    if (!found1) {
                        System.out.println("Không tìm thấy nhân viên phù hợp.");
                    }
                    break;


                case 6:
                    System.out.println("Thoát chương trình");
                    System.exit(0);

                    break;


            }


//
        }
    }
}
