package mn.khosbilegt.service.page;

import java.util.Comparator;

public class PageCreateDateComparator implements Comparator<Page> {
    @Override
    public int compare(Page p1, Page p2) {
        return p2.getCreateDate().compareTo(p1.getCreateDate());
    }
}