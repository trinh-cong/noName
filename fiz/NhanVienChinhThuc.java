package fiz;

public class NhanVienChinhThuc extends NhanVien {
    String loai;


    public NhanVienChinhThuc(String ten, int thangLamViec, String loai, int soNgayLamThem) {
        super(ten, thangLamViec,soNgayLamThem);
        this.loai = loai;
    }

    @Override
    public double tinhLuong() {
        double luongCoBan = 0;
        if (loai.equals("quanly")) {
            luongCoBan = 10000000; // Lương cứng của quản lý
        } else if (loai.equals("congnhan")) {
            luongCoBan = 6000000; // Lương cứng của công nhân
        }
        double luongThem = soNgayLamThem * 500000; // Tiền làm thêm
        return luongCoBan + luongThem;
    }
}
