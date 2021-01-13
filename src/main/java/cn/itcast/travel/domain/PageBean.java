package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    //当前页码
    private int currentPage;
    //总页码
    private int totalPage;
    //总记录数
    private int totalCount;
    //每页显示的条数
    private int pageSize;
    //每页需要展示的对象
    private List<T> categoryList;

    public PageBean() {
    }

    public PageBean(int currentPage, int totalPage, int totalCount, int pageSize, List<T> categoryList) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.categoryList = categoryList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<T> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", categoryList=" + categoryList +
                '}';
    }
}
