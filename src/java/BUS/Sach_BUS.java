package BUS;
import DTO.Sach_DTO;
import DAO.Sach_DAO;
import java.util.ArrayList;
public class Sach_BUS {
    private ArrayList<Sach_DTO> listSach;
    private  Sach_DAO sach_DAO = new Sach_DAO();

    public ArrayList<Sach_DTO> getListSach() {
        listSach = sach_DAO.getListSach();
        return listSach;
    }
    public ArrayList<Sach_DTO> getListSach_not_delete() {
        listSach = sach_DAO.getListSach_not_delete();
        return listSach;
    }
    public boolean themSach(Sach_DTO sach) {
        return sach_DAO.addSach(sach);
    }

    public boolean suaSach(Sach_DTO sach) {
        return sach_DAO.updateSach(sach);
    }

    public boolean xoaSach(Sach_DTO sach) {
        return sach_DAO.deleteSach(sach);
    }

    public ArrayList<Sach_DTO> timSachTheoMaSach(String maSach) {
        return sach_DAO.findSachByMaSach(maSach);
    }
}
