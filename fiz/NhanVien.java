package fiz;

public class NhanVien {
     String ten;
    int thangLamViec;
    int soNgayLamThem;

    public NhanVien(String ten, int thangLamViec, int soNgayLamThem) {
        this.ten = ten;
        this.thangLamViec = thangLamViec;
        this.soNgayLamThem = soNgayLamThem;

    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getThangLamViec() {
        return thangLamViec;
    }

    public void setThangLamViec(int thangLamViec) {
        this.thangLamViec = thangLamViec;
    }

    public int getSoNgayLamThem() {
        return soNgayLamThem;
    }

    public void setSoNgayLamThem(int soNgayLamThem) {
        this.soNgayLamThem = soNgayLamThem;
    }

    public double tinhLuong(){
        return 0;

    }



}
