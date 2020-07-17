package huyng.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "laptops")
@XmlType(name = "", propOrder = {
        "laptop",
        "pageSize",
        "pageNumber",
        "totalPage",
        "haveNext",
        "next",
        "previous"
})
public class LaptopEntityList {

    public LaptopEntityList(List<LaptopEntity> laptop) {
        this.laptop = laptop;
    }

    public LaptopEntityList() {
    }

    protected List<LaptopEntity> laptop;
    private int pageSize;
    private int pageNumber;
    private int totalPage;
    private boolean haveNext;
    private int next;
    private int previous;

    public int getPrevious() {
        if (pageNumber == 1) return 1;
        return pageNumber - 1;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public int getNext() {
        if (totalPage == pageNumber) return pageNumber;
        return pageNumber + 1;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<LaptopEntity> getLaptop() {
        if (laptop == null){
            laptop = new ArrayList<LaptopEntity>();
        }
        return laptop;
    }

    public void setLaptop(List<LaptopEntity> laptops) {
        this.laptop = laptops;
    }

    @Override
    public String toString() {
        return "LaptopEntityList{" +
                "laptop=" + laptop +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", totalPage=" + totalPage +
                '}';
    }

    public boolean isHaveNext() {
        return pageNumber!=totalPage;
    }

    public void setHaveNext(boolean haveNext) {
        this.haveNext = haveNext;
    }
}
