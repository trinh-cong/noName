package fiz;

 public class NhanVienThoiVu extends NhanVien {
    int soGioLamThem;

    public NhanVienThoiVu(String ten, int thangLamViec,int soNgayLamThem, int soGioLamThem ) {
        super(ten, thangLamViec,soNgayLamThem);
        this.soGioLamThem = soGioLamThem;
    }

     public int getSoGioLamThem() {
         return soGioLamThem;
     }

     public void setSoGioLamThem(int soGioLamThem) {
         this.soGioLamThem = soGioLamThem;
     }

     @Override
    public double tinhLuong() {
        double luongNgay =  soNgayLamThem* 240000; // so tien lamf theo ngay
        double luongGio=soGioLamThem*50000; //luong theo gio
        return luongNgay+luongGio;
    }
}